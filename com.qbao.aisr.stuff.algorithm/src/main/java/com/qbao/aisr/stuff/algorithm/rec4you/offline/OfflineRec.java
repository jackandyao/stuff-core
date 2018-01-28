package com.qbao.aisr.stuff.algorithm.rec4you.offline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qbao.aisr.stuff.algorithm.oneday.OnedayRec;
import com.qbao.aisr.stuff.cache.OnedayCache;
import com.qbao.aisr.stuff.cache.PcCache;
import com.qbao.aisr.stuff.cache.Rec4youCache;
import com.qbao.aisr.stuff.utils.DBTools;
import com.qbao.aisr.stuff.utils.JDBCUtil;
import com.qbao.aisr.stuff.utils.ReadHDFS;

public class OfflineRec {
	private static Logger logger = Logger.getLogger("OfflineRec");
	private static String table = "rec_user_stuff";
	public static void mainLogic() throws SQLException{
		
		createTempTable();
		
		retriveSpuMiddleData();
		logger.info("读取mysql上架产品记录" + Rec4youCache.spuIdDirIdMap.size());
		
		retriveOuterStuff(50);
		logger.info("读取站外商品，按类目和佣金排行" + Rec4youCache.dirIdStuffIdListMap.size());
		
		retriveOuterStuffDefault();
		logger.info("读取站外默认推荐结果" + Rec4youCache.stuffIdDefault.size());
		
		ReadHDFS.getUserProfile();
		logger.info("读取hive用户行为数据，并关联行为与类目" + Rec4youCache.userIdSpuIdBuyListMap.size());
		logger.info("读取hive用户行为数据，并关联行为与类目" + Rec4youCache.userIdSpuIdViewListMap.size());
		
		logger.info("推荐排列与存储");
		shuffleAndSave(50);
		
		renameTempTable();
	}


	public static void retriveSpuMiddleData() throws SQLException {
		if(Rec4youCache.spuIdDirIdMap!=null && Rec4youCache.spuIdDirIdMap.size()>0) return;
		
		String sql = "SELECT spu_id,dir_id FROM mer_middle_data";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConn();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String spuId = rs.getString(1);
			String dirId = rs.getString(2);
			Rec4youCache.spuIdDirIdMap.put(spuId, dirId);
			Rec4youCache.stuffIdDirIdMap.put(spuId, dirId);
		}
		rs.close();
		jdbcutil.closeConn();
	}
	
	public static void retriveOuterStuffDefault() throws SQLException {
		if(Rec4youCache.stuffIdDefault!=null && Rec4youCache.stuffIdDefault.size()>0) return;
		
		String sql = "SELECT id FROM stuff_promotion WHERE STATUS=1 AND cat_id <> '110103102102' and pc_promotion_url is not null and ios_promotion_url is not null and android_promotion_url is not null ORDER BY final_price*promotion_rate*LOG(order_num) DESC limit 0,300";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			Rec4youCache.stuffIdDefault.add(rs.getString(1));
		}
		Collections.shuffle(Rec4youCache.stuffIdDefault);
		
		rs.close();
		jdbcutil.closeConn();
		
		List<String[]> valueList = new ArrayList<>();
		valueList.add(new String[]{"0", String.join(",", Rec4youCache.stuffIdDefault), "", ""});
		load(valueList);
		valueList.clear();
	}
	
	
	public static void retriveOuterStuff(int n) throws SQLException {
		if(MapUtils.isNotEmpty(Rec4youCache.dirIdStuffIdListMap)&&MapUtils.isNotEmpty(OnedayCache.dirIdCouponStuffIdListMap)&&MapUtils.isNotEmpty(PcCache.dirIdPcStuffIdListMap)) return;
		if(CollectionUtils.isEmpty(OnedayCache.couponStuffIdSet)) OnedayRec.retriveOuterStuffCoupon();
		
		String sql = "SELECT id,cat_id,promotion_rate,pc_promotion_url,ios_promotion_url,android_promotion_url FROM stuff_promotion WHERE status=1 AND cat_id IS NOT NULL AND promotion_rate IS NOT NULL ORDER BY final_price*promotion_rate*LOG(order_num) DESC";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String id = rs.getString(1);
			String catId = rs.getString(2);
			Rec4youCache.stuffIdDirIdMap.put(id, catId);
			
			int promotionRate = rs.getInt(3);
			
			String pcPromotionUrl = rs.getString(4);
			String iosPromotionUrl = rs.getString(5);
			String androidPromotionUrl = rs.getString(6);
			
			if(promotionRate>=2000 && promotionRate<10000 && StringUtils.isEmpty(iosPromotionUrl) && StringUtils.isEmpty(androidPromotionUrl)) {//取高佣金比例商品
				List<String> stuffList = Rec4youCache.dirIdStuffIdListMap.get(catId);
				if(stuffList==null){
					stuffList = new ArrayList<>();
				}
				if(stuffList.size()<=n) {
					stuffList.add(id);
	//				Collections.shuffle(stuffList);
					Rec4youCache.dirIdStuffIdListMap.put(catId, stuffList);
				}
			}
			
			if(OnedayCache.couponStuffIdSet.contains(id) && StringUtils.isEmpty(iosPromotionUrl) && StringUtils.isEmpty(androidPromotionUrl) ){//优惠券商品
				List<String> stuffList = OnedayCache.dirIdCouponStuffIdListMap.get(catId);
				if(stuffList==null){
					stuffList = new ArrayList<>();
				}
				if(stuffList.size()<=n) {
					stuffList.add(id);
	//				Collections.shuffle(stuffList);
					OnedayCache.dirIdCouponStuffIdListMap.put(catId, stuffList);
				}
			}
			
			if(StringUtils.isEmpty(iosPromotionUrl) && StringUtils.isEmpty(androidPromotionUrl) && StringUtils.isEmpty(pcPromotionUrl)){//优惠券商品
				List<String> stuffList = PcCache.dirIdPcStuffIdListMap.get(catId);
				if(stuffList==null){
					stuffList = new ArrayList<>();
				}
				if(stuffList.size()<=n) {
					stuffList.add(id);
	//				Collections.shuffle(stuffList);
					PcCache.dirIdPcStuffIdListMap.put(catId, stuffList);
				}
			}
			
		}
		rs.close();
		jdbcutil.closeConn();
	}

	public static void retriveUserAction(String line) {
		JSONObject jb = JSON.parseObject(line);
		JSONObject baseInfo = jb.getJSONObject("base_info");
		if(baseInfo==null) return;
		String userId = jb.getString("user_id");
		
		JSONObject goods = jb.getJSONObject("goods");
		
		JSONArray view_items =goods.getJSONArray("view_items");
		if(view_items!=null){
			for(int i=0;i<view_items.size();i++){
				JSONObject itemObject = view_items.getJSONObject(i);
				List<String> list = Rec4youCache.userIdSpuIdViewListMap.get(userId);
				if(list==null) list = new ArrayList<>();
				list.add(itemObject.getString("goods_id"));
				Rec4youCache.userIdSpuIdViewListMap.put(userId, list);
			}
		}
		
		JSONArray buy_items =goods.getJSONArray("buy_items");
		if(buy_items!=null){
			for(int i=0;i<buy_items.size();i++){
				JSONObject itemObject = buy_items.getJSONObject(i);
				List<String> list = Rec4youCache.userIdSpuIdBuyListMap.get(userId);
				if(list==null) list = new ArrayList<>();
				list.add(itemObject.getString("goods_id"));
				Rec4youCache.userIdSpuIdBuyListMap.put(userId, list);
			}
		}
		
	}
	
	private static void shuffleAndSave(int count) {
		
		List<String[]> valueList = new ArrayList<>();
		Set<String> ids = Sets.newHashSet();
		ids.addAll(Rec4youCache.userIdSpuIdViewListMap.keySet());
		ids.addAll(Rec4youCache.userIdSpuIdBuyListMap.keySet());
		for (String userId : ids) {
			List<String> viewSpuIds = Rec4youCache.userIdSpuIdViewListMap.get(userId);
			List<String> buySpuIds = Rec4youCache.userIdSpuIdBuyListMap.get(userId);
			List<String> viewBuyIds = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(viewSpuIds)) viewBuyIds.addAll(viewSpuIds);
			if(CollectionUtils.isNotEmpty(buySpuIds)) viewBuyIds.addAll(buySpuIds);
			List<String> viewRecList = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(viewSpuIds)) viewRecList = getRecListBySpuIds(viewSpuIds, count, true, false);
			List<String> buyRecList = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(buySpuIds)) buyRecList = getRecListBySpuIds(buySpuIds, count, true, false);
			buyRecList.removeAll(viewRecList);
			List<String> stuffIds = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(viewBuyIds)) stuffIds = getRecListBySpuIds(viewBuyIds, count, false, false);
//			for(int i=0; i<Math.max(viewRecList.size(), buyRecList.size()); i++){
//				if(viewRecList.size()>i) stuffIds.add(viewRecList.get(i));
//				if(buyRecList.size()>i) stuffIds.add(buyRecList.get(i));
//			}
			
			valueList.add(new String[]{userId, String.join(",", stuffIds), String.join(",", viewRecList) ,  String.join(",", buyRecList) });
			
			if(valueList.size() >= 100000){
				load(valueList);
				valueList.clear();
			}
			//,"rec_user_stuff", new String[]{"user_id", "stuff_ids", "view_ids", "buy_ids"}
		}
		if(valueList.size()>0){
			load(valueList);
			valueList.clear();
		}
		
	}
	
	
	private static void createTempTable() {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.createUserTable(jdbcutil, table+"_new");
		jdbcutil.closeConn();
	}
	private static void load(List<String[]> valueList){
		DBTools dbtools = new DBTools();
		saveOrUpdate(dbtools, valueList);
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		String[] columns = new String[]{"user_id", "stuff_ids", "view_ids", "buy_ids"};
		dbtools.loadCSV2Mysql(jdbcutil, table+"_new", columns);
		
		jdbcutil.closeConn();
	}
	private static void renameTempTable() {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.deleteTables(jdbcutil, table+"_bak");
		DBTools.renameTable(jdbcutil, table, table+"_bak");
		DBTools.renameTable(jdbcutil, table+"_new", table);
		jdbcutil.closeConn();
	}

	public static void saveOrUpdate(DBTools dbtool , List<String[]> valueList) {
		
		for (String[] record : valueList) {
			dbtool.addOneCSVRecord(record);
		}
//		JDBCUtil jdbcutil = new JDBCUtil();
//		jdbcutil.createConnStuff();
//		
//		StringBuffer deleteBatchSql = new StringBuffer();
//		StringBuffer insertBatchSql = new StringBuffer();
//		deleteBatchSql.append(" delete from "+table+" where ");
//		insertBatchSql.append(" insert into "+table+"("+String.join(",", columns)+") values ");
//		
//		for(int j=0;j<valueList.size();j++){
//    		String[] values = valueList.get(j);
//    		if( j>0 ) {
//    			deleteBatchSql.append(" or ");
//    			insertBatchSql.append(" , ");
//    		}
//    		deleteBatchSql.append(columns[0]).append(" = ").append(values[0]);
//    		insertBatchSql.append(" ( ").append(values[0]);
//    		for(int k=1; k<values.length; k++){
//    			insertBatchSql.append(", '").append(values[k]).append("' ");
//    		}
//    		insertBatchSql.append(" ) ");
//    	}
//    	jdbcutil.execute(deleteBatchSql.toString());
//    	jdbcutil.execute(insertBatchSql.toString());
//    	logger.info("生成"+table+"推荐结果" + valueList.size());
//		valueList.clear();
	}

	public static List<String> getRecListBySpuIds(List<String> spuIds, int count, boolean filterJD, boolean filterCoupon) {
		List<String> recList = new ArrayList<String>();
		Set<String> dupFilter = new HashSet<String>();
		
		int colIndex = 0;
		while(true){
			boolean skip = true;
			for (String id : spuIds) {
				String dirId = Rec4youCache.spuIdDirIdMap.get(id);
				if(dirId==null) continue;
				List<String> stuffIdList = null;
				
				if(!filterCoupon) {
					stuffIdList = Rec4youCache.dirIdStuffIdListMap.get(dirId);
				}  else{
					stuffIdList = OnedayCache.dirIdCouponStuffIdListMap.get(dirId);
				}
				
				if(filterJD) stuffIdList = filterJD(stuffIdList);
				if(stuffIdList != null && stuffIdList.size()>colIndex){
					skip = false;
					String stuffId = stuffIdList.get(colIndex);
					if(!dupFilter.contains(stuffId)){
						recList.add(stuffIdList.get(colIndex));
						dupFilter.add(stuffId);
					}
				}
			}
			++colIndex;
			if (skip) {
				List<String> defaultList = Rec4youCache.stuffIdDefault;
				if(filterJD) defaultList = filterJD(defaultList);
				Collections.shuffle(defaultList);
				for (String stuffId : defaultList) {
					if(!dupFilter.contains(stuffId)){
						recList.add(stuffId);
						dupFilter.add(stuffId);
					}
				}
			}
			if (skip || recList.size() > count) {
				recList = recList.subList(0, count);
				break;
			}
		}
		return recList;
	}
	
	private static List<String> filterJD(List<String> list){
		List<String> retList = Lists.newArrayList();
		if(CollectionUtils.isEmpty(list)) return retList;
		for (String string : list) {
			if(string != null && !string.endsWith("333"))
				retList.add(string);
		}
		return retList;
	}

}

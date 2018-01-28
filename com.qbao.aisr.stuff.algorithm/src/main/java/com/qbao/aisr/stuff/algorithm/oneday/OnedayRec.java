package com.qbao.aisr.stuff.algorithm.oneday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.cache.OnedayCache;
import com.qbao.aisr.stuff.cache.Rec4youCache;
import com.qbao.aisr.stuff.utils.DBTools;
import com.qbao.aisr.stuff.utils.JDBCUtil;

public class OnedayRec {

	private static Logger logger = Logger.getLogger("OnedayRec");
	
	private static String table = "rec_oneday_stuff";
	public static void mainLogic() throws SQLException{
		createTempTable();
		logger.info("开始生成oneday推荐结果");
		retriveOuterStuffCoupon();
		retriveOuterStuffDefault();
		OfflineRec.retriveOuterStuff(50);
		shuffleAndSave(50);
		logger.info("完成oneday推荐结果");
		renameTempTable();
	}
	
	public static void retriveOuterStuffCoupon() throws SQLException {
		if(OnedayCache.couponStuffIdSet !=null && OnedayCache.couponStuffIdSet.size()>0) return;
		
		String sql = "SELECT stuff_id FROM stuff_coupon sc, stuff_promotion sp WHERE sc.`stuff_id` = sp.`id` AND sp.`status`=1 AND sc.start_time<=NOW() AND sc.end_time>=NOW()";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			OnedayCache.couponStuffIdSet.add(rs.getString(1));
		}
		rs.close();
		jdbcutil.closeConn();
	}
	
	public static void retriveOuterStuffDefault() throws SQLException {
		if(OnedayCache.couponStuffIdDefault!=null && OnedayCache.couponStuffIdDefault.size()>0) return;
		
		String sql = "SELECT id FROM stuff_promotion WHERE STATUS=1 AND cat_id <> '110103102102' ORDER BY final_price*promotion_rate*LOG(order_num) DESC";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String stuffId = rs.getString(1);
			if(OnedayCache.couponStuffIdSet.contains(stuffId)) OnedayCache.couponStuffIdDefault.add(stuffId);
			if(OnedayCache.couponStuffIdDefault.size()>100) break;
		}
		Collections.shuffle(OnedayCache.couponStuffIdDefault);
		rs.close();
		jdbcutil.closeConn();
		
		List<String[]> valueList = new ArrayList<>();
		valueList.add(new String[]{"0", String.join(",", OnedayCache.couponStuffIdDefault)});
		load(valueList);
		valueList.clear();
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
			
			List<String> stuffIds = Lists.newArrayList();
			if(CollectionUtils.isNotEmpty(viewBuyIds)) stuffIds = OfflineRec.getRecListBySpuIds(viewBuyIds, count, false, true);
			
			valueList.add(new String[]{userId, String.join(",", stuffIds)});
			
			if(valueList.size() >= 100000){
				load(valueList);
				valueList.clear();
			}
			
		}
		if(valueList.size()>0){
			load(valueList);
			valueList.clear();
		}
		
	}
	
	private static void createTempTable() {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.createTables(jdbcutil, table+"_new");
		jdbcutil.closeConn();
	}
	
	private static void load(List<String[]> valueList){
		DBTools dbtools = new DBTools();
		OfflineRec.saveOrUpdate(dbtools, valueList);
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		String[] columns = new String[]{"user_id", "stuff_ids"};
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
}

package com.qbao.aisr.stuff.algorithm.detail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.cache.PcCache;
import com.qbao.aisr.stuff.cache.Rec4youCache;
import com.qbao.aisr.stuff.utils.DBTools;
import com.qbao.aisr.stuff.utils.JDBCUtil;

public class DetailRec {
	private static Logger logger = Logger.getLogger("DetailRec");
	public static void mainLogic() throws SQLException{
		retriveSpuMiddleData();
		logger.info("读取mysql上架产品记录" + Rec4youCache.spuIdDirIdMap.size());
		
		OfflineRec.retriveOuterStuff(50);
		logger.info("读取站外商品，按类目和佣金排行" + Rec4youCache.dirIdStuffIdListMap.size());
		
		retriveOuterStuffDefault();
		logger.info("读取站外默认推荐结果" + Rec4youCache.stuffIdDefault.size());
		logger.info("生成目录树");
		generateDirTree();
		logger.info("推荐排列与存储");
		shuffleAndSave(60);
		
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
		if(CollectionUtils.isNotEmpty(PcCache.pcStuffIdDefault)) return;
		
		String sql = "SELECT id FROM stuff_promotion where status=1 AND cat_id <> '110103102102' and pc_promotion_url is not null and ios_promotion_url is not null and android_promotion_url is not null ORDER BY promotion_rate DESC limit 0,300";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			PcCache.pcStuffIdDefault.add(rs.getString(1));
		}
		rs.close();
		jdbcutil.closeConn();
	}


	private static void shuffleAndSave(int count) {
		List<String[]> likeList = Lists.newArrayList();
		List<String[]> relateList = Lists.newArrayList();
		createTempTable("rec_stuff_like");
		createTempTable("rec_stuff_relate");
		for (Entry<String, String> stuffIdDirIdEntry : Rec4youCache.stuffIdDirIdMap.entrySet()) {
			String stuffId = stuffIdDirIdEntry.getKey();
			String dirId = stuffIdDirIdEntry.getValue();
			if(dirId==null || dirId.length()!=12) continue;
			List<String> likeRec = getRecListByDirIds(Arrays.asList(dirId),count,true);
			if(CollectionUtils.isNotEmpty(likeRec)){
				likeList.add(new String[]{stuffId,String.join(",", likeRec)});
				if(likeList.size() >= 100000) {
					load(likeList, "rec_stuff_like");
					likeList.clear();
				}
			}
			
			List<String> relatedDirids = getRelatedDirIds(dirId);
			List<String> relateRec = getRecListByDirIds(relatedDirids,count,true);
			if(CollectionUtils.isNotEmpty(relateRec)){
				relateList.add(new String[]{stuffId,String.join(",", relateRec)});
				if(relateList.size() >= 100000) {
					load(relateList, "rec_stuff_relate");
					relateList.clear();
				}
			}
		}
		if(likeList.size() > 0) {
			load(likeList, "rec_stuff_like");
			likeList.clear();
		}
		if(relateList.size() > 0) {
			load(relateList, "rec_stuff_relate");
			relateList.clear();
		}
		renameTempTable("rec_stuff_like");
		renameTempTable("rec_stuff_relate");
	}
	
	private static void createTempTable(String table) {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.createDetailTable(jdbcutil, table+"_new");
		jdbcutil.closeConn();
	}
	private static void load(List<String[]> likeList, String table){
		DBTools dbtoolsLike = new DBTools();
		
		OfflineRec.saveOrUpdate(dbtoolsLike, likeList);
		
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		
		String[] columns = new String[]{"stuff_id", "stuff_ids"};
		dbtoolsLike.loadCSV2Mysql(jdbcutil, table+"_new", columns);
		jdbcutil.closeConn();
	}
	private static void renameTempTable(String table) {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.deleteTables(jdbcutil, table+"_bak");
		DBTools.renameTable(jdbcutil, table, table+"_bak");
		DBTools.renameTable(jdbcutil, table+"_new", table);
		jdbcutil.closeConn();
	}

//	public static void saveOrUpdate(List<String[]> valueList, String table, String[] columns) {
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
//	}

	public static List<String> getRecListByDirIds(List<String> dirIds, int count, boolean doFilter) {
		List<String> recList = new ArrayList<String>();
		Set<String> dupFilter = new HashSet<String>();
		
		int colIndex = 0;
		while(true){
			boolean skip = true;
			
			for (String dirId : dirIds) {
				if(dirId==null) continue;
				List<String> stuffIdList = PcCache.dirIdPcStuffIdListMap.get(dirId);
				if(stuffIdList==null) continue;
				if(doFilter) stuffIdList = filterJD(stuffIdList);
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
			if (skip && recList.size()<count) {
				List<String> defaultList = PcCache.pcStuffIdDefault;
				if(doFilter) defaultList = filterJD(defaultList);
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
	
	private static void generateDirTree(){
		for(String dirId :Rec4youCache.stuffIdDirIdMap.values()){
			if(dirId==null || dirId.length()!=12) continue;
			String id1 = dirId.substring(0, 3);
			Map<String,Map<String,Map<String,String>>> id1Map = Rec4youCache.dirMap.get(id1);
			if(id1Map==null) {
				id1Map = Maps.newHashMap();
				Rec4youCache.dirMap.put(id1, id1Map);
			}
			String id2 = dirId.substring(3, 6);
			Map<String,Map<String,String>> id2Map = id1Map.get(id2);
			if(id2Map==null) {
				id2Map = Maps.newHashMap();
				id1Map.put(id2, id2Map);
			}
			String id3 = dirId.substring(6, 9);
			Map<String,String> id3Map = id2Map.get(id3);
			if(id3Map==null) {
				id3Map = Maps.newHashMap();
				id2Map.put(id3, id3Map);
			}
			String id4 = dirId.substring(9);
			if(id3Map.get(id4)==null) {
				id3Map.put(id4, dirId);
			}
		}
	}
	
	private static List<String> getRelatedDirIds(String dirId){
		List<String> result = Lists.newArrayList();
		if(dirId==null || dirId.length()!=12) return result;
		String d1 = dirId.substring(0, 3);
		String d2 = dirId.substring(3, 6);
		String d3 = dirId.substring(6, 9);
		String d4 = dirId.substring(9);
		
		if("100".equals(d1)){
			return result;
		} else if("100".equals(d2) || "100".equals(d3)) {
			Map<String,Map<String,Map<String,String>>> id1Map = Rec4youCache.dirMap.get(d1);
			for(String id2 : id1Map.keySet()){
				Map<String,Map<String,String>> id2Map = id1Map.get(id2);
				for(String id3 : id2Map.keySet()){
					Map<String,String> id3Map = id2Map.get(id3);
					for (String id4 : id3Map.keySet()) {
						String did = new StringBuffer(d1).append(id2).append(id3).append(id4).toString();
						if(!dirId.equals(did)) result.add(did);
					}
				}
			}
		} else if("100".equals(d4)) {
			Map<String,Map<String,Map<String,String>>> id1Map = Rec4youCache.dirMap.get(d1);
			Map<String,Map<String,String>> id2Map = id1Map.get(d2);
			for(String id3 : id2Map.keySet()){
				Map<String,String> id3Map = id2Map.get(id3);
				for (String id4 : id3Map.keySet()) {
					String did = new StringBuffer(d1).append(d2).append(id3).append(id4).toString();
					if(!dirId.equals(did)) result.add(did);
				}
			}
		} else {
			Map<String,Map<String,Map<String,String>>> id1Map = Rec4youCache.dirMap.get(d1);
			Map<String,Map<String,String>> id2Map = id1Map.get(d2);
			Map<String,String> id3Map = id2Map.get(d3);
			for (String id4 : id3Map.keySet()) {
				String did = new StringBuffer(d1).append(d2).append(d3).append(id4).toString();
				if(!dirId.equals(did)) result.add(did);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws SQLException {
		DetailRec.mainLogic();
	}

}

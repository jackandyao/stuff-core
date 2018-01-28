package com.qbao.aisr.stuff.algorithm.datahelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.utils.DBTools;
import com.qbao.aisr.stuff.utils.JDBCUtil;

public class DataHelperRec {

	
	public static void mainLogic(int n) throws SQLException {
//		generateDefaultRec(n);
		//rec_icf  rec_userdis  rec_similary  rec_directory  rec_agentspu
		shuffleAndSave("rec_directory","rec_zw_directory", n);
		shuffleAndSave("rec_similary","rec_zw_similary", n);
		shuffleAndSave("rec_userdis","rec_zw_userdis", n);
		shuffleAndSave("rec_icf","rec_zw_icf", n);
		shuffleAndSave("rec_agentspu","rec_zw_agentspu", n);
	}
	
//	private static void generateDefaultRec(int n) throws SQLException {
//		OfflineRec.retriveOuterStuffDefault();
//		List<String[]> list = Lists.newArrayList();
//		Collections.shuffle(Rec4youCache.stuffIdDefault);
//		List<String> defaultList = Rec4youCache.stuffIdDefault.subList(0, n);
//		list.add(new String[]{"0",String.join(",",defaultList)});
//		OfflineRec.saveOrUpdate(list, "rec_cloud_stuff", new String[]{"user_id", "stuff_ids"});
//	}
	
	private static void shuffleAndSave(String fromTable, String toTable, int count) throws SQLException{
		
		createTempTable(toTable);
		Map<String,List<List<String>>> restMap = new HashMap<String,List<List<String>>>();
		Map<String,List<String>> tempMap = generateRec(fromTable, count);
		for (Entry<String,List<String>> entry : tempMap.entrySet()) {
			String userId = entry.getKey();
			List<String> recList = entry.getValue();
			List<List<String>> list = restMap.get(userId);
			if(list==null) list = new ArrayList<List<String>>();
			list.add(recList);
			restMap.put(userId, list);
		}
		
		List<String[]> tempList = Lists.newArrayList();
		for (Entry<String,List<List<String>>> entry : restMap.entrySet()) {
			String userId = entry.getKey();
			List<String> restlist = new ArrayList<String>();
			List<List<String>> list = entry.getValue();
			if(CollectionUtils.isEmpty(list)) continue;
			Set<String> dupFilter = new HashSet<String>();
			int colIndex = 0;
			while(true){
				boolean skip = true;
				for (List<String> reclist : list) {
					if(reclist==null || reclist.size() <= colIndex) continue;
					skip = false;
					String recId = reclist.get(colIndex);
					if(!dupFilter.contains(recId)){
						restlist.add(recId);
						dupFilter.add(recId);
					}
				}
				colIndex++;
				if(skip) break;
				if(restlist.size()>=count){
					restlist = restlist.subList(0, count);
					break;
				}
			}
			tempList.add(new String[]{userId, String.join(",", restlist)});
			if(tempList.size()>=100000) {
				load(tempList, toTable);
				tempList.clear();
			}
		}
		if(tempList.size()>=0) {
			load(tempList, toTable);
			tempList.clear();
		}
		renameTempTable(toTable);
	}
	
	
	private static void createTempTable(String table) {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		DBTools.createTables(jdbcutil, table+"_new");
		jdbcutil.closeConn();
	}
	private static void load(List<String[]> tempList, String table){
		
		DBTools dbtools = new DBTools();
		
		OfflineRec.saveOrUpdate(dbtools, tempList);
		
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		String[] columns = new String[]{"user_id", "stuff_ids"};
		dbtools.loadCSV2Mysql(jdbcutil, table+"_new", columns);
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
	private static Map<String,List<String>> generateRec(String recTable, int count) throws SQLException{
		OfflineRec.retriveSpuMiddleData();
		OfflineRec.retriveOuterStuff(50);
		return generate(loadInnerResult(recTable), count);
	}
	
	private static Map<String,List<String>> loadInnerResult(String recTable) throws SQLException {
		Map<String,List<String>> resMap = new HashMap<String,List<String>>();
		String sql = " select user_id,spu_ids from " + recTable;
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnRec();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String userId = rs.getString(1);
			String spuIds = rs.getString(2);
			List<String> ids = new ArrayList<String>();
			String[] spuIdScores = spuIds.split(",");
			for (String spuIdScore : spuIdScores) {
				ids.add(spuIdScore.split(":")[0]);
			}
			resMap.put(userId, ids);
		}
		rs.close();
		jdbcutil.closeConn();
		return resMap;
	}
	private static Map<String,List<String>> generate(Map<String,List<String>> map, int count) {
		Map<String,List<String>> resMap = new HashMap<String,List<String>>();
		for (Entry<String, List<String>> userIdSpuIdListEntry : map.entrySet()) {
			String userId = userIdSpuIdListEntry.getKey();
			List<String> spuIds = userIdSpuIdListEntry.getValue();
			if(spuIds==null) continue;
			List<String> recList = OfflineRec.getRecListBySpuIds(spuIds, count, false, false);
			resMap.put(userId, recList);
		}
		return resMap;
	}
	
	
	public static void main(String[] args) throws SQLException {
		mainLogic(30);
	}
}

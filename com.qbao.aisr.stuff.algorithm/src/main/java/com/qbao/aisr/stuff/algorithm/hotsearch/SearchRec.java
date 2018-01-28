package com.qbao.aisr.stuff.algorithm.hotsearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qbao.aisr.stuff.algorithm.rec4you.offline.OfflineRec;
import com.qbao.aisr.stuff.cache.Rec4youCache;
import com.qbao.aisr.stuff.cache.SearchCache;
import com.qbao.aisr.stuff.utils.DBTools;
import com.qbao.aisr.stuff.utils.HttpUtil;
import com.qbao.aisr.stuff.utils.JDBCUtil;

public class SearchRec {

	private static Logger logger = Logger.getLogger("SearchRec");
	private static String table = "rec_search_stuff";
	public static void mainLogic() throws SQLException {
		
		createTempTable();
		logger.info("读取已有(关键词-类目)记录");
		loadKeywordDirId();
		logger.info("读取热搜词和个人搜索词表");
		loadSearchData();
		logger.info("匹配公共热搜类目和个人喜好类目");
		retriveLikeDir();
		OfflineRec.retriveOuterStuff(50);
		OfflineRec.retriveOuterStuffDefault();
		logger.info("读取站外商品，按类目和佣金排行" + Rec4youCache.dirIdStuffIdListMap.size());
		
		logger.info("生成搜索推荐结果并存储");
		shuffleRecAndSave();
		
		renameTempTable();
	}

	private static void loadKeywordDirId() throws SQLException {
		String sql = "select keyword,dirids from dict_keyword_dirids";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String keyword = rs.getString(1);
			String dirids = rs.getString(2);
			String[] dirs = dirids.split("\\D");
			SearchCache.keywordDiridsMap.put(keyword, Arrays.asList(dirs));
		}
		rs.close();
		jdbcutil.closeConn();
	}

	private static void loadSearchData() throws SQLException {
		loadTopSearchData();
		loadUserSearchData();
	}

	private static void loadTopSearchData() throws SQLException {
		String sql = "select `key` from top_search_key";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String keyword = rs.getString(1);
			SearchCache.topSearchKeywords.add(keyword);
		}
		rs.close();
		jdbcutil.closeConn();
	}

	private static void loadUserSearchData() throws SQLException {
		
		String sql = "select user_id,`key` from user_search order by id desc";
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		ResultSet rs = jdbcutil.getResultSet(sql);
		while(rs.next()){
			String userId = rs.getString(1);
			String keyword = rs.getString(2);
			List<String> keywordList = SearchCache.userSearchKeywordsMap.get(userId);
			if(keywordList == null) keywordList = new ArrayList<String>();
			keywordList.add(keyword);
			SearchCache.userSearchKeywordsMap.put(userId, keywordList);
		}
		rs.close();
		jdbcutil.closeConn();
	}
	
	private static void retriveLikeDir() {
		retriveHotLikeDir();
		retrivePersonalLikeDir();
	}

	private static void retriveHotLikeDir() {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		dealEachPersonLikeDir(SearchCache.topSearchKeywords, "0", jdbcutil);
		jdbcutil.closeConn();
	}
	
	private static void dealEachPersonLikeDir(List<String> keywords, String userId, JDBCUtil jdbcutil){
		if(keywords==null || keywords.size() == 0 ) return;
		for(String keyword : keywords){
			List<String> dirids = SearchCache.keywordDiridsMap.get(keyword);
			if(dirids==null){
				dirids = new ArrayList<String>();
				String json = HttpUtil.getHttpResponse(HttpUtil.URL_KEYWORD_DIRIDS+keyword);
				logger.info(json);
				JSONObject object = JSON.parseObject(json);
				if(!"ok".equals(object.getString("errorMsg"))) continue;
				JSONObject dataObject = object.getJSONObject("data");
				if(dataObject == null) continue;
				JSONArray dirList = dataObject.getJSONArray("dirProductList");
				for (int i = 0; i < dirList.size(); i++) {
					JSONObject dirObject = dirList.getJSONObject(i);
					if(dirObject == null) continue;
					String dirId = dirObject.getString("key");
					if(dirId == null) continue;
					dirids.add(dirId);
					
				}
//				if(dirids==null || dirids.size()==0) continue;
				String sql = "insert into dict_keyword_dirids(keyword,dirids) values('"+keyword+"', '"+String.join(",", dirids)+"')";
				jdbcutil.execute(sql);
				SearchCache.keywordDiridsMap.put(keyword, dirids);
			}
			
			if(dirids!=null && dirids.size()>0 && !StringUtils.isEmpty(dirids.get(0))) {
				for (String dirId : dirids) {
					List<String> keyDirIdList = SearchCache.userSearchDiridsMap.get(userId);
					if(keyDirIdList==null) keyDirIdList = new ArrayList<String>();
					keyDirIdList.add(dirId);
					SearchCache.userSearchDiridsMap.put(userId, keyDirIdList);
				}
			}
		}
	}
	
	private static void retrivePersonalLikeDir() {
		JDBCUtil jdbcutil = new JDBCUtil();
		jdbcutil.createConnStuff();
		for(Entry<String,List<String>> entry : SearchCache.userSearchKeywordsMap.entrySet()){
			dealEachPersonLikeDir(entry.getValue(), entry.getKey(), jdbcutil);
		}
		jdbcutil.closeConn();
	}
	
	private static void shuffleRecAndSave() {
		
		List<String[]> valueList = Lists.newArrayList();
		
		for(Entry<String, List<String>> entry : SearchCache.userSearchDiridsMap.entrySet()){
			List<String> recList = getRecListByDirids(entry.getValue(), 30);
			
			valueList.add(new String[]{entry.getKey(), String.join(",", recList)});
			if(valueList.size() > 100000){
				load(valueList);
				valueList.clear();
			}
		}
		
		if(valueList.size() > 0){
			load(valueList);
			valueList.clear();
		}
		
		
	}
	
//	private static void load(List<String[]> valueList){
//		
//		DBTools dbtools = new DBTools();
//		OfflineRec.saveOrUpdate(dbtools, valueList);
//		JDBCUtil jdbcutil = new JDBCUtil();
//		jdbcutil.createConnStuff();
//		String table = "rec_search_stuff";
//		String[] columns = new String[]{"user_id", "stuff_ids"};
//		dbtools.createTables(jdbcutil, table+"_new");
//		dbtools.loadCSV2Mysql(jdbcutil, table+"_new", columns);
//		dbtools.deleteTables(jdbcutil, table+"_bak");
//		dbtools.renameTable(jdbcutil, table, table+"_bak");
//		dbtools.renameTable(jdbcutil, table+"_new", table);
//		jdbcutil.closeConn();
//	}
	
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
	
	private static List<String> getRecListByDirids(List<String> dirids, int count) {
		List<String> recList = new ArrayList<String>();
		Set<String> dupFilter = new HashSet<String>();
		
		int colIndex = 0;
		while(true){
			boolean skip = true;
			for (String dirId : dirids) {
				if(dirId==null) continue;
				List<String> stuffIdList = Rec4youCache.dirIdStuffIdListMap.get(dirId);
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
				Collections.shuffle(Rec4youCache.stuffIdDefault);
				for (String stuffId : Rec4youCache.stuffIdDefault) {
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

	public static void main(String[] args) throws SQLException {
		SearchRec.mainLogic();
	}
}

package com.qbao.aisr.stuff.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCache {

	public static Map<String,List<String>> keywordDiridsMap = new HashMap<String,List<String>>();
	public static List<String> topSearchKeywords = new ArrayList<String>();
	public static Map<String, List<String>> userSearchKeywordsMap = new HashMap<String, List<String>>();
	
	public static Map<String, List<String>> userSearchDiridsMap = new HashMap<String, List<String>>();
	
}

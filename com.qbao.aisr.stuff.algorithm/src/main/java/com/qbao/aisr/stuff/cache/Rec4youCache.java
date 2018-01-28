package com.qbao.aisr.stuff.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class Rec4youCache {
	public static Map<String,String> spuIdDirIdMap = new HashMap<String,String>();
	public static List<String> stuffIdDefault = new ArrayList<String>();
	public static Map<String,List<String>> dirIdStuffIdListMap = new HashMap<String,List<String>>();
	public static Map<String,String> stuffIdDirIdMap = new HashMap<String,String>();
//	public static Map<String,List<String>> userIdSpuIdListMap = new HashMap<String,List<String>>();
	
	public static Map<String,List<String>> userIdSpuIdBuyListMap = new HashMap<String,List<String>>();
	public static Map<String,List<String>> userIdSpuIdViewListMap = new HashMap<String,List<String>>();
	
	public static Map<String,Map<String,Map<String,Map<String,String>>>> dirMap = Maps.newHashMap();
	
}

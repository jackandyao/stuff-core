package com.qbao.aisr.stuff.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public class OnedayCache {

	public static Set<String> couponStuffIdSet = Sets.newHashSet();
	public static Map<String,List<String>> dirIdCouponStuffIdListMap = new HashMap<String,List<String>>();
	public static List<String> couponStuffIdDefault = new ArrayList<String>();
}

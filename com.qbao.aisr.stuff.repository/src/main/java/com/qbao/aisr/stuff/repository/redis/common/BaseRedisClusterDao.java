package com.qbao.aisr.stuff.repository.redis.common;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseRedisClusterDao<T> extends AbstractBaseRedisClusterDao<String, String>{
	Logger logger = LoggerFactory.getLogger(BaseRedisClusterDao.class);
    
	public static ObjectMapper objectMapper;

	public void insert(String key,T t){
	    String value = this.toJson(t);
		this.jedisCluster.set(key,value);
		logger.info("save key:"+key+" ! value:"+value);
	}
	
	public void del(String key){
	    logger.info("delete redis key:"+key);
	    this.jedisCluster.del(key);
	}
	
	public T get(String key,Class<T> type){
		String json = this.jedisCluster.get(key);
		logger.info("get success ! value:"+json);
		if(json != null){
			return this.toObject(json, type);
		}else{
			return null;
		}
	}
	
	
	private String toJson( T t){
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		try {
			return objectMapper.writeValueAsString(t);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	} 
	
	private T toObject(String json,Class<T> type){
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		try {
			return objectMapper.readValue(json,type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

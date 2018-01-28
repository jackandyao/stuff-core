package com.qbao.aisr.stuff.repository.redis.common;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**  
 * AbstractBaseRedisClusterDao 
 * @author shuaizhihu
 * @date 2016-04-27 
 */   
public abstract class AbstractBaseRedisClusterDao<K, V> { 
    
     @Autowired
     protected JedisCluster jedisCluster;
        

}  
/**
 * 
 */
package com.qbao.aisr.stuff.repository.redis.common;

/**
 * @author shuaizhihu
 *
 * $LastChangedDate: 2016-09-05 18:26:32 +0800 (Mon, 05 Sep 2016) $
 * $LastChangedRevision: 896 $
 * $LastChangedBy: jiahongping $
 */
public class RedisModel<T>   {
    
    private String tableName;
    private String key;
    private T value;
    
    public RedisModel(String key,T t){
        this.key = key;
        this.value = t;
        this.tableName=t.getClass().getName();
    }
    
    public RedisModel(String key,Class<T> type){
        this.key = key;
        this.tableName=type.getName();
    }
    
 
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public T getValue() {
        return value;
    }
    @SuppressWarnings("unchecked")
    public void setValue(Object value) {
        this.value = (T) value;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "tablename:"+tableName+"\t"+"key:"+key+"\t"+"value:"+value;
    }
    
    
    
    
    
}

/**
 * 
 */
package com.qbao.aisr.stuff.repository.mybatis.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author shuaizhihu
 *
 * $LastChangedDate: 2016-06-21 18:21:57 +0800 (周二, 21 六月 2016) $
 * $LastChangedRevision: 29 $
 * $LastChangedBy: shuaizhihu $
 */
public class MultipleDataSource extends AbstractRoutingDataSource{
    
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    /* (non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }

}

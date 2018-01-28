package com.qbao.aisr.stuff.crawler.cmp.persist;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 16:33
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class PersistTask<T> {
    private T persistData;

    public T getPersistData() {
        return persistData;
    }

    public void setPersistData(T persistData) {
        this.persistData = persistData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("persistData", persistData).toString();
    }
}

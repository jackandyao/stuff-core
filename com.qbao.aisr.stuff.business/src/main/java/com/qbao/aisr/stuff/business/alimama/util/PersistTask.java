package com.qbao.aisr.stuff.business.alimama.util;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 16:33
 * $$LastChangedDate: 2017-05-21 18:37:40 +0800 (Sun, 21 May 2017) $$
 * $$LastChangedRevision: 935 $$
 * $$LastChangedBy: liaijun $$
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

package com.qbao.aisr.stuff.business.alimama.util;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by shuaizhihu on 2017/2/28.
 */
public class DownloadTask<T> {

    private String entryUrl;

    private DlMethod method;

    private T data;


    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    public DlMethod getMethod() {
        return method;
    }

    public void setMethod(DlMethod method) {
        this.method = method;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("entryUrl", entryUrl).append("method", method).toString();
    }
}

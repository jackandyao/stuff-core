package com.qbao.aisr.stuff.crawler.cmp.download;

import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.ResponseType;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.login.LoginForm;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

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

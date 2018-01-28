package com.qbao.aisr.stuff.business.alimama.util;

import java.io.Serializable;

/**
 * @author liaijun
 * @createTime 17/5/27 上午11:11
 * $$LastChangedDate: 2017-05-27 11:14:26 +0800 (Sat, 27 May 2017) $$
 * $$LastChangedRevision: 1023 $$
 * $$LastChangedBy: liaijun $$
 */
public class Result implements Serializable{
    private static final long serialVersionUID="$Id: Result.java 1023 2017-05-27 03:14:26Z liaijun $".hashCode();
    private String url;
    private String operatorSource;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperatorSource() {
        return operatorSource;
    }

    public void setOperatorSource(String operatorSource) {
        this.operatorSource = operatorSource;
    }

    @Override
    public String toString() {
        return "Result{" +
                "url='" + url + '\'' +
                ", operatorSource='" + operatorSource + '\'' +
                '}';
    }
}

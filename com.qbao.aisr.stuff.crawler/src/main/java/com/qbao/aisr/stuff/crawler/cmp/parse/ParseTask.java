package com.qbao.aisr.stuff.crawler.cmp.parse;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 16:31
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class ParseTask<T> {
    private T data;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("data", data).append("content", content).toString();
    }
}

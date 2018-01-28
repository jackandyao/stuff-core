package com.qbao.aisr.stuff.crawler.cmp.provide;

import com.qbao.aisr.stuff.crawler.context.CrawlerContext;

import java.io.UnsupportedEncodingException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 18:11
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public interface ICrawlerProvide {

    public void provide(CrawlerContext crawlerContext) throws Exception;

}

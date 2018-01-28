package com.qbao.aisr.stuff.crawler.factory.tbk;

import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;

import java.io.IOException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 18:06
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class TbkOrderDetailCrawlerFactory extends CrawlerAbstractFactory {


    @Override
    public CrawlerContext init() throws IOException, CrawlerException, HttpProcessException {
        super.crawlerContext = new CrawlerContext(new CrawlerConfig("alimama_order_detail_crawler.properties"));
        return super.crawlerContext;
    }
}

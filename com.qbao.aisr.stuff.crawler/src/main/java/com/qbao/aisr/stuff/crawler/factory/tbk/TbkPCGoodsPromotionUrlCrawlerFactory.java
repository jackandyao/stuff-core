package com.qbao.aisr.stuff.crawler.factory.tbk;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 18:06
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="tbkPCGoodsPromotionUrlCrawlerFactory")
public class TbkPCGoodsPromotionUrlCrawlerFactory extends CrawlerAbstractFactory {


    @Override
    public CrawlerContext init() throws IOException, CrawlerException, HttpProcessException {
        super.crawlerContext = new CrawlerContext(new CrawlerConfig("alimama_PC_goods_promotionurl_crawler.properties"));
        return super.crawlerContext;
    }
}

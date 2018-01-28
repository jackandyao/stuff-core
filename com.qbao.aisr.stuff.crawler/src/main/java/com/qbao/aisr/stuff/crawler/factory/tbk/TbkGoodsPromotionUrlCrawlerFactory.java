package com.qbao.aisr.stuff.crawler.factory.tbk;

import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 18:06
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="tbkGoodsPromotionUrlCrawlerFactory")
public class TbkGoodsPromotionUrlCrawlerFactory extends CrawlerAbstractFactory {


    @Override
    public CrawlerContext init() throws IOException, CrawlerException, HttpProcessException {
        super.crawlerContext = new CrawlerContext(new CrawlerConfig("alimama_goods_promotionurl_crawler.properties"));
        return super.crawlerContext;
    }
}

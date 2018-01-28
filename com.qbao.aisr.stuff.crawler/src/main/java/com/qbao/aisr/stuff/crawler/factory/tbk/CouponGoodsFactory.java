package com.qbao.aisr.stuff.crawler.factory.tbk;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;

/**
 * Created by xueming on 2017/3/9.
 */
@Component(value="couponGoodsFactory")
public class CouponGoodsFactory extends CrawlerAbstractFactory{

    @Override
    public CrawlerContext init() throws IOException, CrawlerException {

        super.crawlerContext = new CrawlerContext(new  CrawlerConfig("alimama_coupon_crawler.properties"));

        return super.crawlerContext;
    }


}

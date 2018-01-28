package com.qbao.aisr.stuff.crawler.factory.jdlmeng;

import com.qbao.aisr.stuff.common.spring.SpringApplicationManager;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.thread.DownloadThread;
import com.qbao.aisr.stuff.crawler.thread.ParseThread;
import com.qbao.aisr.stuff.crawler.thread.PersistThread;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by xueming on 2017/3/9.
 */
@Component(value="jdlmengCrawlerAbstractFactory")
public class JdlmengCrawlerAbstractFactory extends CrawlerAbstractFactory{
    @Override
    public CrawlerContext init() throws IOException, CrawlerException {
        crawlerContext = new CrawlerContext(new  CrawlerConfig("jdlmeng_goods_crawler.properties"));
        return crawlerContext;
    }


}

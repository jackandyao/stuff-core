package com.qbao.aisr.stuff.crawler.factory;

import com.qbao.aisr.stuff.common.spring.SpringApplicationManager;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.thread.DownloadThread;
import com.qbao.aisr.stuff.crawler.thread.ParseThread;
import com.qbao.aisr.stuff.crawler.thread.PersistThread;
import com.qbao.aisr.stuff.crawler.thread.ProviderThread;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by xueming on 2017/3/9.
 */
public abstract class CrawlerAbstractFactory {

    protected CrawlerContext crawlerContext;

    protected HttpConfig httpConfig;

    @Resource(name="springApplicationManager")
    protected SpringApplicationManager springApplicationManager;

    public abstract CrawlerContext init() throws IOException, CrawlerException, HttpProcessException;



    public void createDownThread()  throws CrawlerException, InterruptedException{
        int downThreadNum = crawlerContext.crawlerConfig.getDownload_thread_num();
        IDownloadService iDownloadService = (IDownloadService) springApplicationManager.getApplicationContext().getBean(crawlerContext.crawlerConfig.getDl_service_clazz());
        for(int i=0;i<(downThreadNum==0?1:downThreadNum);i++){
            crawlerContext.dlThreadPoolExecutor.execute(new DownloadThread(crawlerContext,httpConfig,iDownloadService));
        }
    }


    public void createParseThread()  throws CrawlerException, InterruptedException {
        int parseThreadNum = crawlerContext.crawlerConfig.getParse_thread_num();
        IParseService iParseService = (IParseService) springApplicationManager.getApplicationContext().getBean(crawlerContext.crawlerConfig.getParse_service_clazz());
        for(int i=0;i<(parseThreadNum==0?1:parseThreadNum);i++){
            crawlerContext.parseThreadPoolExecutor.execute(new ParseThread(crawlerContext,iParseService));
        }
    }


    public void createPersistThread()  throws CrawlerException, InterruptedException {
        int persistThreadNum = crawlerContext.crawlerConfig.getSave_thread_num();
        IPersistService iPersistService = (IPersistService) springApplicationManager.getApplicationContext().getBean(crawlerContext.crawlerConfig.getPersist_service_clazz());
        for(int i=0;i<(persistThreadNum==0?1:persistThreadNum);i++){
            crawlerContext.persistThreadPoolExecutor.execute(new PersistThread(crawlerContext,iPersistService));
        }
    }

    public void createProviderThread(){
        ICrawlerProvide iCrawlerProvide = (ICrawlerProvide) springApplicationManager.getApplicationContext().getBean(crawlerContext.crawlerConfig.getDl_task_provide_clazz());
        crawlerContext.provideThreadPoolExecutor.execute(new ProviderThread(iCrawlerProvide,crawlerContext));
    }
}

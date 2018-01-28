package com.qbao.aisr.stuff.crawler;

import com.qbao.aisr.stuff.common.spring.SpringApplicationManager;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 16:59
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service
public class CrawlerService {

    Logger logger  = Logger.getLogger(CrawlerService.class);

    private CrawlerContext crawlerContext;

    private HttpConfig httpConfig;

    @Resource(name="springApplicationManager")
    SpringApplicationManager springApplicationManager;

    public void init(CrawlerAbstractFactory crawlerAbstractFactory) throws IOException, CrawlerException, HttpProcessException {
         //crawlerContext = new CrawlerContext(new  CrawlerConfig(configFilePath));
        crawlerContext = crawlerAbstractFactory.init();
    }

    public void start(CrawlerAbstractFactory crawlerAbstractFactory) throws Exception {
        logger.info("爬虫任务启动...");
        if(crawlerContext==null){
            logger.error("crawlerContext must init first!");
            throw new CrawlerException("crawlerContext must init first");
        }

        crawlerAbstractFactory.createProviderThread();

        crawlerAbstractFactory.createDownThread();

        crawlerAbstractFactory.createParseThread();

        crawlerAbstractFactory.createPersistThread();

        while(!crawlerContext.provideThreadPoolExecutor.isShutdown()){
            logger.info("循环等待生产者线程结束！");
            int activeCount = crawlerContext.provideThreadPoolExecutor.getActiveCount();
            logger.info("生产线程个数 "+activeCount);
            if(activeCount == 0){
                crawlerContext.provideThreadPoolExecutor.shutdownNow();
            }
            Thread.sleep(5000);
        }

        while(crawlerContext.dlTaskQueue.size()>0){
            logger.info("循环等待下载任务消费结束！");
            Thread.sleep(5000);
        }


        while(crawlerContext.parseTaskQueue.size()>0){
            logger.info("循环等待解析任务队列消费结束！");
            Thread.sleep(5000);
        }


        while(crawlerContext.persistTaskQueue.size()>0){
            logger.info("循环等待持久化任务队列消费结束！");
            Thread.sleep(5000);
        }

        crawlerContext.dlThreadPoolExecutor.shutdownNow();
        crawlerContext.parseThreadPoolExecutor.shutdownNow();
        crawlerContext.persistThreadPoolExecutor.shutdownNow();

        logger.info("爬虫任务结束...");
    }
}

package com.qbao.aisr.stuff.crawler.thread;

import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.redis.cache.annotation.CacheType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:52
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class PersistThread extends Thread {
    Logger logger = LoggerFactory.getLogger(PersistThread.class);
    CrawlerContext crawlerContext;
    IPersistService iPersistService;

    public PersistThread(CrawlerContext crawlerContext,IPersistService iPersistService){
        this.crawlerContext=crawlerContext;
        this.iPersistService=iPersistService;
    }

    public void run(){
        while(true) {
            PersistTask task = null;
            try {
                task = crawlerContext.persistTaskQueue.take();
                iPersistService.persist(task);
                if(crawlerContext.persistTaskQueue.size()==0){
                    iPersistService.persistOver();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }  catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}

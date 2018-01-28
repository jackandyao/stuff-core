package com.qbao.aisr.stuff.crawler.thread;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:39
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class ParseThread extends Thread {
    Logger logger = LoggerFactory.getLogger(ParseThread.class);
    CrawlerContext crawlerContext;
    IParseService iParseService;
    ParseContext context;

    public ParseThread(  CrawlerContext crawlerContext,IParseService iParseService) {
        this.crawlerContext = crawlerContext;
        this.iParseService=iParseService;
    }

    public void run(){
        while(true) {
            ParseTask task = null;
            try {
                task = crawlerContext.parseTaskQueue.take();
                context=new ParseContext(task);
                if(iParseService.parse(context)){
                    List list = context.getParseResult();
                    for(Object o:list){
                        PersistTask persistTask = new PersistTask();
                        persistTask.setPersistData(o);
                        crawlerContext.persistTaskQueue.put(persistTask);
                    }
                }else{
                    logger.info("解析失败！"+task.toString());
                }
                if(crawlerContext.parseTaskQueue.size()==0){
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

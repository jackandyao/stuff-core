package com.qbao.aisr.stuff.crawler.thread;

import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 11:18
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class ProviderThread extends Thread{
    Logger logger = LoggerFactory.getLogger(ProviderThread.class);

    ICrawlerProvide iCrawlerProvide;

    CrawlerContext crawlerContext;

    public ProviderThread(ICrawlerProvide iCrawlerProvide,CrawlerContext crawlerContext){
        this.iCrawlerProvide = iCrawlerProvide;
        this.crawlerContext = crawlerContext;
    }

    public void run(){
        try {
            iCrawlerProvide.provide(crawlerContext);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下载任务队列创建失败！");
        }

    }
}

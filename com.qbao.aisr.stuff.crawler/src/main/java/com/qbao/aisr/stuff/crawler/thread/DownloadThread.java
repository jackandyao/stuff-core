package com.qbao.aisr.stuff.crawler.thread;

import com.qbao.aisr.stuff.common.spring.SpringApplicationManager;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 17:30
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class DownloadThread extends Thread {
    Logger logger = LoggerFactory.getLogger(DownloadThread.class);
    CrawlerContext crawlerContext;
    DownloadContext downloadContext;
    IDownloadService iDownloadService;

    public DownloadThread(CrawlerContext crawlerContext,HttpConfig httpConfig,IDownloadService iDownloadService) throws CrawlerException {
        this.crawlerContext = crawlerContext;
        this.downloadContext = new DownloadContext(httpConfig);
        this.iDownloadService = iDownloadService;
    }

    public void run(){
        while(true) {
            DownloadTask task = null;
            try {
                task = crawlerContext.dlTaskQueue.take();
                downloadContext.setTask(task);
                if(iDownloadService.downLoad(downloadContext)){
                    ParseTask parseTask = new  ParseTask();
                    parseTask.setData( task.getData());
                    parseTask.setContent(downloadContext.getResponseContent());
                    crawlerContext.parseTaskQueue.put(parseTask);
                }else{
                    logger.info("下载失败！ "+task.toString());
                    if(crawlerContext.dlTaskQueue.size()<crawlerContext.crawlerConfig.getMax_dl_queue_size()){
                        logger.info("重新放入队列！");
                        crawlerContext.dlTaskQueue.put(task);
                    }
//
                }

                try {
                    Thread.sleep((long) (Math.random()*crawlerContext.crawlerConfig.getDl_time_sleep()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (CrawlerException e) {
                e.printStackTrace();
            }  catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();

            }

        }
    }
}

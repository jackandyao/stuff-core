package com.qbao.aisr.stuff.crawler.context;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.crawler.configure.CrawlerConfig;

import java.net.HttpCookie;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 15:31
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class CrawlerContext {
    /**
     *  下载任务队列
     */
    public BlockingQueue<DownloadTask> dlTaskQueue = null;

    /**
     * 解析任务队列
     */
    public BlockingQueue<ParseTask> parseTaskQueue =  null;

    /**
     * 解析任务队列
     */
    public BlockingQueue<PersistTask> persistTaskQueue =  null;


    public CrawlerConfig crawlerConfig;

    public HttpCookies httpCookies;

    /**
     * 生产线程池
     */
    public ThreadPoolExecutor provideThreadPoolExecutor;

    /**
     * 下载线程池
     */
    public ThreadPoolExecutor dlThreadPoolExecutor;


    /**
     * 解析线程池
     */
    public ThreadPoolExecutor parseThreadPoolExecutor;


    /**
     * 持久化线程池
     */
    public ThreadPoolExecutor persistThreadPoolExecutor;



    public CrawlerContext(CrawlerConfig config){
        dlTaskQueue = new ArrayBlockingQueue<DownloadTask>(config.getMax_dl_queue_size()==0?1000:config.getMax_dl_queue_size());
        parseTaskQueue = new ArrayBlockingQueue<ParseTask> (config.getMax_parse_queue_size()==0?1000:config.getMax_parse_queue_size());
        persistTaskQueue=new ArrayBlockingQueue<PersistTask>(config.getMax_persist_queue_size()==0?1000:config.getMax_persist_queue_size());
        provideThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        dlThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(config.getDownload_thread_num()==0?1:config.getDownload_thread_num());
        parseThreadPoolExecutor=(ThreadPoolExecutor) Executors.newFixedThreadPool(config.getParse_thread_num()==0?1:config.getParse_thread_num());
        persistThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(config.getSave_thread_num()==0?1:config.getSave_thread_num());

        crawlerConfig = config;
    }


}

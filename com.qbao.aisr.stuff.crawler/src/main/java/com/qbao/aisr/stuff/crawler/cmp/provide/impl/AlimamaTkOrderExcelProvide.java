package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/15 17:44
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Component(value="alimamaTkOrderExcelProvide")
public class AlimamaTkOrderExcelProvide implements ICrawlerProvide {

    public Logger logger = LoggerFactory.getLogger(AlimamaTkOrderExcelProvide.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void provide(CrawlerContext crawlerContext) throws Exception {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR,-1);
        Date y = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR,-89);
        Date s = cal.getTime();
        String url = "http://pub.alimama.com/report/getTbkPaymentDetails.json?spm=&queryType=1&payStatus=&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime="+format.format(s)+"&endTime="+format.format(y);
        logger.info(url);
        DownloadTask<String> downloadTask = new DownloadTask<>();
        downloadTask.setData(format.format(now));
        downloadTask.setEntryUrl(url);
        downloadTask.setMethod(DlMethod.GET);
        crawlerContext.dlTaskQueue.add(downloadTask);
    }
}

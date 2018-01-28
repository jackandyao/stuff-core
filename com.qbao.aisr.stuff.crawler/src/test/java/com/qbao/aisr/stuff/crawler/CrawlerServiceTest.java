package com.qbao.aisr.stuff.crawler;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsPromotionUrlCrawlerFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkOrderDetailCrawlerFactory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/6 12:08
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring_crawler.xml"})
public class CrawlerServiceTest {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    CrawlerService crawlerService;

    @Resource(name="alimamaOrderDetailDDownloadService")
    IDownloadService iDownloadService;
    @Value("${tbk.table.name}")
    private String tableName;
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;


    public void test() throws Exception {
        //crawlerService.init("alimama_goods_crawler.properties");
        //crawlerService.start();
        CrawlerAbstractFactory crawlerAbstractFactory = new TbkGoodsFactory();
        crawlerService.init(crawlerAbstractFactory);
        crawlerService.start(crawlerAbstractFactory);
    }


    public void promotionTest() throws Exception {
        //crawlerService.init("alimama_goods_crawler.properties");
        //crawlerService.start();
        CrawlerAbstractFactory crawlerAbstractFactory = new TbkGoodsPromotionUrlCrawlerFactory();
        crawlerService.init(crawlerAbstractFactory);
        crawlerService.start(crawlerAbstractFactory);
    }


    public void orderDetailTest() throws Exception {
        CrawlerAbstractFactory crawlerAbstractFactory = new TbkOrderDetailCrawlerFactory();
        crawlerService.init(crawlerAbstractFactory);
        crawlerService.start(crawlerAbstractFactory);
    }

    @Test
    public void rename(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        stuffPromotionTmpDao.rename(tableName,format.format(new Date()));
    }


    public void downloadExcel() throws CrawlerException, HttpProcessException {
        DownloadTask task = new DownloadTask();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR,-1);
        Date y = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR,-89);
        Date s = cal.getTime();
        String url = "http://pub.alimama.com/report/getTbkPaymentDetails.json?spm=&queryType=1&payStatus=&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime="+format.format(s)+"&endTime="+format.format(y);
        task.setEntryUrl(url);
        System.out.println(url);
        DownloadContext context = new DownloadContext(HttpConfig.custom());
        context.setTask(task);
        System.out.println(iDownloadService.downLoad(context));
        System.out.println(context.getResponseContent());
    }
}

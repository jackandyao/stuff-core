package com.qbao.aisr.stuff.crawler.run;

import com.qbao.aisr.stuff.common.stuffenum.AlimamaOrderStatus;
import com.qbao.aisr.stuff.crawler.cmp.download.AlimamaOrderDetailDowLoadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.download.impl.AlimamaOrderDetailDDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.parse.IParseService;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseContext;
import com.qbao.aisr.stuff.crawler.cmp.parse.ParseTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.TaokeDetail;
import com.qbao.aisr.stuff.util.message.NotifierUtil;
import com.qbao.aisr.stuff.util.message.SendPhoneMessageUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/20 10:29
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@SuppressWarnings("all")
public class OrderDetailRunner {
    public static Logger logger = Logger.getLogger(OrderDetailRunner.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String paths[] = new String[] { "spring_crawler.xml" };
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        AlimamaOrderDetailDDownloadService iDownloadService = ctx.getBean("alimamaOrderDetailDDownloadService", AlimamaOrderDetailDDownloadService.class);
        IParseService iParseService = ctx.getBean("alimamaOrderDetailParseService", IParseService.class);
        IPersistService iPersistService = ctx.getBean("taokeDetailPersistService", IPersistService.class);
        long start = System.currentTimeMillis();
        String info = "Starting alimama account =["+iDownloadService.getUserName()+"] OrderDetails job ...";
        logger.info(info);
        Date now = new Date();
        SendPhoneMessageUtil.sendPhoneMessage(sdf.format(now) + " : " + info);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date y = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -89);
        Date s = cal.getTime();
        AlimamaOrderStatus[] orderStatusList = AlimamaOrderStatus.values();
        for (AlimamaOrderStatus status : orderStatusList) {
            String url = "http://pub.alimama.com/report/getTbkPaymentDetails.json?spm=&queryType=1&payStatus=" + status.getCode() + "&DownloadID=DOWNLOAD_REPORT_INCOME_NEW&startTime=" + format.format(s) + "&endTime=" + format.format(y);
            logger.info("download alimama " + status.getName() + " and the url   " + url);
            DownloadTask<String> downloadTask = new DownloadTask<>();
            downloadTask.setData(format.format(now));
            downloadTask.setEntryUrl(url);
            downloadTask.setMethod(DlMethod.GET);
            AlimamaOrderDetailDowLoadContext context = new AlimamaOrderDetailDowLoadContext(HttpConfig.custom());
            context.setTask(downloadTask);
            context.setOrderStatus(status);
            iDownloadService.downLoad(context);
            ParseTask parseTask = new ParseTask();
            parseTask.setContent(context.getResponseContent());
            ParseContext<TaokeDetail> parseContext = new ParseContext<>(parseTask);
            iParseService.parse(parseContext);
            try {

                int taskNumer = 0;
                for (TaokeDetail taokeDetail : parseContext.getParseResult()) {
                    PersistTask persistTask = new PersistTask();
                    persistTask.setPersistData(taokeDetail);
                    iPersistService.persist(persistTask);
                    taskNumer++;
                    logger.info("PersistTask task = [" + taskNumer + "] is done");
                }
                final String message = "finished alimama account= ["+iDownloadService.getUserName()+"], and [" + status.getName() + "] OrderDetails job , total [" + taskNumer + "] tasks are presist done! and cost " + (System.currentTimeMillis() - start) / 1000 + ".s";
                logger.info(message);
                SendPhoneMessageUtil.sendPhoneMessage(sdf.format(new Date()) + ":" + message);
            } catch (Exception ex) {
                String topic = sdf.format(new Date()) + ": 阿里妈妈[" + iDownloadService.getUserName() + "] and [" + status.getName() + "],对账任务失败.";
                NotifierUtil.notifyByEmail(topic,ex.getMessage());
                SendPhoneMessageUtil.sendPhoneMessage(topic + ExceptionUtils.getRootCause(ex));
            }
        }
    }

}

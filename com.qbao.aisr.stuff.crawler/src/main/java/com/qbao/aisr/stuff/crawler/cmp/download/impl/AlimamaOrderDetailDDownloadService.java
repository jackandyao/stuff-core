package com.qbao.aisr.stuff.crawler.cmp.download.impl;

import com.qbao.aisr.stuff.common.stuffenum.AlimamaOrderStatus;
import com.qbao.aisr.stuff.crawler.cmp.download.AlimamaOrderDetailDowLoadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.DownloadContext;
import com.qbao.aisr.stuff.crawler.cmp.download.IDownloadService;
import com.qbao.aisr.stuff.crawler.cmp.http.HttpClientUtil;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpConfig;
import com.qbao.aisr.stuff.crawler.cmp.http.common.HttpCookies;
import com.qbao.aisr.stuff.crawler.cmp.http.exception.HttpProcessException;
import com.qbao.aisr.stuff.crawler.cmp.login.ILoginService;
import com.qbao.aisr.stuff.crawler.exception.CrawlerException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author shuaizhihu
 * @createTime 2017/3/15 18:32
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value = "alimamaOrderDetailDDownloadService")
public class AlimamaOrderDetailDDownloadService implements IDownloadService {

    private static HttpCookies httpCookies;
    Logger logger = Logger.getLogger(AlimamaOrderDetailDDownloadService.class);
    private Random random = new Random();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Resource(name = "alimamaLoginServiceImpl")
    ILoginService iLoginService;
    @Value("${tbk.alimama.username}")
    private String userName;
    @Value("${tbk.alimama.userpass}")
    private String userPass;
    @Value("${tbk.alimama.phone}")
    private String userPhone;
    private HttpConfig httpConfig;

    @Override
    public boolean downLoad(DownloadContext context) throws CrawlerException, HttpProcessException {

        try {
            AlimamaOrderDetailDowLoadContext orderDetailDowLoadContext = (AlimamaOrderDetailDowLoadContext) context;
            AlimamaOrderStatus orderStatus = orderDetailDowLoadContext.getOrderStatus();
            File f = new File("/tmp/" + File.separator + "tbkDetail_" + format.format(new Date()) + "_" + orderStatus.getName()+"_" +userName+ ".xls");
            long fileSize = 0;
            int times = 1;
            long minFileSize = 0;
            if (orderStatus == AlimamaOrderStatus.FAIL || orderStatus == AlimamaOrderStatus.PAY) {
                minFileSize = 10240;
            } else {
                minFileSize = 10240;
            }

            while (fileSize < minFileSize) {
                if (f.exists()) {
                    f.delete();
                }
                Thread.sleep(random.nextInt(10000)+5000);
                String entryUrl = context.getHttpConfig().url();
                if (httpCookies == null || times % 11 == 0) {
                    httpCookies = null;
                    this.login();
                }
                httpConfig = iLoginService.getConfig();
                HttpClientUtil.down(httpConfig.url(entryUrl).out(new FileOutputStream(f)));
                context.setResponseContent(f.getAbsolutePath());
                fileSize = FileUtils.sizeOf(f);
                times++;
                logger.info("try " + times + " times to download alimam order details .. and the file size is = [" + fileSize + "]");
            }
            return true;
        } catch (HttpProcessException e) {
            logger.error(e);
            return false;
        } catch (InterruptedException e) {
            logger.error(e);
            return false;
        } catch (FileNotFoundException e) {
            logger.error(e);
            return false;
        }

    }

    private synchronized void login() throws HttpProcessException, InterruptedException {
        if (httpCookies == null) {
            while (!iLoginService.loginWithQrCode()) {
                Thread.sleep(random.nextInt(10000)+5000);
            }
            httpCookies = iLoginService.getCookies();
            httpConfig = iLoginService.getConfig();
        }

    }

    public String getUserName() {
        return userName;
    }
    public  static  void main(String[] args){
        File file = new File("/tmp/tbkDetail_2017-06-19_订单结算_tyoukin.xls");

        System.out.println(FileUtils.sizeOf(file));
    }
}

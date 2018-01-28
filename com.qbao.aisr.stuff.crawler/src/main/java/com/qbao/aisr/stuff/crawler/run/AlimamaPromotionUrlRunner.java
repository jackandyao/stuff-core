package com.qbao.aisr.stuff.crawler.run;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.qbao.aisr.stuff.config.App;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qbao.aisr.stuff.crawler.CrawlerService;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsPromotionUrlCrawlerFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkIosGoodsPromotionUrlCrawlerFactory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;

/**
 * @author shuaizhihu
 * @createTime 2017/3/17 18:23
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@SuppressWarnings("all")
public class AlimamaPromotionUrlRunner {

    public static void main(String[] args) throws Exception {

        String paths[] = new String[]{"spring_crawler.xml"};
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

        CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
        StuffPromotionTmpDao stuffPromotionTmpDao =  ctx.getBean(StuffPromotionTmpDao.class);
        Properties prop = new Properties();//属性集合对象
        InputStream fis;
        fis = App.class.getClassLoader().getResourceAsStream("tbk.properties");
        prop.load(fis);//将属性文件流装载到Properties对象中
        String tableName=prop.getProperty("tbk.table.name");
        System.out.println("临时表stuff_promotion_tmp_${name}后缀名 ${name}="+tableName);
        if(StringUtils.isBlank(tableName)){
            System.out.println("请输入临时表stuff_promotion_tmp_${name}后缀名${name}:");
            Scanner scanner = new Scanner(System.in);
            tableName=scanner.next();
            System.out.println("你输入的临时表stuff_promotion_tmp_${name}后缀名 ${name}="+tableName);
        }
        Thread iosThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("============IOS运行================");
                String paths[] = new String[]{"spring_crawler.xml"};
                //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
                ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

                CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
                //ios
                CrawlerAbstractFactory tbkIosGoodsPromotionUrlCrawlerFactory = ctx.getBean("tbkIosGoodsPromotionUrlCrawlerFactory", TbkIosGoodsPromotionUrlCrawlerFactory.class);
                try {
                    crawlerService.init(tbkIosGoodsPromotionUrlCrawlerFactory);
                    crawlerService.start(tbkIosGoodsPromotionUrlCrawlerFactory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        iosThread.start();


        Thread androidThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("============android运行================");
                String paths[] = new String[]{"spring_crawler.xml"};
                //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
                ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

                CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
                //android
                CrawlerAbstractFactory tbkGoodsPromotionUrlCrawlerFactory = ctx.getBean("tbkGoodsPromotionUrlCrawlerFactory",TbkGoodsPromotionUrlCrawlerFactory.class);
                try {
                    crawlerService.init(tbkGoodsPromotionUrlCrawlerFactory);
                    crawlerService.start(tbkGoodsPromotionUrlCrawlerFactory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        androidThread.start();
        iosThread.join();
        androidThread.join();
        System.out.println("================更新数据库=========================");



        stuffPromotionTmpDao.updateRebateId(tableName);
        stuffPromotionTmpDao.refrash(tableName);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        stuffPromotionTmpDao.rename(tableName,format.format(new Date()));

        System.exit(1);


    }
}

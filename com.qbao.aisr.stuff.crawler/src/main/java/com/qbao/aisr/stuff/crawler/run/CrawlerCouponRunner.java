package com.qbao.aisr.stuff.crawler.run;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qbao.aisr.stuff.config.App;
import com.qbao.aisr.stuff.crawler.CrawlerService;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.CouponGoodsFactory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;

/**
 * @author shuaizhihu
 * @createTime 2017/3/17 18:23
 * $$LastChangedDate: 2017-06-15 10:40:19 +0800 (Thu, 15 Jun 2017) $$
 * $$LastChangedRevision: 1220 $$
 * $$LastChangedBy: liaijun $$
 */
@SuppressWarnings("all")
public class CrawlerCouponRunner {

    public static void main(String[] args) throws Exception {

        String paths[] = new String[]{"spring_crawler.xml"};
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
        StuffCouponDao stuffCouponDao =  ctx.getBean(StuffCouponDao.class);
        Properties prop = new Properties();//属性集合对象
        InputStream fis;
        fis = App.class.getClassLoader().getResourceAsStream("tbk.properties");
        prop.load(fis);//将属性文件流装载到Properties对象中
        String tableName=prop.getProperty("tbk.table.name");
        //stuffCouponDao.updateStuffCouponByTime(tableName, new Date());
        stuffCouponDao.updateStuffCouponByBefore(tableName);
        Thread baseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("======1111111======创建临时表，并下载商品================");
                String paths[] = new String[]{"spring_crawler.xml"};
                //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
                ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

                CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
                

                //更新返券
                CrawlerAbstractFactory couponGoodsFactory = ctx.getBean("couponGoodsFactory",CouponGoodsFactory.class);
                try {
                    crawlerService.init(couponGoodsFactory);
                    crawlerService.start(couponGoodsFactory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        baseThread.start();
        baseThread.join();
        
        System.out.println("===========44444444=====更新数据库结束=========================");


        

        System.exit(1);


    }
}

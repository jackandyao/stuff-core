package com.qbao.aisr.stuff.crawler.run;

import com.qbao.aisr.stuff.crawler.CrawlerService;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsPromotionUrlCrawlerFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkIosGoodsPromotionUrlCrawlerFactory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/17 18:23
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class CrawlerRunnerTmp {

    public static void main(String[] args) throws Exception {

        String paths[] = new String[]{"spring_crawler.xml"};
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
        StuffPromotionTmpDao stuffPromotionTmpDao =  ctx.getBean(StuffPromotionTmpDao.class);

        //stuffPromotionTmpDao.renameProdToTmp();//-----七万--------
        //stuffPromotionTmpDao.createProd();//-----七万--------

    /*    Thread baseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("======1111111======创建临时表，并下载商品================");
                String paths[] = new String[]{"spring_crawler.xml"};
                //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
                ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

                CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
                //创建商品临时表
                //StuffPromotionTmpDao stuffPromotionTmpDao =  ctx.getBean(StuffPromotionTmpDao.class);
                //stuffPromotionTmpDao.createTmp();

                //下载商品
                CrawlerAbstractFactory tbkGoodsFactory = ctx.getBean("tbkGoodsFactory",TbkGoodsFactory.class);
                try {
                    crawlerService.init(tbkGoodsFactory);
                    crawlerService.start(tbkGoodsFactory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        baseThread.start();
        baseThread.join();*/

        //转链接
        /*CrawlerAbstractFactory tbkGoodsPromotionUrlCrawlerFactory = ctx.getBean("tbkGoodsPromotionUrlCrawlerFactory",TbkGoodsPromotionUrlCrawlerFactory.class);
        crawlerService.init(tbkGoodsPromotionUrlCrawlerFactory);
        crawlerService.start(tbkGoodsPromotionUrlCrawlerFactory);*/

        Thread iosThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("=====22222222222=======IOS运行================");
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
                //System.out.println("=====33333333333=======android运行================");
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
        //System.out.println("===========44444444=====更新数据库=========================");


        //更新返券
      /*  stuffPromotionTmpDao.updateRebateId();
        //把生产表里存在，而临时表里不存在的数据同步到临时表里
        stuffPromotionTmpDao.refrash();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //生产表改成备份表，临时表改成生产表
        stuffPromotionTmpDao.rename(format.format(new Date()));
*/
        System.exit(1);


    }
}

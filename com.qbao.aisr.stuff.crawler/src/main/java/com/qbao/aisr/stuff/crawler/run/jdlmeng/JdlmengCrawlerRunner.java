package com.qbao.aisr.stuff.crawler.run.jdlmeng;

import com.qbao.aisr.stuff.crawler.CrawlerService;
import com.qbao.aisr.stuff.crawler.factory.CrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.jdlmeng.JdlmengCrawlerAbstractFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsFactory;
import com.qbao.aisr.stuff.crawler.factory.tbk.TbkGoodsPromotionUrlCrawlerFactory;
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
public class JdlmengCrawlerRunner {

    public static void main(String[] args) throws Exception {

        String paths[] = new String[]{"spring_crawler.xml"};
        //这个xml文件是Spring配置beans的文件，顺带一提，路径 在整个应用的根目录
        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        CrawlerService crawlerService = ctx.getBean(CrawlerService.class);
        StuffPromotionTmpDao stuffPromotionTmpDao =  ctx.getBean(StuffPromotionTmpDao.class);

        //创建商品临时表
        stuffPromotionTmpDao.createJdlmengTmp();

        //下载商品

        CrawlerAbstractFactory crawlerAbstractFactory = ctx.getBean("jdlmengCrawlerAbstractFactory",JdlmengCrawlerAbstractFactory.class);
        crawlerService.init(crawlerAbstractFactory);
        crawlerService.start(crawlerAbstractFactory);

        //转链接
        /*CrawlerAbstractFactory tbkGoodsPromotionUrlCrawlerFactory = ctx.getBean("tbkGoodsPromotionUrlCrawlerFactory",TbkGoodsPromotionUrlCrawlerFactory.class);
        crawlerService.init(tbkGoodsPromotionUrlCrawlerFactory);
        crawlerService.start(tbkGoodsPromotionUrlCrawlerFactory);*/

        //更新返券
        //stuffPromotionTmpDao.updateRebateId();
        //把生产表里存在，而临时表里不存在的数据同步到临时表里
        //stuffPromotionTmpDao.refrash();

        //SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //生产表改成备份表，临时表改成生产表
        //stuffPromotionTmpDao.rename(format.format(new Date()));

        System.exit(1);


    }
}

package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 11:27
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Component(value="alimamaGoodsPromotionUrlTklTaskProvide")
public class AlimamaGoodsPromotionUrlTklTaskProvide implements ICrawlerProvide {

    Logger logger = LoggerFactory.getLogger(AlimamaGoodsPromotionUrlTklTaskProvide.class);

    /*@Value("${tbk.site.id}")
    private String tbkSiteId;
    @Value("${tbk.adzone.id}")
    private String tbkAdzoneId;*/
    @Value("${tbk.android.site.id}")
    private String tbkAndroidSiteId;
    @Value("${tbk.android.adzone.id}")
    private String tbkAndroidAdzoneId;
    /*@Value("${tbk.ios.site.id}")
    private String tbkIosSiteId;
    @Value("${tbk.ios.adzone.id}")
    private String tbkIosAdzoneId;*/
    @Value("${tbk.table.name}")
    private String tableName;
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;

    @Override
    public void provide(CrawlerContext crawlerContext) throws Exception {
        boolean isLoop = true;
        while(isLoop){

            StuffPromotion stuffPromotion = this.get();
            if(stuffPromotion ==null){
                break;
            }
            StuffPromotion old =  stuffPromotionTmpDao.findStuffPromotionByStuffId(stuffPromotion.getId());
            if(old!=null){
                if(old.getAndroidPromotionUrl()!=null){
                    if(old.getAndroidPromotionUrl().startsWith("￥")){
                        logger.info("android promotion url 已经存在，直接入库！");
                        PersistTask persistTask = new PersistTask();
                        //stuffPromotion.setPromotionUrl(old.getPromotionUrl());
                        stuffPromotion.setAndroidPromotionUrl(old.getAndroidPromotionUrl());
                        //必须android和ios的推广链接同时存在时才能把商品置为上架
                        if(stuffPromotion.getIosPromotionUrl()!=null&&stuffPromotion.getIosPromotionUrl().startsWith("￥")){
                            stuffPromotion.setStatus(1);
                        }else{
                            stuffPromotion.setStatus(0);
                        }

                        persistTask.setPersistData(stuffPromotion);
                        crawlerContext.persistTaskQueue.put(persistTask);
                        continue;
                    }
                }
            }

            DownloadTask<StuffPromotion> downloadTask = new DownloadTask<StuffPromotion>();
            downloadTask.setData(stuffPromotion);
            downloadTask.setEntryUrl(AlimamaUtil.createConertUrlForItem(stuffPromotion.getRealStuffId()+"",tbkAndroidAdzoneId,tbkAndroidSiteId,""));
            logger.info(downloadTask.getEntryUrl());
            crawlerContext.dlTaskQueue.put(downloadTask);
        }
    }


    @Transactional
    private StuffPromotion get(){
        //查找android推广链接为空的记录---一条一条的改推广链接
        //StuffPromotion  stuffPromotion = stuffPromotionTmpDao.findAndroidNotPromotionQiWan();//-----七万--------
        StuffPromotion  stuffPromotion = stuffPromotionTmpDao.findAndroidNotPromotion(tableName);//-----七万--------
        //为空表示所有都处理完
        if(stuffPromotion==null){
            return null;
        }
        //防止DownloadTask任务还未完成---多线程
        stuffPromotionTmpDao.updateAndroidPromotionUrl(tableName,stuffPromotion.getId(),"#");
        return stuffPromotion;
    }
}

package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCategoryDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 18:13
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Component(value="alimamaGoodsCrawlerProvide")
public class AlimamaGoodsCrawlerProvide implements ICrawlerProvide {

    @Autowired
    StuffCategoryDao stuffCategoryDao;
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;

    @Override
    public void provide(CrawlerContext crawlerContext) throws InterruptedException, UnsupportedEncodingException {
        List<StuffCategory> list = stuffCategoryDao.findStuffCategoryByLev(4);
        //int count = 0;
        for(StuffCategory sc : list){
            /*count+=1;
            if(count>5){
                break;
            }*/
            DownloadTask<StuffCategory> task = new DownloadTask<StuffCategory>();
            task.setEntryUrl(AlimamaUtil.createGoodsSearchUrl(sc.getCatName(),30,50,10,99,30,100));
            task.setMethod(DlMethod.GET);
            task.setData(sc);
            crawlerContext.dlTaskQueue.put(task);
            DownloadTask<StuffCategory> task1 = new DownloadTask<StuffCategory>();
            task1.setEntryUrl(AlimamaUtil.createGoodsSearchUrl(sc.getCatName(),51,500,8,99,20,100));
            task1.setMethod(DlMethod.GET);
            task1.setData(sc);
            crawlerContext.dlTaskQueue.put(task1);
            DownloadTask<StuffCategory> task2 = new DownloadTask<StuffCategory>();
            task2.setEntryUrl(AlimamaUtil.createGoodsSearchUrl(sc.getCatName(),1000,5000,6,99,10,100));
            task2.setMethod(DlMethod.GET);
            task2.setData(sc);
            crawlerContext.dlTaskQueue.put(task2);
        }
    }


}

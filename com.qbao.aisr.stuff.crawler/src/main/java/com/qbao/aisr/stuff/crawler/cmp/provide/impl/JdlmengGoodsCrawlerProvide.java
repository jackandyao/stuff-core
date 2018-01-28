package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCategoryDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;
import com.qbao.aisr.stuff.util.jdlmeng.JdlmengUtil;
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
@Component(value="jdlmengGoodsCrawlerProvide")
public class JdlmengGoodsCrawlerProvide implements ICrawlerProvide {

    @Autowired
    StuffCategoryDao stuffCategoryDao;
   /* @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;*/

    @Override
    public void provide(CrawlerContext crawlerContext) throws InterruptedException, UnsupportedEncodingException {
        List<StuffCategory> list = stuffCategoryDao.findStuffCategoryByLev(4);
        for(StuffCategory sc : list){
            DownloadTask<StuffCategory> task = new DownloadTask<StuffCategory>();
            task.setEntryUrl(JdlmengUtil.createGoodsSearchUrl(sc.getCatName(), 30, 50, 10, 99, 30, 50));
            task.setMethod(DlMethod.GET);
            task.setData(sc);
            crawlerContext.dlTaskQueue.put(task);
        }
        System.out.printf("========");
    }


}

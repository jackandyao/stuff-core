package com.qbao.aisr.stuff.crawler.cmp.provide.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qbao.aisr.stuff.crawler.cmp.download.DownloadTask;
import com.qbao.aisr.stuff.crawler.cmp.download.dlenum.DlMethod;
import com.qbao.aisr.stuff.crawler.cmp.provide.ICrawlerProvide;
import com.qbao.aisr.stuff.crawler.context.CrawlerContext;
import com.qbao.aisr.stuff.model.mysql.StuffCategory;
import com.qbao.aisr.stuff.model.mysql.StuffKeyword;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffKeywordDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import com.qbao.aisr.stuff.util.alimama.AlimamaUtil;

/**
 * @author shuaizhihu
 * @createTime 2017/3/7 18:13
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Component(value="alimamaGoodsKeywordCrawlerProvide")
public class AlimamaGoodsKeywordCrawlerProvide implements ICrawlerProvide {

    @Autowired
    StuffKeywordDao stuffKeywordDao;
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;

    @Override
    public void provide(CrawlerContext crawlerContext) throws InterruptedException, UnsupportedEncodingException {
//        List<StuffCategory> list=new ArrayList<>();
//        StuffCategory stuffCategory=new StuffCategory();
//        stuffCategory.setCatId("121101105100");
//        stuffCategory.setCatName("女装");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("121101102100");
//        stuffCategory.setCatName("男装");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("121101100100");
//        stuffCategory.setCatName("鞋");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("133100100100");
//        stuffCategory.setCatName("包");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("119107104101");
//        stuffCategory.setCatName("珠宝配饰");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("112100100100");
//        stuffCategory.setCatName("运动户外");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("110100100100");
//        stuffCategory.setCatName("美妆");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("119100100100");
//        stuffCategory.setCatName("母婴");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("117100100100");
//        stuffCategory.setCatName("食品");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("121101103100");
//        stuffCategory.setCatName("内衣");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("107103100100");
//        stuffCategory.setCatName("数码");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("113100100100");
//        stuffCategory.setCatName("家装");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("116102100100");
//        stuffCategory.setCatName("家具用品");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("125103100100");
//        stuffCategory.setCatName("家电");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("120100100100");
//        stuffCategory.setCatName("汽车");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("127100100100");
//        stuffCategory.setCatName("生活服务");
//        list.add(stuffCategory);
//        stuffCategory.setCatId("135100100100");
//        stuffCategory.setCatName("图书音像");

        //重启服务后,把正在执行的修改为等待处理
        stuffKeywordDao.updateKeywordStatus();
        List<StuffKeyword> list = stuffKeywordDao.findStuffKeyword();

        for(StuffKeyword sc : list){
            
            //放入线程先修改状态 表示正在执行
            stuffKeywordDao.updateStatus(sc.getId(),sc.getStatus());

            DownloadTask<StuffCategory> task = new DownloadTask<StuffCategory>();
            task.setEntryUrl(AlimamaUtil.createGoodsSearchUrl(sc.getKeyword(),0,0,0,0,0,0));
            task.setMethod(DlMethod.GET);
            crawlerContext.dlTaskQueue.put(task);

        }
    }


}

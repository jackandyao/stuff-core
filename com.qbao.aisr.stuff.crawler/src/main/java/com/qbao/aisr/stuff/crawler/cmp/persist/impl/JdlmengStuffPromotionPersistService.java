package com.qbao.aisr.stuff.crawler.cmp.persist.impl;

import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:09
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="jdlmengStuffPromotionPersistService")
public class JdlmengStuffPromotionPersistService implements IPersistService{

    Logger logger = LoggerFactory.getLogger(JdlmengStuffPromotionPersistService.class);

    private static HashSet<Long> persistSet;

    List<StuffPromotion> saveList = new ArrayList<StuffPromotion>();

    @Autowired
    StuffPromotionTmpDao stuffPromotionDao;

    @Override
    public boolean persist(PersistTask task) {
        StuffPromotion stuffPromotion = (StuffPromotion) task.getPersistData();
//        StuffPromotion old = stuffPromotionDao.isExsit(stuffPromotion);
        //System.out.printf("========="+isExsit(stuffPromotion.getId()));
        if(isExsit(stuffPromotion.getId())){
            saveList.add(stuffPromotion);
            if(saveList.size()>1000) {
                stuffPromotionDao.insertJdlmengBatch(saveList);
                logger.info("保存商品列表！");
                saveList.clear();
            }
            return true;
        }else{
            logger.info("此商品已经存在！ "+stuffPromotion.toString());
            return false;
        }
    }

    @Override
    public void persistOver() {
        if(saveList.size()>0) {
            stuffPromotionDao.insertJdlmengBatch(saveList);
            saveList.clear();
        }
    }

    public synchronized boolean isExsit(Long stuffId){
        if(persistSet == null){
            persistSet = new HashSet<Long>();
            List<Long> ids = stuffPromotionDao.findJdlmengAllStuffIds();
            for(Long id:ids){
                persistSet.add(id);
            }
        }
        if(persistSet.add(stuffId)){
            return true;
        }else{
            return false;
        }
    }


}

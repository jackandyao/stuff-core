package com.qbao.aisr.stuff.crawler.cmp.persist.impl;

import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 19:44
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="stuffIosPromotionUrlUpdateService")
public class StuffIosPromotionUrlUpdateService implements IPersistService {
    @Value("${tbk.table.name}")
    private String tableName;
    Logger logger = LoggerFactory.getLogger(StuffIosPromotionUrlUpdateService.class);
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;
    @Override
    @Transactional
    public boolean persist(PersistTask task) {
        StuffPromotion stuffPromotion = (StuffPromotion) task.getPersistData();
        stuffPromotionTmpDao.updateIosPromotionUrl(tableName,stuffPromotion.getId(),stuffPromotion.getIosPromotionUrl());
        stuffPromotionTmpDao.updateStatus(tableName,stuffPromotion.getId(),stuffPromotion.getStatus());
        System.out.println("===========IOS推广链接成功入库============="+stuffPromotion.getIosPromotionUrl());
        logger.info("IOS推广链接入库成功！"+stuffPromotion.getIosPromotionUrl());
        return true;
    }

    @Override
    public void persistOver() {

    }
}

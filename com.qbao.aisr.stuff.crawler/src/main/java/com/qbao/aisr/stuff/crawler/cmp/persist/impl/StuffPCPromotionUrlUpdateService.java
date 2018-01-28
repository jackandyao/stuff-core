package com.qbao.aisr.stuff.crawler.cmp.persist.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPromotionTmpDao;

/**
 * @author shuaizhihu
 * @createTime 2017/3/13 19:44
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="stuffPCPromotionUrlUpdateService")
public class StuffPCPromotionUrlUpdateService implements IPersistService {
    @Value("${tbk.table.name}")
    private String tableName;
    Logger logger = LoggerFactory.getLogger(StuffPCPromotionUrlUpdateService.class);
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;
    @Override
    @Transactional
    public boolean persist(PersistTask task) {
        StuffPromotion stuffPromotion = (StuffPromotion) task.getPersistData();
        stuffPromotionTmpDao.updatePCPromotionUrl(tableName,stuffPromotion.getId(),stuffPromotion.getPcPromotionUrl());
        stuffPromotionTmpDao.updateStatus(tableName,stuffPromotion.getId(),stuffPromotion.getStatus());
        System.out.println("===========pc推广链接成功入库============="+stuffPromotion.getPcPromotionUrl());
        logger.info("pc推广链接入库成功！"+stuffPromotion.getPcPromotionUrl());
        return true;
    }

    @Override
    public void persistOver() {

    }
}

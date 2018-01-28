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
@Service(value="stuffPromotionUrlUpdateService")
public class StuffPromotionUrlUpdateService implements IPersistService {
    Logger logger = LoggerFactory.getLogger(StuffPromotionUrlUpdateService.class);
    @Value("${tbk.table.name}")
    private String tableName;
    @Autowired
    StuffPromotionTmpDao stuffPromotionTmpDao;
    @Override
    @Transactional
    public boolean persist(PersistTask task) {
        StuffPromotion stuffPromotion = (StuffPromotion) task.getPersistData();
        stuffPromotionTmpDao.updateAndroidPromotionUrl(tableName,stuffPromotion.getId(),stuffPromotion.getAndroidPromotionUrl());
        stuffPromotionTmpDao.updateStatus(tableName,stuffPromotion.getId(),stuffPromotion.getStatus());
        logger.info("android推广链接入库成功！"+stuffPromotion.getAndroidPromotionUrl());
        return true;
    }

    @Override
    public void persistOver() {

    }
}

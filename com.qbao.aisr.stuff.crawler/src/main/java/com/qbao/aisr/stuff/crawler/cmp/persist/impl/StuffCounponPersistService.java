package com.qbao.aisr.stuff.crawler.cmp.persist.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.StuffCoupon;
import com.qbao.aisr.stuff.model.mysql.StuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffCouponDao;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 15:09
 * $$LastChangedDate: 2017-06-15 10:40:19 +0800 (Thu, 15 Jun 2017) $$
 * $$LastChangedRevision: 1220 $$
 * $$LastChangedBy: liaijun $$
 */
@Service(value="stuffCounponPersistService")
public class StuffCounponPersistService implements IPersistService{

    Logger logger = LoggerFactory.getLogger(StuffCounponPersistService.class);

    private static HashSet<Long> persistSet;

    List<StuffCoupon> saveList = new ArrayList<StuffCoupon>();

    @Autowired
    StuffCouponDao stuffCouponDao;
    @Value("${tbk.table.name}")
    private String tableName;

    @Override
    public boolean persist(PersistTask task) {
    	StuffCoupon stuffCoupon = (StuffCoupon) task.getPersistData();
//        StuffPromotion old = stuffPromotionDao.isNotExsit(stuffPromotion);
    	    stuffCouponDao.updateStuffCouponByType(tableName, stuffCoupon);
            return true;
        
    }

    @Override
    public void persistOver() {
        
    }

   


}
/**
@Service(value="stuffCounponPersistService")
public class StuffCounponPersistService implements IPersistService{

    Logger logger = LoggerFactory.getLogger(StuffCounponPersistService.class);

    private static HashSet<Long> persistSet;

    List<StuffCoupon> saveList = new ArrayList<StuffCoupon>();

    @Autowired
    StuffCouponDao stuffCouponDao;
    @Value("${tbk.table.name}")
    private String tableName;

    @Override
    public boolean persist(PersistTask task) {
    	StuffCoupon stuffCoupon = (StuffCoupon) task.getPersistData();
            if(isExsit(stuffCoupon.getStuffId())){
                saveList.add(stuffCoupon);
                if(saveList.size()>1000) {
                	stuffCouponDao.insertBatch(tableName, saveList);
                    logger.info("保存优惠券！");
                    saveList.clear();
                }
                return true;
            }else{
                logger.info("此商品已经存在！ "+stuffCoupon.toString());
                return false;
            }
    }

    @Override
    public void persistOver() {
    	 if(saveList.size()>0) {
    		 stuffCouponDao.insertBatch(tableName, saveList);
             saveList.clear();
         }
    }

    public synchronized boolean isExsit(Long stuffId){
        if(persistSet == null){
            persistSet = new HashSet<Long>();
            List<Long> ids = stuffCouponDao.findAllStuffIds(tableName);
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


**/

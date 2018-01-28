package com.qbao.aisr.stuff.crawler.cmp.persist.impl;

import com.qbao.aisr.stuff.common.stuffenum.ReturnCouponStatus;
import com.qbao.aisr.stuff.common.stuffenum.TaokeOrderStatus;
import com.qbao.aisr.stuff.crawler.cmp.persist.IPersistService;
import com.qbao.aisr.stuff.crawler.cmp.persist.PersistTask;
import com.qbao.aisr.stuff.model.mysql.TaokeDetail;
import com.qbao.aisr.stuff.model.mysql.UserStuffPromotion;
import com.qbao.aisr.stuff.repository.mybatis.dao.TaokeDetailDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.UserStuffPromotionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shuaizhihu
 * @createTime 2017/3/16 14:30
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
@Service(value="taokeDetailPersistService")
public class TaokeDetailPersistService implements IPersistService {

    @Autowired
    TaokeDetailDao taokeDetailDao;

    @Autowired
    UserStuffPromotionDao userStuffPromotionDao;

    @Override
    @Transactional
    public boolean persist(PersistTask task) {
        TaokeDetail taokeDetail = (TaokeDetail) task.getPersistData();
        TaokeDetail old = taokeDetailDao.isExsit(taokeDetail);
        if(old!=null){
            if(taokeDetail.getStatus()!=old.getStatus()){
                taokeDetail.setId(old.getId());
                taokeDetailDao.update(taokeDetail);
            }

        }else{
            taokeDetail.setCreateTime(new Date());
            taokeDetailDao.insert(taokeDetail);
        }
        long orderId = taokeDetail.getOrderId();
        UserStuffPromotion userStuffPromotion = this.create(orderId);
        if(userStuffPromotion != null){
            userStuffPromotionDao.update(userStuffPromotion);
        }

        return true;
    }

    @Override
    public void persistOver() {

    }

    private UserStuffPromotion create(long orderId){
        UserStuffPromotion userStuffPromotion = userStuffPromotionDao.findByOrderId(orderId);
        if(userStuffPromotion==null){
            return null;
        }
        List<TaokeDetail> list = taokeDetailDao.findByOrderId(orderId);
        BigDecimal totalPrice = BigDecimal.ZERO;
        int returnCouponStatus = -1;
        long rebateValue=0;
        Date orderTime = null;
        for(TaokeDetail detail:list){
            if(null == detail || null == detail.getRebateType()) continue;
            if(0 == detail.getRebateType()){//返宝券
            //返宝券状态-1,为订单录入 0: 未返券,1已返券,2返券被收回(以后可能被用到),3返券失败
            orderTime = detail.getOrderTime();
            if(detail.getStatus()== TaokeOrderStatus.PAY.getCode()){
                returnCouponStatus = 0;//待返券状态
            }else if(detail.getStatus()==TaokeOrderStatus.FAIL.getCode()){
                returnCouponStatus = ReturnCouponStatus.FAIL.getCode();
            }else if(detail.getStatus() == TaokeOrderStatus.SETTLEMENT.getCode()){
                returnCouponStatus = TaokeOrderStatus.SETTLEMENT.getCode(); //后期根据用户信用分来决定是否在确认收货之后返券  目前不返券 （用户确认收货后订单状态为订单结算）
            }else if(detail.getStatus() == TaokeOrderStatus.SUCCESS.getCode()){
                returnCouponStatus = 0;//已返券状态
            }
            totalPrice = totalPrice.add(detail.getPayValue());
            rebateValue += detail.getRebateValue();
            userStuffPromotion.setRebateType(detail.getRebateType());
            }else { //TODO 返RMB 全部状态显示待返
                orderTime = detail.getOrderTime();
                if(detail.getStatus()== TaokeOrderStatus.PAY.getCode()){
                    returnCouponStatus = 0;//待返券状态
                }else if(detail.getStatus()==TaokeOrderStatus.FAIL.getCode()){
                    returnCouponStatus = ReturnCouponStatus.FAIL.getCode();
                }else if(detail.getStatus() == TaokeOrderStatus.SETTLEMENT.getCode()){
                    returnCouponStatus = 0;
                   // returnCouponStatus = TaokeOrderStatus.SETTLEMENT.getCode(); //后期根据用户信用分来决定是否在确认收货之后返券  目前不返券 （用户确认收货后订单状态为订单结算）
                }else if(detail.getStatus() == TaokeOrderStatus.SUCCESS.getCode()){
                    returnCouponStatus = 0;//已返券状态
                }
                totalPrice = totalPrice.add(detail.getPayValue());
                rebateValue += detail.getRebateValue();
                userStuffPromotion.setRebateType(detail.getRebateType());
            }
        }

        userStuffPromotion.setUpdateTime(new Date());
        userStuffPromotion.setOrderTime(orderTime);
        userStuffPromotion.setPrice(totalPrice);
        userStuffPromotion.setRebateValue(rebateValue);
        userStuffPromotion.setReturnCouponStatus(returnCouponStatus);
        if(0!=userStuffPromotion.getStatus() ) {//我的好货订单状态 逻辑是否删除：0:删除，1:正常 , -1 初始状态
            userStuffPromotion.setStatus(1);
        }
        return userStuffPromotion;
    }
}

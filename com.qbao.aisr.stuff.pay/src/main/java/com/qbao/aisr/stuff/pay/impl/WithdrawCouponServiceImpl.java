package com.qbao.aisr.stuff.pay.impl;

import com.qbao.aisr.stuff.model.mysql.StuffWithdrawFlow;
import com.qbao.aisr.stuff.pay.CouponService;
import com.qbao.aisr.stuff.pay.WithdrawCouponService;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffWithdrawFlowDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.UserStuffPromotionAppealDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.UserStuffPromotionDao;
import com.qbao.aisr.stuff.util.message.NotifierUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * @author wangping
 * @createTime 2017/6/6 上午9:39
 * $$LastChangedDate: 2017-06-21 18:48:31 +0800 (Wed, 21 Jun 2017) $$
 * $$LastChangedRevision: 1308 $$
 * $$LastChangedBy: wangping $$
 */
@Service("withdrawCouponService")
public class WithdrawCouponServiceImpl implements WithdrawCouponService {
    private static final Logger log = Logger.getLogger(WithdrawCouponServiceImpl.class);
    private static int WAIT_PAY_STATUS = 4; //返宝券状态-1,为订单录入 0: 未返券,1已返券,2返券被收回(以后可能被用到),3返券失败,4 订单结算
    private static int PAY_STATUS = 1; //返宝券状态-1,为订单录入 0: 未返券,1已返券,2返券被收回(以后可能被用到),3返券失败,4 订单结算
    private static long BATCH_PAY_SIZE = 10;
    private static int APPEAL_STATUS = 3; //申诉状态: -1 提交申诉,0 平台审核, 1受理申诉 ,2 用户取消申诉,3申诉反馈
    private static int APPEAL_WAIT_PAY_STATUS = 0; //支付状态 0: 没有支付 1 已经支付
    private static int PAY_SOURCE_USER_STUFF_PROMOTION = 0;
    private static int PAY_SOURCE_USER_STUFF_PROMOTION_APPEAL = 1;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserStuffPromotionDao userStuffPromotionDao;

    @Autowired
    private UserStuffPromotionAppealDao userStuffPromotionAppealDao;

    @Autowired
    private StuffWithdrawFlowDao stuffWithdrawFlowDao;

    private long totalUserStuffPromotionPay = 0;
    private long totalUserStuffPromotionRecord = 0;

    private long totalUserAppealPay = 0;
    private long totalUserAppealRecord = 0;


    @Override
    public void withdraw(Long userId,String orderId,String source,Long couponValue) {
        if(0 == couponValue){
            return;
        }
        //void to repay again the coupon
        StuffWithdrawFlow isPayed = stuffWithdrawFlowDao.find(userId,orderId,PAY_SOURCE_USER_STUFF_PROMOTION,1,source);
        if(null!=isPayed){
            log.info("订单号已经扣券,跳过重复扣券:"+isPayed);
            NotifierUtil.notifyByPhone("订单号已经扣券,跳过重复扣券:"+isPayed);
            return;
        }

        boolean isSuccessed = couponService.withdrawCoupons(userId, couponValue, orderId, source,PAY_SOURCE_USER_STUFF_PROMOTION);


    }

}

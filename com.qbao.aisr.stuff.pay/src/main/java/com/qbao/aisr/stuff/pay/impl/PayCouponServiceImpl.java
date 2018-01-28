package com.qbao.aisr.stuff.pay.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.qbao.aisr.stuff.model.mysql.StuffPayFlow;
import com.qbao.aisr.stuff.model.mysql.UserStuffPromotion;
import com.qbao.aisr.stuff.model.mysql.UserStuffPromotionAppeal;
import com.qbao.aisr.stuff.pay.CouponService;
import com.qbao.aisr.stuff.pay.PayCouponService;
import com.qbao.aisr.stuff.pay.constant.RebateTypeEnum;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPayFlowDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.UserStuffPromotionAppealDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.UserStuffPromotionDao;
import com.qbao.aisr.stuff.util.message.NotifierUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangping
 * @createTime 2017/6/6 上午9:39
 * $$LastChangedDate: 2017-06-27 18:42:28 +0800 (Tue, 27 Jun 2017) $$
 * $$LastChangedRevision: 1354 $$
 * $$LastChangedBy: wangping $$
 */
@Service("payCouponService")
public class PayCouponServiceImpl implements PayCouponService {
    private static final Logger log = Logger.getLogger(PayCouponServiceImpl.class);
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
    private StuffPayFlowDao stuffPayFlowDao;

    private long totalUserStuffPromotionPay = 0;
    private long totalUserStuffPromotionRecord = 0;

    private long totalUserAppealPay = 0;
    private long totalUserAppealRecord = 0;


    @Override
    public void pay() {
        String date = sdf.format(new Date()).toString();
        NotifierUtil.notifyByPhone(date+" 返券任务开始执行...");
        log.info("starting pay user_stuff_promotion ...");
        long ts = System.currentTimeMillis();
        payUserStuffPromotion();
        log.info("finished to pay user_stuff_promotion and cost : " +(System.currentTimeMillis() -ts) +"  sec ");
        log.info("starting pay user_stuff_promotion_appeal ...");
        payUserStuffPromotionAppeal();
        log.info("finished to pay user_stuff_promotion_appeal and cost : " +(System.currentTimeMillis() -ts) +"  sec ");
        log.info("pay job is done!");
        long totalRecord = totalUserStuffPromotionRecord + totalUserAppealRecord;
        long totalPay = totalUserAppealPay + totalUserStuffPromotionPay;
        String report = date + " 返宝券任务完成. 成功支付" + totalRecord + "条记录,共" + totalPay + "宝券.  用户订单表" + totalUserStuffPromotionRecord + "条记录,支付" + totalUserStuffPromotionPay + "宝券; 申诉表" + totalUserAppealRecord + "条记录,支付" + totalUserAppealPay + "宝券";
        NotifierUtil.notifyByPhone(report);
        NotifierUtil.notifyByEmail(date+"返券统计" ,report);

    }

    @Transactional
    private void payUserStuffPromotion() {
        long total = userStuffPromotionDao.queryTotalByReturnCouponStatus(WAIT_PAY_STATUS, RebateTypeEnum.COUPON.getCode());
        int times = (int) (total / BATCH_PAY_SIZE);
        log.info("total [" + total + "] records are needed to pay coupon. and need "+times+"batch to pay");
        for (int i = 0; i <= times; i++) {
            long offset =0;
            long limit = BATCH_PAY_SIZE;
            List<UserStuffPromotion> userStuffPromotions = userStuffPromotionDao.queryByReturnCouponStatus(WAIT_PAY_STATUS,RebateTypeEnum.COUPON.getCode(), 0, limit);

            List<Long> successedIds = Lists.newArrayList();
            List<Long> failIds = Lists.newArrayList();
            if (null != userStuffPromotions) {
                for (UserStuffPromotion userStuffPromotion : userStuffPromotions) {
                    Long userId = userStuffPromotion.getUserId();
                    String orderId = StringUtils.trimToNull(userStuffPromotion.getOrderId());
                    String source = StringUtils.trimToNull(userStuffPromotion.getSource());
                    long couponValue =userStuffPromotion.getRebateValue();
                    Preconditions.checkNotNull(userId, "UserStuffPromotion user id is null ");
                    Preconditions.checkNotNull(orderId, "UserStuffPromotion order id is null ");
                    Preconditions.checkNotNull(source, "UserStuffPromotion source  is null ");
                    log.info("userId=["+userId+"]"+",orderId=["+orderId+"], source=["+source+"], couponValue=["+couponValue+"]");
                    //void to repay again the coupon
                    StuffPayFlow isPayed = stuffPayFlowDao.find(userId,orderId,PAY_SOURCE_USER_STUFF_PROMOTION,1,source);
                    if(null!=isPayed){
                        log.info("订单号已经返券,跳过重复返券:"+isPayed);
                        NotifierUtil.notifyByPhone("订单号已经返券,跳过重复返券:"+isPayed);
                        successedIds.add(userStuffPromotion.getId());
                        continue;
                    }
                    boolean isSucceed = couponService.grantCoupons(userId, couponValue, orderId, source,PAY_SOURCE_USER_STUFF_PROMOTION);
                    if (isSucceed) {
                        totalUserStuffPromotionPay += couponValue;
                        totalUserStuffPromotionRecord += 1;
                        successedIds.add(userStuffPromotion.getId());
                        log.info("grant coupon is succeed  for userId=["+userId+"]"+",orderId=["+orderId+"], source=["+source+"], couponValue=["+couponValue+"]");
                    } else {
                        //TODO
                        failIds.add(userStuffPromotion.getId());

                    }


                }
            }
            if(0!=failIds.size() ){
                NotifierUtil.notifyByEmail("用户订单表返券失败","用户订单表id :"+failIds.toString());
            }
            userStuffPromotionDao.batchUpdateReturnCouponStatus(successedIds, PAY_STATUS);
        }

    }

    @Transactional
    private void payUserStuffPromotionAppeal() {
        long total = userStuffPromotionAppealDao.queryTotalIsPayAndStatus(APPEAL_WAIT_PAY_STATUS,APPEAL_STATUS);
        log.info("total [" + total + "] records to pay coupon");
        int times = (int) (total / BATCH_PAY_SIZE);
        for (int i = 0; i <= times; i++) {
            long offset = 0;
            long limit = BATCH_PAY_SIZE;
            List<UserStuffPromotionAppeal> appeals = userStuffPromotionAppealDao.queryByIsPayAndStatus(APPEAL_WAIT_PAY_STATUS,APPEAL_STATUS, offset, limit);
            List<Long> successedIds = Lists.newArrayList();
            List<Long> failIds = Lists.newArrayList();
            if (null != appeals) {
                for (UserStuffPromotionAppeal appeal : appeals) {
                    Long userId = appeal.getUserId();
                    String orderId = StringUtils.trimToNull(appeal.getOrderId());
                    String source = StringUtils.trimToNull(appeal.getSource());
                    Preconditions.checkNotNull(userId, "UserStuffPromotion user id is null ");
                    Preconditions.checkNotNull(orderId, "UserStuffPromotion order id is null ");
                    Preconditions.checkNotNull(source, "UserStuffPromotion source  is null ");
                    long couponValue =appeal.getRebateValue();
                    log.info("userId=["+userId+"]"+",orderId=["+orderId+"], source=["+source+"], couponValue=["+couponValue+"]");
                    StuffPayFlow isPayed = stuffPayFlowDao.find(userId,orderId,PAY_SOURCE_USER_STUFF_PROMOTION_APPEAL,1,source);
                    if(null!=isPayed){
                        log.info("订单号已经返券,跳过重复返券:"+isPayed);
                        NotifierUtil.notifyByPhone("申诉订单号已经返券,跳过重复返券:"+isPayed);
                        successedIds.add(appeal.getId());
                        continue;
                    }
                    boolean isSucceed = couponService.grantCoupons(userId, couponValue, orderId, source,PAY_SOURCE_USER_STUFF_PROMOTION_APPEAL);
                    if (isSucceed) {
                        totalUserAppealPay += couponValue;
                        totalUserAppealRecord += 1;
                        successedIds.add(appeal.getId());
                        log.info("grant coupon is succeed  for userId=["+userId+"]"+",orderId=["+orderId+"], source=["+source+"], couponValue=["+couponValue+"]");

                    } else {
                        //TODO
                        failIds.add(appeal.getId());
                    }
                }
            }
            if(0!=failIds.size() ){
                NotifierUtil.notifyByEmail("申诉表返券失败","申诉表id :"+failIds.toString());
            }
            userStuffPromotionAppealDao.batchUpdateIsPayStatus(successedIds, PAY_STATUS);
        }

    }
    public static void main(String[] args){
        System.out.println((int) (657/BATCH_PAY_SIZE));
    }
}

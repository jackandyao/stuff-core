package com.qbao.aisr.stuff.pay.impl;

import com.qbao.aisr.stuff.model.mysql.StuffPayFlow;
import com.qbao.aisr.stuff.model.mysql.StuffWithdrawFlow;
import com.qbao.aisr.stuff.pay.CouponService;
import com.qbao.aisr.stuff.pay.PayInterfaceApi;
import com.qbao.aisr.stuff.pay.bean.PayResponse;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffPayFlowDao;
import com.qbao.aisr.stuff.repository.mybatis.dao.StuffWithdrawFlowDao;
import com.qbao.aisr.stuff.util.codec.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangchanghong on 16/4/29.
 */
@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger log = Logger.getLogger(CouponServiceImpl.class);

	@Autowired
	private PayInterfaceApi payInterfaceApi;
	
	@Autowired
	private StuffPayFlowDao stuffPayFlowDao;

	@Autowired
	private StuffWithdrawFlowDao stuffWithdrawFlowDao;

	@Override
	public boolean grantCoupons(long userId, long couponAmout,String orderId,String source,int paySource) {
		boolean isSuccessed =  false;
		try {
			String msg = ",用户ID:" + userId + ",宝券金额:" + couponAmout + " , 订单号:" + orderId + " , 商品源:" + source;
			log.info("发放宝券任务开始" + DateUtil.dateToDateString(new Date()) + msg);
			// 创建流水
			StuffPayFlow stuffPayFlow = new StuffPayFlow();
			stuffPayFlow.setUserId(userId);
			stuffPayFlow.setAmt(couponAmout);
			stuffPayFlow.setCreateTime(new Date());
			stuffPayFlow.setOrderId(orderId);
			stuffPayFlow.setSource(source);
			stuffPayFlow.setPaySource(paySource);
			stuffPayFlowDao.insert(stuffPayFlow);
			String outTradeNo = generateOutTradeNo(stuffPayFlow);
			PayResponse payResponse = payInterfaceApi.payGrantCoupons(userId, couponAmout, outTradeNo);
			if (payResponse.success()) {
				log.info(msg+ "发放宝券调用支付中心成功");
				// 更新流水
				stuffPayFlow.setPayStatus(1);
				stuffPayFlow.setPayTradeNo(payResponse.getPayTradeNo());
				stuffPayFlowDao.update(stuffPayFlow);
				isSuccessed = true;
			} else {
				log.error("发放宝券调用支付中心失败"+ msg+ ", response : " +payResponse.getRespMsg());
				isSuccessed = false;
				// 更新流水
				stuffPayFlow.setPayStatus(2);
				stuffPayFlow.setPayTradeNo(payResponse.getPayTradeNo());
				stuffPayFlow.setFailMsg(payResponse.getRespMsg());
				stuffPayFlowDao.update(stuffPayFlow);
			}
		} catch (Exception e) {
			log.error("发放宝券调用支付中心处理异常", e);
			isSuccessed = false;
		}
		log.info("发放宝券任务结束" + DateUtil.dateToDateString(new Date()));
		return isSuccessed;
	}

	@Override
	public String generateOutTradeNo(StuffPayFlow stuffPayFlow ) {
		//return StringUtils.trimToEmpty(stuffPayFlow.getId() +"_" + stuffPayFlow.getUserId() + "_" + stuffPayFlow.getOrderId()+"_"+stuffPayFlow.getSource()+"_"+stuffPayFlow.getPaySource());
		return StringUtils.trimToEmpty(stuffPayFlow.getUserId() + "_" + stuffPayFlow.getOrderId()+"_"+stuffPayFlow.getPaySource()+"_"+stuffPayFlow.getAmt());
	}


	@Override
	public String generateOutTradeNo(StuffWithdrawFlow stuffWithdrawFlow ) {
		//return StringUtils.trimToEmpty(stuffPayFlow.getId() +"_" + stuffPayFlow.getUserId() + "_" + stuffPayFlow.getOrderId()+"_"+stuffPayFlow.getSource()+"_"+stuffPayFlow.getPaySource());
		return StringUtils.trimToEmpty(stuffWithdrawFlow.getUserId() + "_" + stuffWithdrawFlow.getOrderId()+"_"+stuffWithdrawFlow.getPaySource()+"_"+stuffWithdrawFlow.getAmt());
	}

	@Override
	public boolean withdrawCoupons(long userId, long couponAmout, String orderId, String source, int paySource) {
		boolean isSuccessed =  false;
		try {
			String msg = ",用户ID:" + userId + ",宝券金额:" + couponAmout + " , 订单号:" + orderId + " , 商品源:" + source;
			log.info("扣宝券任务开始" + DateUtil.dateToDateString(new Date()) + msg);
			// 创建流水
			StuffWithdrawFlow stuffWithdrawFlow = new StuffWithdrawFlow();
			stuffWithdrawFlow.setUserId(userId);
			stuffWithdrawFlow.setAmt(couponAmout);
			stuffWithdrawFlow.setCreateTime(new Date());
			stuffWithdrawFlow.setOrderId(orderId);
			stuffWithdrawFlow.setSource(source);
			stuffWithdrawFlow.setPaySource(paySource);
			stuffWithdrawFlowDao.insert(stuffWithdrawFlow);
			String outTradeNo = generateOutTradeNo(stuffWithdrawFlow);
			PayResponse payResponse = payInterfaceApi.withdrawCoupons(userId, couponAmout, outTradeNo);
			if (payResponse.success()) {
				log.info(msg+ "扣宝券调用支付中心成功");
				// 更新流水
				stuffWithdrawFlow.setPayStatus(1);
				stuffWithdrawFlow.setPayTradeNo(payResponse.getPayTradeNo());
				stuffWithdrawFlowDao.update(stuffWithdrawFlow);
				isSuccessed = true;
			} else {
				log.error("扣宝券调用支付中心失败"+ msg+ ", response : " +payResponse.getRespMsg());
				isSuccessed = false;
				// 更新流水
				stuffWithdrawFlow.setPayStatus(2);
				stuffWithdrawFlow.setPayTradeNo(payResponse.getPayTradeNo());
				stuffWithdrawFlow.setFailMsg(payResponse.getRespMsg());
				stuffWithdrawFlowDao.update(stuffWithdrawFlow);
			}
		} catch (Exception e) {
			log.error("扣宝券调用支付中心处理异常", e);
			isSuccessed = false;
		}
		log.info("扣宝券任务结束" + DateUtil.dateToDateString(new Date()));
		return isSuccessed;
	}

}

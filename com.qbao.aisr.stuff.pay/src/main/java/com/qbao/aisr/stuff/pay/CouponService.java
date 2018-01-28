package com.qbao.aisr.stuff.pay;

import com.qbao.aisr.stuff.model.mysql.StuffPayFlow;
import com.qbao.aisr.stuff.model.mysql.StuffWithdrawFlow;

/**
 * Created by zhangchanghong on 16/4/29.
 */
public interface CouponService {
	
	/**
	 * 发放宝券
	 * 
	 * @param userId
	 *            用户ID
	 * @param couponAmout
	 *            宝券金额
	 * @throws Exception
	 */
	public boolean grantCoupons(long userId, long couponAmout,String orderId,String source,int paySource);

	public String generateOutTradeNo(StuffPayFlow stuffPayFlow);

	public String generateOutTradeNo(StuffWithdrawFlow stuffWithdrawFlow);

	public boolean withdrawCoupons(long userId, long couponAmout,String orderId,String source,int paySource);

}

package com.qbao.aisr.stuff.pay;

/**
 * @author wangping
 * @createTime 2017/6/6 上午9:38
 * $$LastChangedDate: 2017-06-21 18:48:31 +0800 (Wed, 21 Jun 2017) $$
 * $$LastChangedRevision: 1308 $$
 * $$LastChangedBy: wangping $$
 */
public interface WithdrawCouponService {
    public void withdraw(Long userId,String orderId,String source,Long couponValue);
}

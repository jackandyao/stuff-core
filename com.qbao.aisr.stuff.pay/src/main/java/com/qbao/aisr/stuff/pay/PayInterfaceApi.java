package com.qbao.aisr.stuff.pay;

import com.qbao.aisr.stuff.pay.bean.PayResponse;

/**
 * Created by zhangchanghong on 16/4/26.
 */
public interface PayInterfaceApi {
    String SIGN_TYPE = "RSA";
    String INPUT_CHARSET = "UTF-8";
    String OUTTRAD_NO_PREFIX="FX";
 
    /**
     * 发放宝券
     * @param userId    用户ID
     * @param couponAmout   宝券金额
     * @return
     * @throws Exception
     */
    public PayResponse payGrantCoupons(long userId,long couponAmout, String outTradeNo) throws Exception;

    public PayResponse withdrawCoupons(long userId,long couponAmout, String outTradeNo) throws Exception;
}

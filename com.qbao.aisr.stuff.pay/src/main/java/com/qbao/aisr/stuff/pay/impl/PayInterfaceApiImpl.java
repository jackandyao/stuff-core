package com.qbao.aisr.stuff.pay.impl;

import com.alibaba.fastjson.JSON;
import com.qbao.aisr.stuff.pay.PayInterfaceApi;
import com.qbao.aisr.stuff.pay.bean.DataBizException;
import com.qbao.aisr.stuff.pay.bean.GrantCouponsInfo;
import com.qbao.aisr.stuff.pay.bean.PayResponse;
import com.qbao.aisr.stuff.util.codec.BeanUtils;
import com.qbao.aisr.stuff.util.codec.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by yunlong fan on 13/3/17.
 */
@Component
public class PayInterfaceApiImpl implements PayInterfaceApi {
    private static final Logger log = LoggerFactory.getLogger(PayInterfaceApiImpl.class);
    @Value("${pay_private_key}")
    private String payPrivateKey;
    @Value("${group_code}")
    private String groupCode;
    @Value("${pay_receive_url}")
    private String payReceiveUrl;

    @Override
    public PayResponse payGrantCoupons(long userId, long couponAmout, String outTradeNo) throws Exception {
        try {
            GrantCouponsInfo info = GrantCouponsInfo.payInstance(userId, couponAmout, groupCode, payPrivateKey, outTradeNo);
            log.info("pay center grant coupons param=" + BeanUtils.objToMap(info));
            String res = HttpUtils.doPost(payReceiveUrl, BeanUtils.objToMap(info));
            log.info("调用发放宝券支付中心返回结果:" + res);
            PayResponse response = JSON.parseObject(res, PayResponse.class);
            return response;
        } catch (IOException e) {
            log.error("调用发放宝券支付中心超时", e);
            throw new DataBizException("调用发放宝券支付中心超时");
        } catch (Exception e) {
            log.error("调用发放宝券接口失败", e);
            throw new DataBizException("调用发放宝券接口失败");
        }
    }

    @Override
    public PayResponse withdrawCoupons(long userId, long couponAmout, String outTradeNo) throws Exception {
        try {
            GrantCouponsInfo info = GrantCouponsInfo.withdrawInstance(userId, couponAmout, groupCode, payPrivateKey, outTradeNo);
            log.info("pay center withdraw coupons param=" + BeanUtils.objToMap(info));
            String res = HttpUtils.doPost(payReceiveUrl, BeanUtils.objToMap(info));
            log.info("调用扣宝券支付中心返回结果:" + res);
            PayResponse response = JSON.parseObject(res, PayResponse.class);
            return response;
        } catch (IOException e) {
            log.error("调用扣宝券支付中心超时", e);
            throw new DataBizException("调用扣宝券支付中心超时");
        } catch (Exception e) {
            log.error("调用扣宝券宝券接口失败", e);
            throw new DataBizException("调用扣宝券接口失败");
        }
    }

}

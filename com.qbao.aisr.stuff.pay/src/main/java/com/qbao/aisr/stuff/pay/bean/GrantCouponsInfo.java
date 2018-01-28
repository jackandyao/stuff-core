package com.qbao.aisr.stuff.pay.bean;

import com.alibaba.fastjson.JSON;
import com.qbao.aisr.stuff.pay.constant.PayCenterConst;
import com.qbao.aisr.stuff.util.codec.Digest;
import com.qbao.aisr.stuff.util.codec.RSAUtil;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrantCouponsInfo {

    // 集团账号
    protected String groupCode;
    // 交易请求时间
    protected String tradeTime;
    // 签名算法   RSA
    private String signType = "RSA";
    private String signCode;
    // 编码类型
    private String inputCharset = "UTF-8";
    // 分配类型
    private String busiType;
    // userId
    private String userId;
    // 外部流水号
    private String outTradeNo;
    // 手续费
    private Long feeAmount;
    // 交易描述
    private String tradeDesc;
    // 终端  1-PC,2-ios,3-android
    private String terminal;
    // 明细数据
    private String data;

    public GrantCouponsInfo() {
    }

    public GrantCouponsInfo(String groupCode, String tradeTime, String busiType, String userId, Long feeAmount, String outTradeNo, List<GrantCouponInfoTradeDataItem> tradeDataList) {
        this.groupCode = groupCode;
        this.tradeTime = tradeTime;
        this.busiType = busiType;
        this.userId = userId;
        this.feeAmount = feeAmount;
        this.outTradeNo = outTradeNo;
        this.data = JSON.toJSONString(tradeDataList);
    }

    public static GrantCouponsInfo payInstance(long userId, long couponAmout, String groupCode, String payPrivateKey, String outTradeNo) throws Exception {
        List<GrantCouponInfoTradeDataItem> items = new ArrayList<GrantCouponInfoTradeDataItem>();
        //行项JSON数据初始化
        GrantCouponInfoTradeDataItem payEntity = GrantCouponInfoTradeDataItem.instance(outTradeNo, couponAmout);
        items.add(payEntity);

        GrantCouponsInfo payInfo = new GrantCouponsInfo(groupCode,
                org.apache.commons.lang3.time.DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"),
                PayCenterConst.AGENT_BUSI_BIG_TYPE,
                userId + "",
                0L,
                outTradeNo,
                items);
        // add sign
        payInfo.setTradeDesc(Base64.encodeBase64URLSafeString("发放宝券".getBytes("utf-8")));
        payInfo.addSign(payPrivateKey);
        return payInfo;
    }
    public static GrantCouponsInfo withdrawInstance(long userId, long couponAmout, String groupCode,String payPrivateKey, String outTradeNo) throws Exception {
        List<GrantCouponInfoTradeDataItem> items = new ArrayList<GrantCouponInfoTradeDataItem>();
        //行项JSON数据初始化
        GrantCouponInfoTradeDataItem payEntity = GrantCouponInfoTradeDataItem.instance(outTradeNo,couponAmout,PayCenterConst.WITHDRAW_COUPON_SMALL_CODE);
        items.add(payEntity);

        GrantCouponsInfo payInfo = new GrantCouponsInfo(groupCode,
                org.apache.commons.lang3.time.DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"),
                PayCenterConst.AGENT_BUSI_BIG_TYPE,
                userId + "",
                0L,
                outTradeNo,
                items);
        // add sign
        payInfo.setTradeDesc(Base64.encodeBase64URLSafeString("扣宝券".getBytes("utf-8")));
        payInfo.addSign(payPrivateKey);
        return payInfo;
    }

    public void addSign(String privateKey) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("groupCode", groupCode);
        params.put("inputCharset", inputCharset);
        params.put("tradeTime", tradeTime);
        params.put("outTradeNo", outTradeNo);
        params.put("userId", userId);
        params.put("tradeDesc", tradeDesc);
        params.put("busiType", busiType);
        params.put("feeAmount", feeAmount + "");
        params.put("data", data);
        String md = Digest.digest(params, "signType");
        this.signCode = RSAUtil.sign(md, RSAUtil.getPrivateKey(privateKey));
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Long feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

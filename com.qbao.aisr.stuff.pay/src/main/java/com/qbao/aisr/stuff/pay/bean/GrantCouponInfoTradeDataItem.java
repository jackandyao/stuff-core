package com.qbao.aisr.stuff.pay.bean;

import com.qbao.aisr.stuff.pay.constant.PayCenterConst;

/**
 * 发放宝券明细
 * @author zhangwf
 * @date 16/8/24
 */
public class GrantCouponInfoTradeDataItem {
    // 行项明细流水
    private String itemTradeNo;
    // 支付方式 1-人民币,2-宝券
    private String payType = "2";
    // 金额,单位分
    private Long amount;
    // 明细业务类型
    private String itemBusiType;

    public GrantCouponInfoTradeDataItem(String itemTradeNo, Long amount){
        this.itemTradeNo = itemTradeNo;
        this.amount = amount;
        this.itemBusiType = PayCenterConst.GRANT_COUPON_SMALL_CODE;
    }
    public GrantCouponInfoTradeDataItem(String itemTradeNo, Long amount,String itemBusiType){
        this.itemTradeNo = itemTradeNo;
        this.amount = amount;
        this.itemBusiType = itemBusiType;
    }

    public static GrantCouponInfoTradeDataItem instance(String itemTradeNo, Long amount) {
        return new GrantCouponInfoTradeDataItem(itemTradeNo, amount);
    }
    public static GrantCouponInfoTradeDataItem instance(String itemTradeNo, Long amount,String itemBusiType) {
        return new GrantCouponInfoTradeDataItem(itemTradeNo, amount,itemBusiType);
    }

    public String getItemTradeNo() {
        return itemTradeNo;
    }

    public void setItemTradeNo(String itemTradeNo) {
        this.itemTradeNo = itemTradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getItemBusiType() {
        return itemBusiType;
    }

    public void setItemBusiType(String itemBusiType) {
        this.itemBusiType = itemBusiType;
    }
}

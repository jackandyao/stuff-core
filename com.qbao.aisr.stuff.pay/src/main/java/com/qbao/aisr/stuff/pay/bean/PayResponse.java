package com.qbao.aisr.stuff.pay.bean;

import java.util.List;

/**
 * Created by zhangchanghong on 16/4/26.
 */
public class PayResponse {
    // 交易成功
    public static final String RES_SUC = "100000";
    // 交易失败
    public static final String RES_FAIL = "100001";
    // 交易处理中
    public static final String RES_DEALING = "100002";
	// 交易信息不存在
	public static final String RES_TRADE_NONENTITY = "100003";
    // 安全验证失败
    public static final String RES_AUTH_VER_FAIL = "200000";
    // 参数不合法
    public static final String RES_VER_FAIL = "300000";
    // 系统异常
    public static final String RES_BUSY = "999999";
    // 余额不足
    public static final String RES_BALANCE_INSUFFICIENT = "400000";
	// 请求重复:该批次已受理
	public static final String RES_REPEAT = "500000";
	// 支付请求超时
	public static final String RES_TIME_OUT = "800000";

    // 返回编码
    private String respCode;
    // 返回信息
    private String respMsg;
    // 集团账号
    private String groupCode;
    // 用户ID
    private String userId;
    // 外部流水号
    private String outTradeNo;
    // 交易流水号
    private String payTradeNo;

    private List<Long> origItemTradeNos;

    public boolean success() {
        return respCode != null && respCode.equals(RES_SUC);
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public List<Long> getOrigItemTradeNos() {
        return origItemTradeNos;
    }

    public void setOrigItemTradeNos(List<Long> origItemTradeNos) {
        this.origItemTradeNos = origItemTradeNos;
    }

	@Override
	public String toString() {
		return "PayResponse [respCode=" + respCode + ", respMsg=" + respMsg
				+ ", groupCode=" + groupCode + ", userId=" + userId
				+ ", outTradeNo=" + outTradeNo + ", payTradeNo=" + payTradeNo
				+ ", origItemTradeNos=" + origItemTradeNos + "]";
	}
    
}

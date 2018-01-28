package com.qbao.aisr.stuff.common.stuffenum;

/**
 * @author wangping
 * @createTime 2017/6/18 下午10:23
 * $$LastChangedDate: 2017-06-18 23:52:37 +0800 (Sun, 18 Jun 2017) $$
 * $$LastChangedRevision: 1281 $$
 * $$LastChangedBy: wangping $$
 */
public enum AlimamaOrderStatus {
    PAY(12,"订单付款"),SUCCESS(14,"订单成功"),FAIL(13,"订单失效"),SETTLEMENT(3,"订单结算");

    private int code;
    private String name;

    private AlimamaOrderStatus(int code, String name){
        this.code = code;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getCode() {
        return code;
    }
}

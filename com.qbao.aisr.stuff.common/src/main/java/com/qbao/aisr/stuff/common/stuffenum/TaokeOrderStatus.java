package com.qbao.aisr.stuff.common.stuffenum;

/**
 * @author shuaizhihu
 * @createTime 2017/3/16 17:02
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public enum TaokeOrderStatus {
    PAY(1,"订单付款"),SUCCESS(2,"订单成功"),FAIL(3,"订单失效"),SETTLEMENT(4,"订单结算");

    private int code;
    private String name;

    private TaokeOrderStatus(int code, String name){
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

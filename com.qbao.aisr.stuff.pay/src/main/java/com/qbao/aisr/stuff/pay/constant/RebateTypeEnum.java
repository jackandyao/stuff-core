package com.qbao.aisr.stuff.pay.constant;

/**
 * @author wangping
 * @createTime 2017/6/27 下午4:10
 * $$LastChangedDate: 2017-06-27 18:45:58 +0800 (Tue, 27 Jun 2017) $$
 * $$LastChangedRevision: 1356 $$
 * $$LastChangedBy: wangping $$
 */
public enum  RebateTypeEnum {
    ////返利类型 0  宝券 1 RMB
    COUPON(0,"宝券"),SUCCESS(1,"RMB");

    private int code;
    private String name;

    private RebateTypeEnum(int code, String name){
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

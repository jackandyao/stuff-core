package com.qbao.aisr.stuff.common.stuffenum;

/**
 * @author shuaizhihu
 * @createTime 2017/3/16 17:11
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public enum ReturnCouponStatus {
    //返宝券状态 0: 未返券,1已返券,2返券被收回(以后可能被用到),3返券失败
    WAIT(0,"未返券"),SUCCESS(1,"已返券"), RECOVER(2,"返券被收回"),FAIL(3,"返券失败");
    private int code;
    private String name;

    private ReturnCouponStatus(int code, String name){
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

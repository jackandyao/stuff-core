package com.qbao.aisr.stuff.common.stuffenum;

/**
 * @author shuaizhihu
 * @createTime 2017/3/8 14:08
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public enum GoodsSource {
    TAOBAO(111, "taobao"),
    TMALL(222, "tmall"),
    JD(333, "jd"),
    VIP(444, "vip"),
    YHD(555, "yhd");

    private int code;
    private String name;

    private GoodsSource(int code, String name){
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

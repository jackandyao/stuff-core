package com.qbao.aisr.stuff.model.tbk;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
public class GoodsItem implements Serializable{
//    num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick
    private static final long serialVersionUID ="$Id$".hashCode();
    private long num_iid;//商品ID
    private String title;//商品名称
    private String pictUrl;// 主图url
    private String[] smallImages;//小图urls
    private String reservePrice;//原价
    private String zkFinalPrice;//折扣价
    private int userType;//卖家类型，0表示集市，1表示商城
    private String  provcity;//宝贝所在地
    private String itemUrl;//商品地址
    private String  nick;//卖家昵称
    private long sellerId;//卖家id
    private long  volume;//30天销量

    public long getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(long num_iid) {
        this.num_iid = num_iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String[] getSmallImages() {
        return smallImages;
    }

    public void setSmallImages(String[] smallImages) {
        this.smallImages = smallImages;
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getZkFinalPrice() {
        return zkFinalPrice;
    }

    public void setZkFinalPrice(String zkFinalPrice) {
        this.zkFinalPrice = zkFinalPrice;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getProvcity() {
        return provcity;
    }

    public void setProvcity(String provcity) {
        this.provcity = provcity;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("num_iid", num_iid).append("title", title).append("pictUrl", pictUrl).append("smallImages", smallImages).append("reservePrice", reservePrice).append("zkFinalPrice", zkFinalPrice).append("userType", userType).append("provcity", provcity).append("itemUrl", itemUrl).append("nick", nick).append("sellerId", sellerId).append("volume", volume).toString();
    }
}

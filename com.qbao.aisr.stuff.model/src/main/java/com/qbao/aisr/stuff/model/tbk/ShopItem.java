package com.qbao.aisr.stuff.model.tbk;

import java.io.Serializable;
/**
 * @author shuaizhihu
 * @createTime 2017/3/6 10:45
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */

public class ShopItem implements Serializable {

    private static final long serialVersionUID ="$Id$".hashCode();
    //user_id,shop_title,shop_type,seller_nick,pict_url,shop_url
    private long userId;//卖家ID
    private String shopTitle;//店铺名称
    private String shopType;//店铺类型，B：天猫，C：淘宝
    private String sellerNick;//卖家昵称
    private String pictUrl;//店标图片
    private String shopUrl;//店铺地址

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }
}

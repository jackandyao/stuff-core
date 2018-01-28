package com.qbao.aisr.stuff.model.mysql;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by shuaizhihu on 2017/3/1.
 */
public class StuffPromotion implements Serializable{

    private long id;
    private long realStuffId;
    private String name;
    private BigDecimal reservePrice;
    private BigDecimal finalPrice;
    private long rebateId;
    private String imgUrl;
    private String url;
    private String androidPromotionUrl;
    private String iosPromotionUrl;
    private String pcPromotionUrl;
    private long promotionRate;
    private String catId;
    private int status;
    private String source;
    private long shopId;
    private String shopName;
    private int orderNum;
    private int clickNum;
    private Date createTime;
    private Date updateTime;
    private String operatorSource;
    private String catName;
    private String activities;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAndroidPromotionUrl() {
        return androidPromotionUrl;
    }

    public void setAndroidPromotionUrl(String androidPromotionUrl) {
        this.androidPromotionUrl = androidPromotionUrl;
    }

    public String getIosPromotionUrl() {
        return iosPromotionUrl;
    }

    public void setIosPromotionUrl(String iosPromotionUrl) {
        this.iosPromotionUrl = iosPromotionUrl;
    }

    public long getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(long promotionRate) {
        this.promotionRate = promotionRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRealStuffId() {
        return realStuffId;
    }

    public void setRealStuffId(long realStuffId) {
        this.realStuffId = realStuffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public long getRebateId() {
        return rebateId;
    }

    public void setRebateId(long rebateId) {
        this.rebateId = rebateId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getPcPromotionUrl() {
        return pcPromotionUrl;
    }

    public void setPcPromotionUrl(String pcPromotionUrl) {
        this.pcPromotionUrl = pcPromotionUrl;
    }

    public String getOperatorSource() {
		return operatorSource;
	}

	public void setOperatorSource(String operatorSource) {
		this.operatorSource = operatorSource;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("realStuffId", realStuffId).append("name", name).append("reservePrice", reservePrice).append("finalPrice", finalPrice).append("rebateId", rebateId).append("imgUrl", imgUrl).append("url", url).append("androidPromotionUrl", androidPromotionUrl).append("iosPromotionUrl", iosPromotionUrl).append("promotionRate", promotionRate).append("catId", catId).append("status", status).append("source", source).append("shopId", shopId).append("shopName", shopName).append("orderNum", orderNum).append("clickNum", clickNum).append("createTime", createTime).append("updateTime", updateTime).toString();
    }
}

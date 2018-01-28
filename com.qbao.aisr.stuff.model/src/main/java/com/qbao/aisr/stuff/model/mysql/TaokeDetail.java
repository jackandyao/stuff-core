package com.qbao.aisr.stuff.model.mysql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/15 19:30
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class TaokeDetail implements Serializable {
    //
    private Long id;
    // 商品名称
    private String name;
    // 创建时间
    private Date orderTime;
    // 点击时间
    private Date clickTime;
    // 商品id
    private Long stuffId;
    // 商品真正id
    private Long realStuffId;
    // 掌柜旺旺
    private String wangwang;
    // 所属店铺
    private String shopName;
    // 商品数
    private Integer stuffNum;
    // 商品单价
    private BigDecimal price;
    //订单状态值  1:订单付款 2:订单成功 3:订单失效 4:订单结算
    private int status;
    // 订单状态
    private String orderStatus;
    // 订单类型
    private String orderType;
    // 收入比率
    private Double incomeRate;
    // 分成比率
    private Double sharingRate;
    // 佣金比率
    private Double commissionRate;
    // 补贴比率
    private Double subsidyRate;
    // 补贴类型
    private String subsidyType;
    // 成交平台
    private String platform;
    // 第三方服务
    private String thirdPartySerivce;
    // 订单编号
    private Long orderId;
    // 类目名称
    private String categoryName;
    // 来源媒体id
    private Long sourceId;
    // 来源媒体名称
    private String mediaName;
    // 来源平台名称
    private String sourceName;
    // 广告位ID
    private Long advId;
    // 广告位名称
    private String advName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    // 商品图片地址
    private String imgUrl;
    //android商品详细页面
    private String androidClickUrl;
    //ios商品详细页面
    private String iosClickUrl;
    //返券数量
    private long  rebateValue;
    //付款金额
    private BigDecimal payValue;
    //佣金金额
    private BigDecimal commissionValue;
    //预估价格
    private BigDecimal estimateValue;
    //结算价格
    private BigDecimal settlementValue;
    //返利类型 0  宝券 1 RMB
    private Integer rebateType;


    public BigDecimal getEstimateValue() {
        return estimateValue;
    }

    public void setEstimateValue(BigDecimal estimateValue) {
        this.estimateValue = estimateValue;
    }

    public BigDecimal getSettlementValue() {
        return settlementValue;
    }

    public void setSettlementValue(BigDecimal settlementValue) {
        this.settlementValue = settlementValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public Long getStuffId() {
        return stuffId;
    }

    public void setStuffId(Long stuffId) {
        this.stuffId = stuffId;
    }

    public Long getRealStuffId() {
        return realStuffId;
    }

    public void setRealStuffId(Long realStuffId) {
        this.realStuffId = realStuffId;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getStuffNum() {
        return stuffNum;
    }

    public void setStuffNum(Integer stuffNum) {
        this.stuffNum = stuffNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(Double incomeRate) {
        this.incomeRate = incomeRate;
    }

    public Double getSharingRate() {
        return sharingRate;
    }

    public void setSharingRate(Double sharingRate) {
        this.sharingRate = sharingRate;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(Double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public String getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(String subsidyType) {
        this.subsidyType = subsidyType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getThirdPartySerivce() {
        return thirdPartySerivce;
    }

    public void setThirdPartySerivce(String thirdPartySerivce) {
        this.thirdPartySerivce = thirdPartySerivce;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime(Date date) {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /*public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }*/

    public String getAndroidClickUrl() {
        return androidClickUrl;
    }

    public void setAndroidClickUrl(String androidClickUrl) {
        this.androidClickUrl = androidClickUrl;
    }

    public String getIosClickUrl() {
        return iosClickUrl;
    }

    public void setIosClickUrl(String iosClickUrl) {
        this.iosClickUrl = iosClickUrl;
    }

    public long getRebateValue() {
        return rebateValue;
    }

    public void setRebateValue(long rebateValue) {
        this.rebateValue = rebateValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getPayValue() {
        return payValue;
    }

    public void setPayValue(BigDecimal payValue) {
        this.payValue = payValue;
    }

    public BigDecimal getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(BigDecimal commissionValue) {
        this.commissionValue = commissionValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getRebateType() {
        return rebateType;
    }

    public void setRebateType(Integer rebateType) {
        this.rebateType = rebateType;
    }
}

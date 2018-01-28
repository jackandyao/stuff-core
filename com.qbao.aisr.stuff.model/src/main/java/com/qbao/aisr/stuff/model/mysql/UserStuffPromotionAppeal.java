package com.qbao.aisr.stuff.model.mysql;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author wangping
 * @createTime 2017/6/6 上午9:30
 * $$LastChangedDate: 2017-06-09 17:36:20 +0800 (Fri, 09 Jun 2017) $$
 * $$LastChangedRevision: 1149 $$
 * $$LastChangedBy: wangping $$
 */
public class UserStuffPromotionAppeal implements Serializable {
    private static final long serialVersionUID="$Id: UserStuffPromotionAppeal.java 1149 2017-06-09 09:36:20Z wangping $".hashCode();
    private long id;
    private long userId;
    private String source;
    private String orderId;
    private int status;
    private int isPay;
    private long rebateValue;

    public long getRebateValue() {
        return rebateValue;
    }

    public void setRebateValue(long rebateValue) {
        this.rebateValue = rebateValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserStuffPromotionAppeal))
            return false;

        UserStuffPromotionAppeal that = (UserStuffPromotionAppeal) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getUserId(), that.getUserId())
                .append(getStatus(), that.getStatus())
                .append(getIsPay(), that.getIsPay())
                .append(getRebateValue(), that.getRebateValue())
                .append(getSource(), that.getSource())
                .append(getOrderId(), that.getOrderId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getUserId())
                .append(getSource())
                .append(getOrderId())
                .append(getStatus())
                .append(getIsPay())
                .append(getRebateValue())
                .toHashCode();
    }
}

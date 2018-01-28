package com.qbao.aisr.stuff.model.mysql;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shuaizhihu
 * @createTime 2017/3/6 16:41 $$LastChangedDate$$ $$LastChangedRevision$$
 *             $$LastChangedBy$$
 */
public class StuffPayFlow implements Serializable {

    private static final long serialVersionUID = "$Id$".hashCode();

    private long id;
    private long userId;
    private long amt;
    private int payStatus;
    private String failMsg;
    private String payTradeNo;
    private Date createTime;
    private String orderId;
    private String source;
    private int paySource;

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

    public long getAmt() {
        return amt;
    }

    public void setAmt(long amt) {
        this.amt = amt;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public int getPaySource() {
        return paySource;
    }

    public void setPaySource(int paySource) {
        this.paySource = paySource;
    }

    @Override
    public String
    toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("amt", amt)
                .append("payStatus", payStatus)
                .append("failMsg", failMsg)
                .append("payTradeNo", payTradeNo)
                .append("createTime", createTime)
                .append("orderId", orderId)
                .append("source", source)
                .append("paySource", paySource)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof StuffPayFlow))
            return false;

        StuffPayFlow that = (StuffPayFlow) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getUserId(), that.getUserId())
                .append(getAmt(), that.getAmt())
                .append(getPayStatus(), that.getPayStatus())
                .append(getFailMsg(), that.getFailMsg())
                .append(getPayTradeNo(), that.getPayTradeNo())
                .append(getCreateTime(), that.getCreateTime())
                .append(getOrderId(), that.getOrderId())
                .append(getSource(), that.getSource())
                .append(getPaySource(), that.getPaySource())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getUserId())
                .append(getAmt())
                .append(getPayStatus())
                .append(getFailMsg())
                .append(getPayTradeNo())
                .append(getCreateTime())
                .append(getOrderId())
                .append(getSource())
                .append(getPaySource())
                .toHashCode();
    }
}

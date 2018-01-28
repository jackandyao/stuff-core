package com.qbao.aisr.stuff.model.mysql;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author liaijun
 * @email liaijun@qbao.com
 * @date 2017-03-07 13:45:15
 */
public class UserStuffPromotion implements Serializable {
	private static final long serialVersionUID="$Id: UserStuffPromotion.java 1354 2017-06-27 10:42:28Z wangping $".hashCode();
	
	//
	private Long id;
	//用户id
	private Long userId;
	//订单id
	private String orderId;
    // 商品来源
    private String source;
	//返宝券状态 0: 未返券,1已返券,2返券被收回(以后可能被用到)
	private Integer returnCouponStatus;
    // 返利多少
    private long  rebateValue;
	//下单时间
	private Date orderTime;
	//创建时间
	private Date createTime;
	//
	private Date updateTime;

	//商品实际价格
    private BigDecimal price;

    private Integer status; //逻辑是否删除：0:删除，1:正常 , -1 初始状态

	//返利类型 0  宝券 1 RMB
	private Integer rebateType;






	/**
	 * 设置：用户id
	 */

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：返宝券状态 0: 未返券,1已返券,2返券被收回(以后可能被用到)
	 */
	public void setReturnCouponStatus(Integer returnCouponStatus) {
		this.returnCouponStatus = returnCouponStatus;
	}
	/**
	 * 获取：返宝券状态 0: 未返券,1已返券,2返券被收回(以后可能被用到)
	 */
	public Integer getReturnCouponStatus() {
		return returnCouponStatus;
	}
	/**
	 * 设置：下单时间
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * 获取：下单时间
	 */
	public Date getOrderTime() {
		return orderTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

    public Long getId() {
        return id;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getRebateValue() {
        return rebateValue;
    }

    public void setRebateValue(long rebateValue) {
        this.rebateValue = rebateValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("userId", userId)
				.append("orderId", orderId)
				.append("source", source)
				.append("returnCouponStatus", returnCouponStatus)
				.append("rebateValue", rebateValue)
				.append("orderTime", orderTime)
				.append("createTime", createTime)
				.append("updateTime", updateTime)
				.append("price", price)
				.append("status", status)
				.append("rebateType", rebateType)
				.toString();
	}
}

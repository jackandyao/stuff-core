package com.qbao.aisr.stuff.model.mysql;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author liaijun
 * @email liaijun@qbao.com
 * @date 2017-06-06 14:20:15
 */
public class StuffCoupon implements Serializable {
	private static final long serialVersionUID="$Id: StuffCoupon.java 1098 2017-06-06 08:53:21Z liaijun $".hashCode();
	
	//主键
	private Long id;
	//商品id
	private Long stuffId;
	//真实商品id
	private Long realStuffId;
	//优惠券链接
	private String link;
	//优惠幅度
	private BigDecimal value;
	//推广文案
	private String introduce;
	//优惠券类型
	private String type;
	//总库存
	private Integer totalCount;
	//来源
	private String source;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：商品id
	 */
	public void setStuffId(Long stuffId) {
		this.stuffId = stuffId;
	}
	/**
	 * 获取：商品id
	 */
	public Long getStuffId() {
		return stuffId;
	}
	/**
	 * 设置：真实商品id
	 */
	public void setRealStuffId(Long realStuffId) {
		this.realStuffId = realStuffId;
	}
	/**
	 * 获取：真实商品id
	 */
	public Long getRealStuffId() {
		return realStuffId;
	}
	/**
	 * 设置：优惠券链接
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * 获取：优惠券链接
	 */
	public String getLink() {
		return link;
	}
	/**
	 * 设置：优惠幅度
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * 获取：优惠幅度
	 */
	public BigDecimal getValue() {
		return value;
	}
	/**
	 * 设置：推广文案
	 */
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	/**
	 * 获取：推广文案
	 */
	public String getIntroduce() {
		return introduce;
	}
	/**
	 * 设置：优惠券类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：优惠券类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：总库存
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 获取：总库存
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置：来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
}

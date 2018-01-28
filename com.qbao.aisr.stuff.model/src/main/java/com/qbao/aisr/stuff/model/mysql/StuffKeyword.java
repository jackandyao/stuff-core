package com.qbao.aisr.stuff.model.mysql;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liaijun
 * @createTime 17/3/2 下午5:41
 * $$LastChangedDate: 2017-06-08 10:40:13 +0800 (Thu, 08 Jun 2017) $$
 * $$LastChangedRevision: 1125 $$
 * $$LastChangedBy: liaijun $$
 */
public class StuffKeyword implements Serializable {
    private static final long serialVersionUID = "$Id: StuffKeyword.java 1125 2017-06-08 02:40:13Z liaijun $".hashCode();

    //
    private Long id;
    // 图片对应的关键字
    private String keyword;
    //
    private Long userId;

    private String source;
    // 统计日期
    private Date updateTime;
    // 搜索时返回总共数量
    private Integer total;

    private String status;

    // 统计日期

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "StuffKeyword{" + "id=" + id + ", keyword='" + keyword + '\'' + ", userId=" + userId + ", source='" + source + '\'' + ", updateTime=" + updateTime + ", createTime=" + createTime + '}';
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}

package com.qbao.aisr.stuff.model.tbk;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shuaizhihu
 * @createTime 2017/3/6 11:22
 * $$LastChangedDate$$
 * $$LastChangedRevision$$
 * $$LastChangedBy$$
 */
public class CatItem implements Serializable {
    private static final long serialVersionUID ="$Id$".hashCode();
    private long cid;//商品所属类目ID
    private long parentCid;//父类目ID=0时，代表的是一级的类目
    private String name;//类目名称
    private Boolean isParent;//该类目是否为父类目(即：该类目是否还有子类目)
    private String  status;//状态。可选值:normal(正常),deleted(删除)
    private long sortOrder;//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数

    public long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getParentCid() {
        return parentCid;
    }

    public void setParentCid(long parentCid) {
        this.parentCid = parentCid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

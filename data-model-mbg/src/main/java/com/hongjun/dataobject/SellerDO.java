package com.hongjun.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SellerDO implements Serializable {
    private Integer id;

    private String name;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal remarkScore;

    private Integer disabledFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getRemarkScore() {
        return remarkScore;
    }

    public void setRemarkScore(BigDecimal remarkScore) {
        this.remarkScore = remarkScore;
    }

    public Integer getDisabledFlag() {
        return disabledFlag;
    }

    public void setDisabledFlag(Integer disabledFlag) {
        this.disabledFlag = disabledFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", remarkScore=").append(remarkScore);
        sb.append(", disabledFlag=").append(disabledFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
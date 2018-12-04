package com.dmx.api.entity.meta;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "t_operator_meta", schema = "db_dmx", catalog = "")
public class TOperatorMetaEntity {
    private String id;
    private Integer type;
    private String symbol;
    private String description;
    private Long updateTime;
    private Long createTime;
    private String createUserName;
    private String createGroupName;
    private Integer platformId;
    private String platformName;

    @PrePersist
    public void uuid() {
        this.id = UUID.randomUUID().toString().toUpperCase();
        this.updateTime = System.currentTimeMillis()/1000;
        this.createTime = System.currentTimeMillis()/1000;
    }

    @Id
    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "symbol", nullable = false, length = 32)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 8096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_user_name", nullable = false, length = 255)
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Basic
    @Column(name = "create_group_name", nullable = false, length = 255)
    public String getCreateGroupName() {
        return createGroupName;
    }

    public void setCreateGroupName(String createGroupName) {
        this.createGroupName = createGroupName;
    }

    @Basic
    @Column(name = "platform_id", nullable = false)
    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @Basic
    @Column(name = "platform_name", nullable = false, length = 255)
    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TOperatorMetaEntity that = (TOperatorMetaEntity) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (updateTime != that.updateTime) return false;
        if (createTime != that.createTime) return false;
        if (platformId != that.platformId) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createUserName != null ? !createUserName.equals(that.createUserName) : that.createUserName != null)
            return false;
        if (createGroupName != null ? !createGroupName.equals(that.createGroupName) : that.createGroupName != null)
            return false;
        if (platformName != null ? !platformName.equals(that.platformName) : that.platformName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) type;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TOperatorMetaEntity merge(TOperatorMetaEntity o) {
        if (null != o.getType()) this.type = o.getType();
        if (null != o.getSymbol()) this.symbol = o.getSymbol();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


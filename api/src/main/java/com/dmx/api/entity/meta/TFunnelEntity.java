package com.dmx.api.entity.meta;

import javax.persistence.PrePersist;
import java.util.UUID;

@javax.persistence.Entity
@javax.persistence.Table(name = "t_funnel", schema = "db_dmx", catalog = "")
public class TFunnelEntity {
    private String id;
    private String name;
    private Integer step;
    private Integer customerCount;
    private String eventName;
    private Integer eventNameHash;
    private String createUserName;
    private String createGroupName;
    private Integer platformId;
    private String platformName;
    private Long updateTime;
    private Long createTime;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrePersist
    public void uuid() {
        this.id = UUID.randomUUID().toString().toUpperCase();
        this.updateTime = System.currentTimeMillis()/1000;
        this.createTime = System.currentTimeMillis()/1000;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = false, length = 255)
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "step", nullable = false)
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "customer_count", nullable = false)
    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "event_name", nullable = false, length = 255)
    public java.lang.String getEventName() {
        return eventName;
    }

    public void setEventName(java.lang.String eventName) {
        this.eventName = eventName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "event_name_hash", nullable = false)
    public Integer getEventNameHash() {
        return eventNameHash;
    }

    public void setEventNameHash(Integer eventNameHash) {
        this.eventNameHash = eventNameHash;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_user_name", nullable = false, length = 255)
    public java.lang.String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(java.lang.String createUserName) {
        this.createUserName = createUserName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_group_name", nullable = false, length = 255)
    public java.lang.String getCreateGroupName() {
        return createGroupName;
    }

    public void setCreateGroupName(java.lang.String createGroupName) {
        this.createGroupName = createGroupName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "platform_id", nullable = false)
    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "platform_name", nullable = false, length = 255)
    public java.lang.String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(java.lang.String platformName) {
        this.platformName = platformName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "update_time", nullable = false)
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_time", nullable = false)
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        TFunnelEntity that = (TFunnelEntity) object;

        if (id != that.id) return false;
        if (step != that.step) return false;
        if (customerCount != that.customerCount) return false;
        if (eventNameHash != that.eventNameHash) return false;
        if (platformId != that.platformId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null) return false;
        if (createUserName != null ? !createUserName.equals(that.createUserName) : that.createUserName != null)
            return false;
        if (createGroupName != null ? !createGroupName.equals(that.createGroupName) : that.createGroupName != null)
            return false;
        if (platformName != null ? !platformName.equals(that.platformName) : that.platformName != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + step;
        result = 31 * result + customerCount;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + eventNameHash;
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TFunnelEntity merge(TFunnelEntity o) {
        if (null != o.getName()) this.name = o.getName();
        if (null != o.getStep()) this.step = o.getStep();
        if (null != o.getEventName()) this.eventName = o.getEventName();
        if (null != o.getEventNameHash()) this.eventNameHash = o.getEventNameHash();
        if (null != o.getCustomerCount()) this.getCustomerCount();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


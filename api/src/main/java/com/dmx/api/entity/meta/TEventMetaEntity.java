package com.dmx.api.entity.meta;

import javax.persistence.PrePersist;
import java.util.UUID;

@javax.persistence.Entity
@javax.persistence.Table(name = "t_event_meta", schema = "db_dmx", catalog = "")
public class TEventMetaEntity {
    private String id;
    private String name;
    private Integer applicationId;
    private String applicationName;
    private Integer type;
    private String objectId;
    private Integer objectIdHash;
    private String pageUrl;
    private String pageHost;
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
    @javax.persistence.Column(name = "name", nullable = false, length = 512)
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "application_id", nullable = false)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "application_name", nullable = false, length = 255)
    public java.lang.String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(java.lang.String applicationName) {
        this.applicationName = applicationName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "type", nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "object_id", nullable = false, length = 512)
    public java.lang.String getObjectId() {
        return objectId;
    }

    public void setObjectId(java.lang.String objectId) {
        this.objectId = objectId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "object_id_hash", nullable = false)
    public Integer getObjectIdHash() {
        return objectIdHash;
    }

    public void setObjectIdHash(Integer objectIdHash) {
        this.objectIdHash = objectIdHash;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "page_url", nullable = false, length = 512)
    public java.lang.String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(java.lang.String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "page_host", nullable = false, length = 255)
    public java.lang.String getPageHost() {
        return pageHost;
    }

    public void setPageHost(java.lang.String pageHost) {
        this.pageHost = pageHost;
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

        TEventMetaEntity that = (TEventMetaEntity) object;

        if (id != that.id) return false;
        if (applicationId != that.applicationId) return false;
        if (type != that.type) return false;
        if (objectIdHash != that.objectIdHash) return false;
        if (platformId != that.platformId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (applicationName != null ? !applicationName.equals(that.applicationName) : that.applicationName != null)
            return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (pageUrl != null ? !pageUrl.equals(that.pageUrl) : that.pageUrl != null) return false;
        if (pageHost != null ? !pageHost.equals(that.pageHost) : that.pageHost != null) return false;
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
        result = 31 * result + applicationId;
        result = 31 * result + (applicationName != null ? applicationName.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + objectIdHash;
        result = 31 * result + (pageUrl != null ? pageUrl.hashCode() : 0);
        result = 31 * result + (pageHost != null ? pageHost.hashCode() : 0);
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    public TEventMetaEntity merge(TEventMetaEntity o) {
        if (null != o.getName()) this.name = o.getName();
        if (null != o.getType()) this.type = o.getType();
        if (null != o.getApplicationId()) this.applicationId = o.getApplicationId();
        if (null != o.getApplicationName()) this.applicationName = o.getApplicationName();
        if (null != o.getObjectId()) this.objectId = o.getObjectId();
        if (null != o.getObjectIdHash()) this.objectIdHash = o.getObjectIdHash();
        if (null != o.getPageUrl()) this.pageUrl = o.getPageUrl();
        if (null != o.getPageHost()) this.pageHost = o.getPageHost();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


package com.dmx.api.entity;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@javax.persistence.Entity
@javax.persistence.Table(name = "t_tag_meta", schema = "db_dmx", catalog = "")
public class TTagMetaEntity {
    private String id;
    @NotNull(message = "name must not be null")
    private String name;
    @NotNull(message = "type must not be null")
    private short type;
    @NotNull(message = "isStatic must not be null")
    private byte isStatic;
    @NotNull(message = "category must not be null")
    private String category;
    private String parentCategory;
    private String categoryTree;
    @NotNull(message = "rules must not be null")
    private String rules;
    private String description;
    private Timestamp updateTime;
    private Timestamp createTime;
    private String createUserName;
    private String createGroupName;
    private int platformId;
    private String platformName;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false, length = 64)
    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @PrePersist
    public void uuid() {
        this.id = UUID.randomUUID().toString().toUpperCase();
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = false, length = 64)
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "type", nullable = false)
    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "is_static", nullable = false)
    public byte getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(byte isStatic) {
        this.isStatic = isStatic;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "category", nullable = false, length = 512)
    public java.lang.String getCategory() {
        return category;
    }

    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "parent_category", nullable = false, length = 512)
    public java.lang.String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(java.lang.String parentCategory) {
        this.parentCategory = parentCategory;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "category_tree", nullable = false, length = 8096)
    public java.lang.String getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(java.lang.String categoryTree) {
        this.categoryTree = categoryTree;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "rules", nullable = false, length = 8096)
    public java.lang.String getRules() {
        return rules;
    }

    public void setRules(java.lang.String rules) {
        this.rules = rules;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "description", nullable = false, length = 1024)
    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "update_time", nullable = false)
    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "create_time", nullable = false)
    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
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
    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
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

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        TTagMetaEntity that = (TTagMetaEntity) object;

        if (type != that.type) return false;
        if (isStatic != that.isStatic) return false;
        if (platformId != that.platformId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (parentCategory != null ? !parentCategory.equals(that.parentCategory) : that.parentCategory != null)
            return false;
        if (categoryTree != null ? !categoryTree.equals(that.categoryTree) : that.categoryTree != null) return false;
        if (rules != null ? !rules.equals(that.rules) : that.rules != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUserName != null ? !createUserName.equals(that.createUserName) : that.createUserName != null)
            return false;
        if (createGroupName != null ? !createGroupName.equals(that.createGroupName) : that.createGroupName != null)
            return false;
        if (platformName != null ? !platformName.equals(that.platformName) : that.platformName != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (int) isStatic;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        result = 31 * result + (categoryTree != null ? categoryTree.hashCode() : 0);
        result = 31 * result + (rules != null ? rules.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TTagMetaEntity merge(TTagMetaEntity o) {
        if (null != o.getName()) this.name = o.getName();
        if (0 < o.getType()) this.type = o.getType();
        if (0 < o.getIsStatic()) this.isStatic = o.getIsStatic();
        if (null != o.getCategory()) this.category = o.getCategory();
        if (null != o.getParentCategory()) this.parentCategory = o.getParentCategory();
        if (null != o.getRules()) this.rules = o.getRules();
        if (null != o.getDescription()) this.description = o.getDescription();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (0 < o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


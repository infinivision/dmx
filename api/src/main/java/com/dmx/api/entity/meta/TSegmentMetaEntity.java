package com.dmx.api.entity.meta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "t_segment_meta", schema = "db_dmx", catalog = "")
public class TSegmentMetaEntity {
    private String id;
    @NotNull(message = "name must not be null")
    private String name;
    private String category;
    private String parentCategory;
    private String categoryTree;
    @NotNull(message = "rules must not be null")
    private String rules;
    private Long customerCount;
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
        this.updateTime = System.currentTimeMillis();
        this.createTime = System.currentTimeMillis();
    }

    @Id
    @Column(name = "id", nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "category", nullable = false, length = 512)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "parent_category", nullable = false, length = 512)
    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Basic
    @Column(name = "category_tree", nullable = false, length = 8096)
    public String getCategoryTree() {
        return categoryTree;
    }

    public void setCategoryTree(String categoryTree) {
        this.categoryTree = categoryTree;
    }

    @Basic
    @Column(name = "rules", nullable = false, length = 8096)
    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    @Basic
    @Column(name = "customer_count", nullable = false)
    public Long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Long customerCount) {
        this.customerCount = customerCount;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 1024)
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

        TSegmentMetaEntity that = (TSegmentMetaEntity) o;

        if (customerCount != that.customerCount) return false;
        if (updateTime != that.updateTime) return false;
        if (createTime != that.createTime) return false;
        if (platformId != that.platformId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (parentCategory != null ? !parentCategory.equals(that.parentCategory) : that.parentCategory != null)
            return false;
        if (categoryTree != null ? !categoryTree.equals(that.categoryTree) : that.categoryTree != null) return false;
        if (rules != null ? !rules.equals(that.rules) : that.rules != null) return false;
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
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        result = 31 * result + (categoryTree != null ? categoryTree.hashCode() : 0);
        result = 31 * result + (rules != null ? rules.hashCode() : 0);
        result = 31 * result + (int) (customerCount ^ (customerCount >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TSegmentMetaEntity merge(TSegmentMetaEntity o) {
        if (null != o.getName()) this.name = o.getName();
        if (null != o.getCategory()) this.category = o.getCategory();
        if (null != o.getParentCategory()) this.parentCategory = o.getParentCategory();
        if (null != o.getRules()) this.rules = o.getRules();
        if (null != o.getDescription()) this.description = o.getDescription();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


package com.dmx.api.entity.meta;

import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "t_column_meta", schema = "db_dmx", catalog = "")
public class TColumnMetaEntity {
    private String id;
    private String columnName;
    private String category;
    private Integer calculateFlag;
    private String typeName;
    private String enumValues;
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
    @Column(name = "column_name", nullable = false, length = 255)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "category", nullable = false, length = 255)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "calculate_flag", nullable = false, length = 255)
    public Integer getCalculateFlag() {
        return calculateFlag;
    }

    public void setCalculateFlag(Integer calculateFlag) {
        this.calculateFlag = calculateFlag;
    }


    @Basic
    @Column(name = "type_name", nullable = false, length = 255)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "enum_values", nullable = false, length = 8196)
    public String getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(String enumValues) {
        this.enumValues = enumValues;
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

        TColumnMetaEntity that = (TColumnMetaEntity) o;

        if (id != that.id) return false;
        if (updateTime != that.updateTime) return false;
        if (createTime != that.createTime) return false;
        if (platformId != that.platformId) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
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
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TColumnMetaEntity merge(TColumnMetaEntity o) {
        if (null != o.getColumnName()) this.columnName = o.getColumnName();
        if (null != o.getCategory()) this.category = o.getCategory();
        if (null != o.getTypeName()) this.typeName = o.getTypeName();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


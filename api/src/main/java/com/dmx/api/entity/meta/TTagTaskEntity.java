package com.dmx.api.entity.meta;

import javax.persistence.*;

@Entity
@Table(name = "t_tag_task", schema = "db_dmx", catalog = "")
public class TTagTaskEntity {
    private String id;
    private Integer groupId;
    private Integer status;
    private String jobId;
    private String jobName;
    private String tagList;
    private Long updateTime;
    private Long createTime;
    private String createUserName;
    private String createGroupName;
    private Integer platformId;
    private String platformName;

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id", nullable = false)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "job_id", nullable = false, length = 255)
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "job_name", nullable = false, length = 255)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "tag_list", nullable = false, length = 8096)
    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
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

        TTagTaskEntity that = (TTagTaskEntity) o;

        if (groupId != that.groupId) return false;
        if (status != that.status) return false;
        if (updateTime != that.updateTime) return false;
        if (createTime != that.createTime) return false;
        if (platformId != that.platformId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if (jobName != null ? !jobName.equals(that.jobName) : that.jobName != null) return false;
        if (tagList != null ? !tagList.equals(that.tagList) : that.tagList != null) return false;
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
        result = 31 * result + groupId;
        result = 31 * result + (int) status;
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (jobName != null ? jobName.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }

    public TTagTaskEntity merge(TTagTaskEntity o) {
        if (null != o.getJobId()) this.jobId = o.getJobId();
        if (null != o.getJobName()) this.jobName = o.getJobName();
        if (null != o.getGroupId()) this.groupId = o.getGroupId();
        if (null != o.getStatus()) this.status = o.getStatus();
        if (null != o.getTagList()) this.tagList = o.getTagList();
        if (null != o.getUpdateTime()) this.updateTime = o.getUpdateTime();
        if (null != o.getCreateTime()) this.createTime = o.getCreateTime();
        if (null != o.getCreateUserName()) this.createUserName = o.getCreateUserName();
        if (null != o.getCreateGroupName()) this.createGroupName = o.getCreateGroupName();
        if (null != o.getPlatformId()) this.platformId = o.getPlatformId();
        if (null != o.getPlatformName()) this.platformName = o.getPlatformName();

        return this;
    }
}


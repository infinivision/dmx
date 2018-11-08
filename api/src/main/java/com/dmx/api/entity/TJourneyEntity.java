package com.dmx.api.entity;

@javax.persistence.Entity
@javax.persistence.Table(name = "t_journey", schema = "db_dmx", catalog = "")
public class TJourneyEntity {
    private long id;
    private String name;
    private int step;
    private String eventName;
    private Integer eventNameHash;
    private Integer customerCount;
    private String createUserName;
    private String createGroupName;
    private int platformId;
    private String platformName;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @javax.persistence.Column(name = "step", nullable = false)
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "event_name", nullable = false, length = 512)
    public java.lang.String getEventName() {
        return eventName;
    }

    public void setEventName(java.lang.String eventName) {
        this.eventName = eventName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "event_name_hash", nullable = true)
    public java.lang.Integer getEventNameHash() {
        return eventNameHash;
    }

    public void setEventNameHash(java.lang.Integer eventNameHash) {
        this.eventNameHash = eventNameHash;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "customer_count", nullable = true)
    public java.lang.Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(java.lang.Integer customerCount) {
        this.customerCount = customerCount;
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

        TJourneyEntity that = (TJourneyEntity) object;

        if (id != that.id) return false;
        if (step != that.step) return false;
        if (platformId != that.platformId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null) return false;
        if (eventNameHash != null ? !eventNameHash.equals(that.eventNameHash) : that.eventNameHash != null)
            return false;
        if (customerCount != null ? !customerCount.equals(that.customerCount) : that.customerCount != null)
            return false;
        if (createUserName != null ? !createUserName.equals(that.createUserName) : that.createUserName != null)
            return false;
        if (createGroupName != null ? !createGroupName.equals(that.createGroupName) : that.createGroupName != null)
            return false;
        if (platformName != null ? !platformName.equals(that.platformName) : that.platformName != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + step;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (eventNameHash != null ? eventNameHash.hashCode() : 0);
        result = 31 * result + (customerCount != null ? customerCount.hashCode() : 0);
        result = 31 * result + (createUserName != null ? createUserName.hashCode() : 0);
        result = 31 * result + (createGroupName != null ? createGroupName.hashCode() : 0);
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }
}


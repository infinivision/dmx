package com.dmx.api.entity.analysis;

import javax.persistence.*;

@Entity
@Table(name = "t_customer", schema = "public", catalog = "db_dmx_stage")
public class TCustomerEntity {
    private long id;
    private String name;
    private String email;
    private String phoneNum;
    private String gender;
    private short age;
    private String education;
    private String birthday;
    private String cardId;
    private int monthlySalary;
    private int yearSalary;
    private String language;
    private String state;
    private String province;
    private String city;
    private String company;
    private String position;
    private String companyAddress;
    private String companyDistrict;
    private String resideAddress;
    private String resideDistrict;
    private int registerDate;
    private String registerChannel;
    private long updateTime;
    private long createTime;
    private int platformId;
    private String platformName;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    @Column(name = "email", nullable = false, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone_num", nullable = false, length = 64)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Basic
    @Column(name = "gender", nullable = false, length = 32)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "age", nullable = false)
    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    @Basic
    @Column(name = "education", nullable = false, length = 32)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "card_id", nullable = false, length = 32)
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Basic
    @Column(name = "monthly_salary", nullable = false)
    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Basic
    @Column(name = "year_salary", nullable = false)
    public int getYearSalary() {
        return yearSalary;
    }

    public void setYearSalary(int yearSalary) {
        this.yearSalary = yearSalary;
    }

    @Basic
    @Column(name = "language", nullable = false, length = 64)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 32)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "province", nullable = false, length = 512)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city", nullable = false, length = 512)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "company", nullable = false, length = 512)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "position", nullable = false, length = 64)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "company_address", nullable = false, length = 512)
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Basic
    @Column(name = "company_district", nullable = false, length = 512)
    public String getCompanyDistrict() {
        return companyDistrict;
    }

    public void setCompanyDistrict(String companyDistrict) {
        this.companyDistrict = companyDistrict;
    }

    @Basic
    @Column(name = "reside_address", nullable = false, length = 512)
    public String getResideAddress() {
        return resideAddress;
    }

    public void setResideAddress(String resideAddress) {
        this.resideAddress = resideAddress;
    }

    @Basic
    @Column(name = "reside_district", nullable = false, length = 512)
    public String getResideDistrict() {
        return resideDistrict;
    }

    public void setResideDistrict(String resideDistrict) {
        this.resideDistrict = resideDistrict;
    }

    @Basic
    @Column(name = "register_date", nullable = false)
    public int getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(int registerDate) {
        this.registerDate = registerDate;
    }

    @Basic
    @Column(name = "register_channel", nullable = false, length = 64)
    public String getRegisterChannel() {
        return registerChannel;
    }

    public void setRegisterChannel(String registerChannel) {
        this.registerChannel = registerChannel;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "platform_id", nullable = false)
    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
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

        TCustomerEntity that = (TCustomerEntity) o;

        if (id != that.id) return false;
        if (age != that.age) return false;
        if (birthday != that.birthday) return false;
        if (monthlySalary != that.monthlySalary) return false;
        if (yearSalary != that.yearSalary) return false;
        if (registerDate != that.registerDate) return false;
        if (updateTime != that.updateTime) return false;
        if (createTime != that.createTime) return false;
        if (platformId != that.platformId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNum != null ? !phoneNum.equals(that.phoneNum) : that.phoneNum != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (companyAddress != null ? !companyAddress.equals(that.companyAddress) : that.companyAddress != null)
            return false;
        if (companyDistrict != null ? !companyDistrict.equals(that.companyDistrict) : that.companyDistrict != null)
            return false;
        if (resideAddress != null ? !resideAddress.equals(that.resideAddress) : that.resideAddress != null)
            return false;
        if (resideDistrict != null ? !resideDistrict.equals(that.resideDistrict) : that.resideDistrict != null)
            return false;
        if (registerChannel != null ? !registerChannel.equals(that.registerChannel) : that.registerChannel != null)
            return false;
        if (platformName != null ? !platformName.equals(that.platformName) : that.platformName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNum != null ? phoneNum.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (int) age;
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + monthlySalary;
        result = 31 * result + yearSalary;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (companyAddress != null ? companyAddress.hashCode() : 0);
        result = 31 * result + (companyDistrict != null ? companyDistrict.hashCode() : 0);
        result = 31 * result + (resideAddress != null ? resideAddress.hashCode() : 0);
        result = 31 * result + (resideDistrict != null ? resideDistrict.hashCode() : 0);
        result = 31 * result + registerDate;
        result = 31 * result + (registerChannel != null ? registerChannel.hashCode() : 0);
        result = 31 * result + (int) (updateTime ^ (updateTime >>> 32));
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + platformId;
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        return result;
    }
}
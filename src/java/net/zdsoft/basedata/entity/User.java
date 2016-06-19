package net.zdsoft.basedata.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name = "base_user")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public static final int OWNER_TYPE_STUDENT = 1;
	public static final int OWNER_TYPE_TEACHER = 2;
	public static final int OWNER_TYPE_FAMILY = 3;

	private String unitId;
	private Integer sequence;
	private String accountId;
	private String ownerId;
	private Integer ownerType;
	private String username;
	private String realName;
	private Integer userState;
	private Integer userType;
	private String email;
	private String regionCode;
	private Integer displayOrder;
	private Integer sex;
	private String chargeNumber;
	private Integer chargeNumberType;
	private Integer orderStatus;
	private String nickName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	private Integer isDeleted;
	private Integer eventSource;
	private String password;
	private Integer subsystemAdmin;
	private String deptId;
	private Integer roleType;
	private Integer userRole;
	private String classId;
	private Integer iconIndex;
	private Integer authProperty;
	private String signature;
	private Date birthday;
	private Integer enrollYear;
	private String webpage;
	private String summary;
	private String mobilePhone;
	private String address;
	private String zipCode;
	private String officeTel;
	private String homeTel;
	private String qq;
	private String msn;
	private String orgVisual;
	private String bgImg;
	private String pinyinAll;
	@Transient
	private Long sequenceIntId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Override
	public String fetchCacheEntitName() {
		return "user";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public Integer getChargeNumberType() {
		return chargeNumberType;
	}

	public void setChargeNumberType(Integer chargeNumberType) {
		this.chargeNumberType = chargeNumberType;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getEventSource() {
		return eventSource;
	}

	public void setEventSource(Integer eventSource) {
		this.eventSource = eventSource;
	}

	public Integer getSubsystemAdmin() {
		return subsystemAdmin;
	}

	public void setSubsystemAdmin(Integer subsystemAdmin) {
		this.subsystemAdmin = subsystemAdmin;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Integer getIconIndex() {
		return iconIndex;
	}

	public void setIconIndex(Integer iconIndex) {
		this.iconIndex = iconIndex;
	}

	public Integer getAuthProperty() {
		return authProperty;
	}

	public void setAuthProperty(Integer authProperty) {
		this.authProperty = authProperty;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getEnrollYear() {
		return enrollYear;
	}

	public void setEnrollYear(Integer enrollYear) {
		this.enrollYear = enrollYear;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getOrgVisual() {
		return orgVisual;
	}

	public void setOrgVisual(String orgVisual) {
		this.orgVisual = orgVisual;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getPinyinAll() {
		return pinyinAll;
	}

	public void setPinyinAll(String pinyinAll) {
		this.pinyinAll = pinyinAll;
	}

	public Long getSequenceIntId() {
		return sequenceIntId;
	}

	public void setSequenceIntId(Long sequenceIntId) {
		this.sequenceIntId = sequenceIntId;
	}

}

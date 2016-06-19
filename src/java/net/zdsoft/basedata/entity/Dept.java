package net.zdsoft.basedata.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import net.zdsoft.framework.annotation.ColumnInfo;
import net.zdsoft.framework.entity.BaseEntity;
import net.zdsoft.framework.entity.Constant;

@Entity
@Table(name = "base_dept")
public class Dept extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@ColumnInfo(displayName = "单位ID", hide = true)
	private String unitId;
	@ColumnInfo(displayName = "名称")
	private String deptName;
	@ColumnInfo(displayName = "上级部门", vtype = ColumnInfo.VTYPE_SELECT, vsql = "select '00000000000000000000000000000000', '一级部门' from dual union select id, dept_name from base_dept where is_deleted = '0' and unit_id = {unitId} and id <> {id}")
	private String parentId;
	@ColumnInfo(displayName = "编号")
	private String deptCode;
	@ColumnInfo(displayName = "备注")
	private String remark;
	@ColumnInfo(displayName = "办公电话")
	private String deptTel;
	@ColumnInfo(displayName = "类型", vtype = ColumnInfo.VTYPE_SELECT, mcodeId = "DM-BMLX")
	private Integer deptType;
	@ColumnInfo(displayName = "负责人")
	private String teacherId;
	@ColumnInfo(displayName = "排序号", vtype = ColumnInfo.VTYPE_INT)
	private Integer displayOrder;
	private Integer isDefault;
	@ColumnInfo(displayName = "创建时间", disabled = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnInfo(displayName = "修改时间", disabled = true)
	private Date modifyTime;
	@ColumnInfo(displayName = "是否删除", vtype = ColumnInfo.VTYPE_RADIO, mcodeId = "DM-BOOLEAN")
	private Integer isDeleted;
	private Integer eventSource;
	private Integer deptState;
	private String leaderId;
	private String instituteId;
	private String areaId;
	private String deputyHeadId;
	private String deptShortName;
	@Transient
	@ColumnInfo(disabled = true)
	private Long sequenceIntId;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getIsDeleted() {
		if (isDeleted == null)
			isDeleted = 0;
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	@Override
	public String fetchCacheEntitName() {
		return "dept";
	}

	public String getDeptTel() {
		return deptTel;
	}

	public void setDeptTel(String deptTel) {
		this.deptTel = deptTel;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getEventSource() {
		return eventSource;
	}

	public void setEventSource(Integer eventSource) {
		this.eventSource = eventSource;
	}

	public Integer getDeptState() {
		return deptState;
	}

	public void setDeptState(Integer deptState) {
		this.deptState = deptState;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDeputyHeadId() {
		return deputyHeadId;
	}

	public void setDeputyHeadId(String deputyHeadId) {
		this.deputyHeadId = deputyHeadId;
	}

	public Long getSequenceIntId() {
		return sequenceIntId;
	}

	public void setSequenceIntId(Long sequenceIntId) {
		this.sequenceIntId = sequenceIntId;
	}
}

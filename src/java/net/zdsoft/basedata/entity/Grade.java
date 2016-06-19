package net.zdsoft.basedata.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name="base_grade")
public class Grade extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String gradeName;
	private String schoolId;
	private Integer amLessonCount;
	private Integer pmLessonCount;
	private Integer nightLessonCount;
	private String openAcadyear;
	private Integer schoolingLength;
	private Integer isGraduate;
	private Integer section;
	private String teacherId;
	private Integer displayOrder;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;
	private Integer isDeleted;
	private Integer eventSource;
	private String gradeCode;
	private String subschoolId;

	@Override
	public String fetchCacheEntitName() {
		return "grade";
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getAmLessonCount() {
		return amLessonCount;
	}

	public void setAmLessonCount(Integer amLessonCount) {
		this.amLessonCount = amLessonCount;
	}

	public Integer getPmLessonCount() {
		return pmLessonCount;
	}

	public void setPmLessonCount(Integer pmLessonCount) {
		this.pmLessonCount = pmLessonCount;
	}

	public Integer getNightLessonCount() {
		return nightLessonCount;
	}

	public void setNightLessonCount(Integer nightLessonCount) {
		this.nightLessonCount = nightLessonCount;
	}

	public String getOpenAcadyear() {
		return openAcadyear;
	}

	public void setOpenAcadyear(String openAcadyear) {
		this.openAcadyear = openAcadyear;
	}

	public Integer getSchoolingLength() {
		return schoolingLength;
	}

	public void setSchoolingLength(Integer schoolingLength) {
		this.schoolingLength = schoolingLength;
	}

	public Integer getIsGraduate() {
		return isGraduate;
	}

	public void setIsGraduate(Integer isGraduate) {
		this.isGraduate = isGraduate;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getEventSource() {
		return eventSource;
	}

	public void setEventSource(Integer eventSource) {
		this.eventSource = eventSource;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getSubschoolId() {
		return subschoolId;
	}

	public void setSubschoolId(String subschoolId) {
		this.subschoolId = subschoolId;
	}

}

package net.zdsoft.basedata.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name = "base_mcode_detail")
public class McodeDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String mcodeId;
	private String thisId;
	private String mcodeContent;
	private Integer isUsing;
	private Integer mcodeType;
	private Integer displayOrder;
	private String fieldType;
	private String fieldValue;
	private String parentThisId;
	private Integer isFolder;

	public String getThisId() {
		return thisId;
	}

	public void setThisId(String thisId) {
		this.thisId = thisId;
	}

	public String getMcodeId() {
		return mcodeId;
	}

	public void setMcodeId(String mcodeId) {
		this.mcodeId = mcodeId;
	}

	public String getMcodeContent() {
		return mcodeContent;
	}

	public void setMcodeContent(String mcodeContent) {
		this.mcodeContent = mcodeContent;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getMcodeType() {
		return mcodeType;
	}

	public void setMcodeType(Integer mcodeType) {
		this.mcodeType = mcodeType;
	}

	public Integer getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(Integer isFolder) {
		this.isFolder = isFolder;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getParentThisId() {
		return parentThisId;
	}

	public void setParentThisId(String parentThisId) {
		this.parentThisId = parentThisId;
	}

	@Override
	public String fetchCacheEntitName() {
		return "mcodedetail";
	}

}

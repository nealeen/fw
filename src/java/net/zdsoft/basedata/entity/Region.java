package net.zdsoft.basedata.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name="base_region")
public class Region extends BaseEntity {

	private static final long serialVersionUID = -5530280145433605527L;
	private String fullCode;
	private String fullName;
	private String regionCode;
	private String regionName;
	private String type;

	@Override
	public String fetchCacheEntitName() {
		return "region";
	}

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

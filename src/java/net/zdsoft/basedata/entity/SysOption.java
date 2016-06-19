package net.zdsoft.basedata.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name="base_sys_option")
public class SysOption extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String optionCode;
	private String name;
	private String defaultValue;
	private String description;
	private String nowValue;
	private String validateJs;
	private Integer viewable;
	private Integer eventSource;
	private Integer isDeleted;

	@Override
	public String fetchCacheEntitName() {
		return "sysOption";
	}

	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNowValue() {
		return nowValue;
	}

	public void setNowValue(String nowValue) {
		this.nowValue = nowValue;
	}

	public String getValidateJs() {
		return validateJs;
	}

	public void setValidateJs(String validateJs) {
		this.validateJs = validateJs;
	}

	public Integer getViewable() {
		return viewable;
	}

	public void setViewable(Integer viewable) {
		this.viewable = viewable;
	}

	public Integer getEventSource() {
		return eventSource;
	}

	public void setEventSource(Integer eventSource) {
		this.eventSource = eventSource;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}

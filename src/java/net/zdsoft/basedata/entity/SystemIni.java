package net.zdsoft.basedata.entity;

import javax.persistence.Table;

import net.zdsoft.framework.entity.BaseEntity;

@Table(name="sys_option")
public class SystemIni extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String iniid;
	private String name;
	private String dvalue;
	private String description;
	private String nowvalue;
	private Integer viewable;
	private String validatejs;
	private Integer orderid;
	private String subsystemid;
	private Integer coercive;
	private Integer orderId;
	private String subsystemId;
	private String validateJs;
	private Integer viewAble;

	
	@Override
	public String fetchCacheEntitName() {
		return "systemIni";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIniid() {
		return iniid;
	}


	public void setIniid(String iniid) {
		this.iniid = iniid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDvalue() {
		return dvalue;
	}


	public void setDvalue(String dvalue) {
		this.dvalue = dvalue;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNowvalue() {
		return nowvalue;
	}


	public void setNowvalue(String nowvalue) {
		this.nowvalue = nowvalue;
	}


	public Integer getViewable() {
		return viewable;
	}


	public void setViewable(Integer viewable) {
		this.viewable = viewable;
	}


	public String getValidatejs() {
		return validatejs;
	}


	public void setValidatejs(String validatejs) {
		this.validatejs = validatejs;
	}


	public Integer getOrderid() {
		return orderid;
	}


	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}


	public String getSubsystemid() {
		return subsystemid;
	}


	public void setSubsystemid(String subsystemid) {
		this.subsystemid = subsystemid;
	}


	public Integer getCoercive() {
		return coercive;
	}


	public void setCoercive(Integer coercive) {
		this.coercive = coercive;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getSubsystemId() {
		return subsystemId;
	}


	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}


	public String getValidateJs() {
		return validateJs;
	}


	public void setValidateJs(String validateJs) {
		this.validateJs = validateJs;
	}


	public Integer getViewAble() {
		return viewAble;
	}


	public void setViewAble(Integer viewAble) {
		this.viewAble = viewAble;
	}

}

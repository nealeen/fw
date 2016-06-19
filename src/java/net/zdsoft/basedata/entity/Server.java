package net.zdsoft.basedata.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.zdsoft.framework.entity.BaseEntity;

@Entity
@Table(name="base_server")
public class Server extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer status;
	private String code;
	private Integer baseSyncType;
	private Integer interfaceType;
	private String capabilityurl;
	private String introduceurl;
	private String indexUrl;
	private String linkPhone;
	private String linkMan;
	private String appoint;
	private String serverKey;
	private String protocol;
	private String domain;
	private Integer port;
	private Integer isPassport;
	private String serverCode;
	private Integer serverTypeId;
	private Integer isDeleted;
	private Integer eventSource;
	private String context;
	private Integer serverClass;
	private String secondDomain;

	@Override
	public String fetchCacheEntitName() {
		return "server";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getBaseSyncType() {
		return baseSyncType;
	}

	public void setBaseSyncType(Integer baseSyncType) {
		this.baseSyncType = baseSyncType;
	}

	public Integer getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getCapabilityurl() {
		return capabilityurl;
	}

	public void setCapabilityurl(String capabilityurl) {
		this.capabilityurl = capabilityurl;
	}

	public String getIntroduceurl() {
		return introduceurl;
	}

	public void setIntroduceurl(String introduceurl) {
		this.introduceurl = introduceurl;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getAppoint() {
		return appoint;
	}

	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getIsPassport() {
		return isPassport;
	}

	public void setIsPassport(Integer isPassport) {
		this.isPassport = isPassport;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public Integer getServerTypeId() {
		return serverTypeId;
	}

	public void setServerTypeId(Integer serverTypeId) {
		this.serverTypeId = serverTypeId;
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

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getServerClass() {
		return serverClass;
	}

	public void setServerClass(Integer serverClass) {
		this.serverClass = serverClass;
	}

	public String getSecondDomain() {
		return secondDomain;
	}

	public void setSecondDomain(String secondDomain) {
		this.secondDomain = secondDomain;
	}

}

package net.zdsoft.basedata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_model")
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public final static int PARENT_ID_DIRECT_SUBSYSTEM = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String mid;
    @Column(name = "parentid")
    private Integer parentId;
    @Column(name = "orderid")
    private Integer displayOrder;
    private String name;
    private String type;
    private String picture;
    private String url;

    @Column(name = "subsystem")
    private Integer subSystem;
    @Column(name = "usertype")
    private String userType;
    @Column(name = "unitclass")
    private Integer unitClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(Integer unitClass) {
        this.unitClass = unitClass;
    }
}

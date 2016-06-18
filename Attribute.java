package aio.com.entity;

import org.apache.commons.lang3.StringUtils;

import aio.com.enums.AttributeEnum;
import aio.com.enums.AttributeTypeEnum;

public class Attribute extends BaseEntity {
    private String code;
    private String heroId;
    private long cvalue;
    private long ovalue;
    private String attributeType;

    public void put(String heroId, String code, long cvalue, long ovalue, String attributeType) {
        this.code = code;
        this.cvalue = cvalue;
        this.ovalue = ovalue;
        this.attributeType = attributeType;
        this.heroId = heroId;
    }

    public void put(String heroId, String code, long value, String attributeType) {
        this.code = code;
        this.cvalue = value;
        this.ovalue = value;
        this.attributeType = attributeType;
        this.heroId = heroId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public long getCvalue() {
        return cvalue;
    }

    public void setCvalue(long cvalue) {
        this.cvalue = cvalue;
    }

    public long getOvalue() {
        return ovalue;
    }

    public void setOvalue(long ovalue) {
        this.ovalue = ovalue;
    }

    public String getAttributeType() {
        if (StringUtils.isBlank(attributeType))
            attributeType = AttributeTypeEnum.attr.name();
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String takeDesc() {
        return AttributeEnum.valueOf(getCode()).name() + "[" + getCvalue() + "/" + getOvalue()
                + "]";
    }

    @Override
    public String takeType() {
        // TODO Auto-generated method stub
        return null;
    }

}

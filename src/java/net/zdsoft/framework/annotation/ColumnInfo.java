package net.zdsoft.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnInfo {

	public static final String VTYPE_EMAIL = "email";
	public static final String VTYPE_URL = "url";
	public static final String VTYPE_INT = "int";
	public static final String VTYPE_STRING = "string";
	public static final String VTYPE_DATE = "date";
	public static final String VTYPE_SELECT = "select";
	public static final String VTYPE_RADIO = "radio";
	public static final String VTYPE_CHECKBOX = "checkbox";
	public static final String VTYPE_RANGEDATE = "rangedate";
	public static final String UNIT_TYPE_1 = "1";
	public static final String UNIT_TYPE_2 = "2";
	public static final String UNIT_TYPE_3 = "3";
	public static final String UNIT_TYPE_4 = "4";
	public static final String UNIT_TYPE_5 = "5"; 
	
	public String vsql() default "";
	public String vextends() default "";
	public String unitType() default ""; 
	public boolean hide() default false;
	public boolean disabled() default false;
	public String format() default "";
	public String mcodeId() default "";
	public String displayName() default "";
	public boolean nullable() default true;
	public int maxLength() default 1000;
	public int minLength() default 0;
	public int length() default 0;
	public String max() default "";
	public String min() default "";
	public String regex() default "";
	public String regexTip() default "";
	public String vtype() default "";
}

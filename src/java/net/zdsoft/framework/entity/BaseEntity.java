package net.zdsoft.framework.entity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.zdsoft.framework.annotation.ColumnInfo;
import net.zdsoft.framework.utils.ToolUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4965074185832333543L;

	@Id
	@Column(length = 32, updatable=false)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@ColumnInfo(hide = true, nullable = false, displayName = "编号")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract String fetchCacheEntitName();

	public static void main(String[] args) throws Exception {
		List<String> ss = FileUtils.readLines(new File("C:\\Users\\qq\\Desktop\\Temp\\2.txt"));
		List<String> sf = new ArrayList<String>();
		for (String s : ss) {
			s = s.trim();
			String name = StringUtils.substringBefore(s, " ").toLowerCase();
			String s1 = ToolUtils.underline2Camel(name);
			if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), "char")) {
				sf.add("private String " + s1 + ";");
			} else if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), "Number")) {
				if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), ",")) {
					sf.add("private Double " + s1 + ";");
				} else if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), "30"))
					sf.add("private Long " + s1 + ";");
				else
					sf.add("private Integer " + s1 + ";");
			} else if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), "Date")) {
				sf.add("private Date " + s1 + ";");
			} else if (StringUtils.containsIgnoreCase(StringUtils.substringAfter(s, " "), "Timestamp")) {
				sf.add("@Temporal(TemporalType.TIMESTAMP)");
				sf.add("private Date " + s1 + ";");
			} else
				sf.add("private -------------------------------- " + s1 + ";");
		}
		FileUtils.writeLines(new File("C:\\Users\\qq\\Desktop\\Temp\\2.txt"), sf);
	}

}

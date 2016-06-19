package net.zdsoft.basedata.dto;

import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.Unit;

import com.wanpeng.basedata.remote.dto.BaseDto;

public class TeacherDto extends BaseDto {
	private static final long serialVersionUID = -5504279997746666085L;
	private Teacher teacher = new Teacher();

	private Unit unit;
	private Dept dept;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

}

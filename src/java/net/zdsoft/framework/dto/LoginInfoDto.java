package net.zdsoft.framework.dto;

import net.zdsoft.basedata.entity.Family;
import net.zdsoft.basedata.entity.Student;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.entity.LoginInfo;

import com.wanpeng.basedata.remote.dto.BaseDto;

public class LoginInfoDto extends BaseDto {
    private LoginInfo loginInfo;
    private User user;
    private Unit unit;
    private Teacher teacher;
    private Student student;
    private Family family;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

}

package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Teacher;

public interface TeacherExtDao {
    List<Teacher> findByDeptIdAndSex(String deptId, String sex);
}

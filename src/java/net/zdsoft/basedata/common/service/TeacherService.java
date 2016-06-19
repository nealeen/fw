package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.framework.utils.Pagination;

public interface TeacherService extends BaseService<Teacher, String>{

    List<Teacher> findByUnitId(String unitId);

    List<Teacher> findByDeptId(String deptId);

    List<Teacher> findByDeptId(String deptId, Pagination page);
    
    Teacher findByUsername(String username);
    
    Teacher findByUserId(String userId);
    
}

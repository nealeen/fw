package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.framework.utils.Pagination;

public interface DeptService extends BaseService<Dept, String>{
    List<Dept> findByUnitId(String unitId);
    
    List<Dept> findByParentId(String parentId);

    Integer countByUnitId(String unitId);

    List<Dept> findByUnitId(String unitId, Pagination page);

}

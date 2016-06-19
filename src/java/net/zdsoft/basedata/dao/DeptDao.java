package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Dept;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface DeptDao extends BaseJapRepositoryDao<Dept, String> {

    @Query("from Dept where unitId = ?1 and isDeleted = 0")
    List<Dept> findByUnitId(String unitId);
    
    @Query("from Dept where parentId = ?1 and isDeleted = 0")
    List<Dept> findByParentId(String parentId);

    @Query("from Dept where unitId = ?1 and isDeleted = 0")
    List<Dept> findByUnitId(String unitId, Pageable page);

    @Query("select count(*) from Dept where isDeleted = 0 and unitId = ?1")
    Integer countByUnitId(String unitId);
    
}

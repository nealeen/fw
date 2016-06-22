package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;
import net.zdsoft.framework.utils.Pagination;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UnitDao extends BaseJapRepositoryDao<Unit, String> {

    @Query("From Unit where parentId = '00000000000000000000000000000000' and isDeleted = '0'")
    Unit findTopUnit();

    @Query("From Unit where parentId = ?1 and isDeleted = '0'")
    List<Unit> findDirectUnitsByParentId(String parentId);

    @Query("From Unit where unionCode like ?1% and isDeleted = '0'")
    List<Unit> findByBeginWithUnionCode(String unionCode, Pagination page);
}

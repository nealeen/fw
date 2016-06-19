package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.framework.utils.Pagination;

public interface UnitService extends BaseService<Unit, String> {
    List<Unit> findAll();

    List<Unit> findAll(Pagination page);

    Unit findTopUnit();

    List<Unit> findDirectUnitsByParentId(String parentId);

    List<Unit> findAllUnitsByParentId(String parentId, Pagination page);
}

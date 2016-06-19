package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.UnitService;
import net.zdsoft.basedata.dao.BaseJapRepositoryDao;
import net.zdsoft.basedata.dao.UnitDao;
import net.zdsoft.basedata.entity.Unit;
import net.zdsoft.framework.entity.Specifications;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends BaseServiceImpl<Unit, String> implements UnitService {

    @Resource
    private UnitDao unitDao;

    @Override
    public List<Unit> findAll() {
        Specifications<Unit> s = new Specifications<Unit>().addEq("isDeleted", 0);
        return findAll(s.getSpecification());
    }

    @Override
    public List<Unit> findAll(Pagination page) {
        Pageable pageable = new PageRequest(page.getPageIndex(), page.getPageSize());
        Page<Unit> units = unitDao.findAll(pageable);
        if (units != null)
            return units.getContent();

        return new ArrayList<Unit>();
    }

    @Override
    public Unit findTopUnit() {
        String topUnitId = RedisUtils.get("unit.top.id", new RedisInterface<String>() {
            @Override
            public String queryData() {
                Unit topUnit = unitDao.findTopUnit();
                if (topUnit != null)
                    return topUnit.getId();
                else
                    return null;
            }
        });
        if (StringUtils.isNotBlank(topUnitId)) {
            return findOne(topUnitId);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Unit> findDirectUnitsByParentId(final String parentId) {
        Set<String> unitIds = RedisUtils.getSet("unit.ids.by.parent.id." + parentId,
                new RedisInterface<Set<String>>() {
                    @Override
                    public Set<String> queryData() {
                        List<Unit> units = unitDao.findDirectUnitsByParentId(parentId);
                        Set<String> ids = new HashSet<String>();
                        for (Unit u : units) {
                            ids.add(u.getId());
                        }
                        return ids;
                    }
                });

        List<Unit> units = findByIdIn(unitIds.toArray(new String[0]));
        return units;
    }
 
    /**
     * 分页，取出所有下属单位
     * 不做缓存
     */
    @Override
    public List<Unit> findAllUnitsByParentId(final String parentId, final Pagination page) {
        Unit parentUnit = unitDao.findOne(parentId);
        String unionCode = parentUnit.getUnionCode();
        return unitDao.findByBeginWithUnionCode(unionCode, page);
    }

    @Override
    protected BaseJapRepositoryDao<Unit, String> getBaseJapRepositoryDao() {
        return unitDao;
    }
}

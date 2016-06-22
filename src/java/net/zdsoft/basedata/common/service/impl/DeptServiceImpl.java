package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.DeptService;
import net.zdsoft.basedata.dao.DeptDao;
import net.zdsoft.basedata.entity.Dept;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept, String> implements DeptService {

	@Resource
	private DeptDao deptDao;

	@Override
	public List<Dept> findByUnitId(final String unitId) {
		Set<String> deptIds = RedisUtils.getObject("dept.ids.by.unit.id." + unitId, new RedisInterface<Set<String>>() {
			@Override
			public Set<String> queryData() {
				List<Dept> depts = deptDao.findByUnitId(unitId);
				Set<String> deptIds = new HashSet<String>();
				for (Dept dept : depts) {
					deptIds.add(dept.getId());
				}
				return deptIds;
			}
		});
		if (deptIds == null)
			return new ArrayList<Dept>();
		List<Dept> depts = findByIdIn(deptIds.toArray(new String[0]));
		return depts;
	}

	@Override
	public Integer countByUnitId(final String unitId) {
		return RedisUtils.getObject("dept.count.by.unit.id." + unitId, new RedisInterface<Integer>() {
			@Override
			public Integer queryData() {
				return deptDao.countByUnitId(unitId);
			}
		});
	}

	@Override
	public List<Dept> findByUnitId(final String unitId, final Pagination page) {
		Integer count = countByUnitId(unitId);
		page.setMaxRowCount(count);
		return deptDao.findByUnitId(unitId, Pagination.toPageable(page));
	}

	@Override
	protected BaseJapRepositoryDao<Dept, String> getBaseJapRepositoryDao() {
		return deptDao;
	}

	@Override
	public List<Dept> findByParentId(String parentId) {
		return deptDao.findByParentId(parentId);
	}
}

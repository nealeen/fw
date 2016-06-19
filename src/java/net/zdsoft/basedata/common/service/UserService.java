package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.utils.Pagination;

public interface UserService extends BaseService<User, String> {
	User findByUsername(String username);

	List<User> findByOwnerId(String ownerId);

	List<User> findByDeptId(String deptId);

	List<User> findByUnitId(String unitId);

	User findOne(String userId);

	List<User> findByUnitId(String unitId, Pagination page);
}

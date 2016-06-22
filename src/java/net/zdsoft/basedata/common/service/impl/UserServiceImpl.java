package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.dao.UserDao;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User findByUsername(final String username) {
		String userId = RedisUtils.getObject("user.id.by.username." + username, new RedisInterface<String>() {
			@Override
			public String queryData() {
				User user = userDao.findByUsername(username);
				if (user != null) {
					return user.getId();
				} else {
					return null;
				}
			}
		});
		if (StringUtils.isBlank(userId))
			return null;
		return findOne(userId);
	}

	@Override
	protected BaseJapRepositoryDao<User, String> getBaseJapRepositoryDao() {
		return userDao;
	}

	@Override
	public List<User> findByDeptId(final String deptId) {
		Set<String> ids = RedisUtils.getSet("user.ids.by.dept.id." + deptId, new RedisInterface<Set<String>>() {
			@Override
			public Set<String> queryData() {
				List<User> users = userDao.findByDeptId(deptId);
				Set<String> ids = new HashSet<String>();
				for (User u : users) {
					ids.add(u.getId());
				}
				return ids;
			}
		});
		List<User> users = new ArrayList<User>();
		for (String id : ids) {
			users.add(findOne(id));
		}
		return users;
	}

	@Override
	public List<User> findByUnitId(final String unitId) {
		Set<String> ids = RedisUtils.getSet("user.ids.by.unit.id." + unitId, new RedisInterface<Set<String>>() {
			@Override
			public Set<String> queryData() {
				List<User> users = userDao.findByUnitId(unitId);
				Set<String> ids = new HashSet<String>();
				for (User u : users) {
					ids.add(u.getId());
				}
				return ids;
			}
		});
		List<User> users = new ArrayList<User>();
		for (String id : ids) {
			users.add(findOne(id));
		}
		return users;
	}

	@Override
	public List<User> findByUnitId(String unitId, Pagination page) {
		return userDao.findByUnitId(unitId, Pagination.toPageable(page));
	}

	@Override
	public List<User> findByOwnerId(final String ownerId) {
		Set<String> ids = RedisUtils.getSet("user.ids.by.owner.id." + ownerId, new RedisInterface<Set<String>>() {

			@Override
			public Set<String> queryData() {
				List<User> users = userDao.findByOwnerId(ownerId);
				Set<String> set = new HashSet<String>();
				for (User u : users) {
					set.add(u.getId());
				}
				return set;
			}
		});
		return findByIdIn(ids.toArray(new String[0]));
	}
}

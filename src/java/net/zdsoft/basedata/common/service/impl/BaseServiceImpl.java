package net.zdsoft.basedata.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.zdsoft.basedata.common.service.BaseService;
import net.zdsoft.basedata.dao.BaseJdbcDao;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;
import net.zdsoft.framework.entity.BaseEntity;
import net.zdsoft.framework.utils.CacheKeySyncUtils;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;
import net.zdsoft.framework.utils.ToolUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.data.jpa.domain.Specification;

import redis.clients.jedis.Jedis;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public abstract class BaseServiceImpl<T extends BaseEntity, K extends Serializable> implements BaseService<T, K> {

	protected abstract BaseJapRepositoryDao<T, K> getBaseJapRepositoryDao();

	@Resource
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<T> findAll(Specification<T> s) {
		return getBaseJapRepositoryDao().findAll(s);
	}

	@Override
	public List<T> findAll(final Specification<T> s, final Pagination page) {
		List<T> ts = getBaseJapRepositoryDao().findAll(s, Pagination.toPageable(page)).getContent();
		Long count = getBaseJapRepositoryDao().count(s);
		page.setMaxRowCount(count.intValue());
		return ts;
	}

	@Override
	public List<T> findByIdIn(final K[] ids) {
		return findByIn("id", ids);
	}

	@Override
	/**
	 * 根据参数in，获取数据，如果是name="id"，会走缓存
	 * @param name
	 * @param params
	 * @return
	 */
	public List<T> findByIn(final String name, final K[] params) {
		if (StringUtils.equals("id", name)) {
			List<T> list = new ArrayList<T>();
			Jedis jedis = RedisUtils.getJedis();
			for (K k : params) {
				T t = findOne(jedis, k);
				if (t == null)
					continue;
				list.add(t);
			}
			RedisUtils.returnResource(jedis);
			return list;
		} else {
			Specification<T> s = new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
					if (params.length <= 1000) {
						return root.<String> get(name).in((Object[]) params);
					}
					int cyc = params.length / 1000 + (params.length % 1000 == 0 ? 0 : 1);
					List<Predicate> ps = new ArrayList<Predicate>();
					for (int i = 0; i < cyc; i++) {
						int max = (i + 1) * 1000;
						if (max > params.length)
							max = params.length;
						Predicate p = root.<String> get(name).in((Object[]) ArrayUtils.subarray(params, i * 1000, max));
						ps.add(p);
					}
					return cb.or(ps.toArray(new Predicate[0]));
				}
			};
			return getBaseJapRepositoryDao().findAll(s);
		}
	}

	@Override
	public T findOne(final K id) {
		return findOne(null, id);
	}

	public T findOne(Jedis jedis, final K id) {
		if (id == null)
			return null;
		Type type = getClass().getGenericSuperclass();
		T t = ToolUtils.getGeneric(type);
		if (t == null || StringUtils.isBlank(t.fetchCacheEntitName())) {
			return getBaseJapRepositoryDao().findOne(id);
		}
		final String s = t.fetchCacheEntitName();
		return RedisUtils.getObject(jedis, s + ".by.id." + id, new RedisInterface<T>() {
			@Override
			public T queryData() {
				return getBaseJapRepositoryDao().findOne(id);
			}
		});
	}

	@Override
	public List<T> findAllByPage(final Pagination page) {
		Type type = getClass().getGenericSuperclass();
		T t = ToolUtils.getGeneric(type);
		if (t == null || StringUtils.isBlank(t.fetchCacheEntitName())) {
			List<T> ts = getBaseJapRepositoryDao().findAll(Pagination.toPageable(page)).getContent();
			page.setMaxRowCount((int) getBaseJapRepositoryDao().count());
			return ts;
		}
		List<T> ts = getBaseJapRepositoryDao().findAll(Pagination.toPageable(page)).getContent();
		page.setMaxRowCount((int) getBaseJapRepositoryDao().count());
		return ts;
	}

	@Override
	public T findOne(Specification<T> s) {
		return getBaseJapRepositoryDao().findOne(s);
	}

	@Override
	public void delete(K id) {
		Type type = getClass().getGenericSuperclass();
		T t = ToolUtils.getGeneric(type);
		if (t != null && StringUtils.isNotBlank(t.fetchCacheEntitName())) {
			getBaseJapRepositoryDao().delete(id);
			final String s = t.fetchCacheEntitName();
			CacheKeySyncUtils.update(s);
		} else {
			getBaseJapRepositoryDao().delete(id);
		}
	}

	@Override
	public void remove(@SuppressWarnings("unchecked") K... ids) {
		Type type = getClass().getGenericSuperclass();
		T t = ToolUtils.getGeneric(type);
		if (t != null && StringUtils.isNotBlank(t.fetchCacheEntitName())) {
			for (K id : ids) {
				getBaseJapRepositoryDao().delete(id);
			}
			final String s = t.fetchCacheEntitName();
			CacheKeySyncUtils.update(s);
		} else {
			for (K k : ids)
				getBaseJapRepositoryDao().delete(k);
		}
	}

	@Override
	public T save(T t) {
		try {
			Field f = t.getClass().getDeclaredField("creationTime");
			if (f != null) {
				MethodUtils.invokeMethod(t, "setCreationTime", new Date());
			}
			f = t.getClass().getDeclaredField("modifyTime");
			if (f != null) {
				MethodUtils.invokeMethod(t, "setModifyTime", new Date());
			}
			f = t.getClass().getDeclaredField("eventSource");
			if (f != null) {
				MethodUtils.invokeMethod(t, "setEventSource", 1);
			}
		} catch (Exception e) {
		}
		T tr = getBaseJapRepositoryDao().save(t);
		Type type = getClass().getGenericSuperclass();
		T t1 = ToolUtils.getGeneric(type);
		if (t1 != null) {
			final String s = t1.fetchCacheEntitName();
			if (StringUtils.isNotBlank(s)) {
				RedisUtils.setObject(s + ".by.id." + t.getId(), tr);
				CacheKeySyncUtils.update(s);
			}
		}
		return tr;
	}

	@Override
	public List<T> saves(@SuppressWarnings("unchecked") T... ts) {
		List<T> tss = getBaseJapRepositoryDao().save(Arrays.asList(ts));
		Type type = getClass().getGenericSuperclass();
		T t1 = ToolUtils.getGeneric(type);
		if (t1 != null) {
			final String s = t1.fetchCacheEntitName();
			if (StringUtils.isNotBlank(s)) {
				Jedis jedis = RedisUtils.getJedis();
				for (T t : tss) {
					RedisUtils.setObject(jedis, s + ".by.id." + t.getId(), t);
				}
				RedisUtils.returnResource(jedis);
			}
		}
		return tss;
	}

	@Override
	public List<T> findAll() {
		List<T> tt = getBaseJapRepositoryDao().findAll();
		return tt;
	}

	@Override
	public List<String[]> findBySql(String sql, Object[] params) {
		return baseJdbcDao.findBySql(sql, params);
	}

}

package net.zdsoft.basedata.dao;

import java.util.List;

public interface BaseJdbcDao {
	public List<String[]> findBySql(String sql, Object[] params);
}

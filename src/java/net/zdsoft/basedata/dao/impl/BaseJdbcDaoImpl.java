package net.zdsoft.basedata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import net.zdsoft.basedata.dao.BaseJdbcDao;
import net.zdsoft.framework.dao.BaseDao;
import net.zdsoft.framework.utils.MultiRowMapper;

@Repository
public class BaseJdbcDaoImpl extends BaseDao<Object> implements BaseJdbcDao {

	@Override
	public List<String[]> findBySql(String sql, Object[] params) {
		return query(sql, params, new MultiRowMapper<String[]>(){
			@Override
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new String[]{rs.getObject(1).toString(), rs.getObject(2).toString()};
			}
		});
	}

	@Override
	public Object setField(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}

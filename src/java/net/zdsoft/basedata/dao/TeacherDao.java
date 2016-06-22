package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface TeacherDao extends BaseJapRepositoryDao<Teacher, String>, TeacherExtDao {

	@Query("From Teacher where isDeleted = 0 and unitId = ?1")
	List<Teacher> findByUnitId(String unitId);

	List<Teacher> findByIdIn(String[] ids);

	@Query("From Teacher where deptId = ?1 and isDeleted = 0")
	List<Teacher> findByDeptId(String deptId);

	@Query("From Teacher where deptId = ?1 and isDeleted = 0")
	List<Teacher> findByDeptId(String deptId, Pageable page);

	@Query("select count(*) from Teacher where deptId = ?1 and isDeleted = 0")
	Integer countByDeptId(String deptId);
}

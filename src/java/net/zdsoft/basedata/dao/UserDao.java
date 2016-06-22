package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.dao.BaseJapRepositoryDao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends BaseJapRepositoryDao<User, String> {

    @Query("from User where unitId = ?1 and isDeleted = 0")
    List<User> findByUnitId(String unitId);

    @Query("from User where deptId = ?1 and isDeleted = 0")
    List<User> findByDeptId(String deptId);

    @Query("Select count(*) from User where unitId = ?1 and isDeleted = 0")
    Integer countByUnitId(String unitId);

    @Query("From User where unitId = ?1 and isDeleted = 0")
    List<User> findByUnitId(String unitId, Pageable page);

    @Query("From User where ownerId = ?1 and isDeleted = 0")
    List<User> findByOwnerId(String ownerId);

    @Query("From User where username = ?1 and isDeleted = 0")
    User findByUsername(String username);
}

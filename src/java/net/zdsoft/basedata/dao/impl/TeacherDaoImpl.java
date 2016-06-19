package net.zdsoft.basedata.dao.impl;

import java.util.List;

import net.zdsoft.basedata.dao.TeacherExtDao;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.framework.dao.BaseJpaDao;

public class TeacherDaoImpl extends BaseJpaDao<Teacher> implements TeacherExtDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Teacher> findByDeptIdAndSex(final String deptId, final String sex) {
        return getEntityManager().createQuery(
                "from Teacher where deptId = ?1 and sex = ?2 and isDeleted = 0").setParameter(1,
                deptId).setParameter(2, sex).getResultList();
    }
 
}

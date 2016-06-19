package net.zdsoft.basedata.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.TeacherService;
import net.zdsoft.basedata.common.service.UserService;
import net.zdsoft.basedata.dao.BaseJapRepositoryDao;
import net.zdsoft.basedata.dao.TeacherDao;
import net.zdsoft.basedata.entity.Teacher;
import net.zdsoft.basedata.entity.User;
import net.zdsoft.framework.utils.Pagination;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, String> implements TeacherService {

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private UserService userService;

    @Override
    public List<Teacher> findByUnitId(final String unitId) {
        Set<String> ids = RedisUtils.getSet("teacher.ids.by.unit.id." + unitId,
                new RedisInterface<Set<String>>() {
                    @Override
                    public Set<String> queryData() {
                        List<Teacher> teachers = teacherDao.findByUnitId(unitId);
                        Set<String> set = new HashSet<String>();
                        for (Teacher t : teachers) {
                            set.add(t.getId());
                        }
                        return set;
                    }
                });
        List<Teacher> teachers = findByIdIn(ids.toArray(new String[0]));
        return teachers;
    }

    @Override
    public List<Teacher> findByDeptId(final String deptId) {
        Set<String> ids = RedisUtils.getSet("teacher.ids.by.dept.id." + deptId,
                new RedisInterface<Set<String>>() {
                    @Override
                    public Set<String> queryData() {
                        List<Teacher> teachers = teacherDao.findByDeptId(deptId);
                        Set<String> set = new HashSet<String>();
                        for (Teacher t : teachers) {
                            set.add(t.getId());
                        }
                        return set;
                    }
                });
        List<Teacher> teachers = findByIdIn(ids.toArray(new String[0]));
        return teachers;
    }

    @Override
    public List<Teacher> findByDeptId(final String deptId, final Pagination page) {
        Integer count = countByDeptId(deptId);
        page.setMaxRowCount(count);
        return teacherDao.findByDeptId(deptId, Pagination.toPageable(page));
    }

    private Integer countByDeptId(String deptId) {
        final String deptIdf = deptId;
        return RedisUtils.getObject("teacher.count.by.dept.id." + deptId,
                new RedisInterface<Integer>() {
                    @Override
                    public Integer queryData() {
                        Integer count = teacherDao.countByDeptId(deptIdf);
                        return count == null ? 0 : count;
                    }
                });
    }

    @Override
    protected BaseJapRepositoryDao<Teacher, String> getBaseJapRepositoryDao() {
        return teacherDao;
    }

    @Override
    public Teacher findByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        String ownerId = user.getOwnerId();
        return findOne(ownerId);
    }

    @Override
    public Teacher findByUserId(String userId) {
        User user = userService.findOne(userId);
        if (user == null) {
            return null;
        }
        String ownerId = user.getOwnerId();
        return findOne(ownerId);
    }
}

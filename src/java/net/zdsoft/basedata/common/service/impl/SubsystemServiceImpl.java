package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.SubsystemService;
import net.zdsoft.basedata.dao.SubSystemDao;
import net.zdsoft.basedata.entity.SubSystem;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.springframework.stereotype.Service;

@Service
public class SubsystemServiceImpl implements SubsystemService {

    @Resource
    private SubSystemDao subSystemDao;

    @Override
    public List<SubSystem> findAll() {
        Set<Integer> ids = RedisUtils.getObject("subsystem.ids",
                new RedisInterface<Set<Integer>>() {
                    @Override
                    public Set<Integer> queryData() {
                        List<SubSystem> systems = subSystemDao.findAll();
                        Set<Integer> set = new HashSet<Integer>();
                        for (SubSystem system : systems) {
                            set.add(system.getId());
                        }
                        return set;
                    }
                });
        List<SubSystem> list = new ArrayList<SubSystem>();
        for (Integer id : ids) {
            SubSystem system = findOne(id);
            if (system != null)
                list.add(findOne(id));
        }
        return list;
    }

    public SubSystem findOne(Integer id) {
        final Integer idf = id;
        return RedisUtils.getObject("subsystem.by.id." + idf, new RedisInterface<SubSystem>() {
            @Override
            public SubSystem queryData() {
                SubSystem system = subSystemDao.findOne(idf);
                return system;
            }
        });
    }

}

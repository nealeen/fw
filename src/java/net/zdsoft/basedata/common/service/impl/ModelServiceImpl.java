package net.zdsoft.basedata.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.ModelService;
import net.zdsoft.basedata.dao.ModelDao;
import net.zdsoft.basedata.entity.Model;
import net.zdsoft.framework.utils.RedisInterface;
import net.zdsoft.framework.utils.RedisUtils;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelDao modelDao;

    @Override
    public List<Model> findBySubSystemId(Integer subSystem) {
        return modelDao.findBySubSystem(subSystem);
    }

    @Override
    public List<Model> findAll() {
        return modelDao.findAll();
    }

    @Override
    public List<Model> findByUnitClass(final Integer unitClass) {
        List<String> ids = RedisUtils.getList("model.ids.by.unit.class." + unitClass,
                new RedisInterface<List<String>>() {
                    @Override
                    public List<String> queryData() {
                        List<Model> models = modelDao.findByUnitClass(unitClass);
                        List<String> ids = new ArrayList<String>();
                        for (Model m : models) {
                            ids.add(m.getId() + "");
                        }
                        return ids;
                    }
                });
        List<Model> models = new ArrayList<Model>();
        for (String id : ids) {
            models.add(findOne(NumberUtils.toInt(id)));
        }
        return models;
    }

    @Override
    public List<Model> findByUserId(String userId) {
        // XXX 临时测试用，要删除的
        return findByUnitClass(2);
    }

    @Override
    public Model findOne(final Integer id) {
        return RedisUtils.getObject("model.by.id." + id, new RedisInterface<Model>() {
            @Override
            public Model queryData() {
                return modelDao.findOne(id);
            }
        });
    }
}

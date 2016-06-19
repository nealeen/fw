package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.Model;

public interface ModelService {
    List<Model> findBySubSystemId(Integer subSystem);

    List<Model> findAll();

    Model findOne(Integer id);

    List<Model> findByUnitClass(Integer unitClass);

    /**
     * 获取用户有权限的模块列表
     * 
     * @param userId
     * @return
     */
    List<Model> findByUserId(String userId);
}

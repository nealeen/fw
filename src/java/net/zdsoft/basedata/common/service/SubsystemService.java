package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.SubSystem;

public interface SubsystemService {
    public List<SubSystem> findAll();

    public SubSystem findOne(Integer id);
}

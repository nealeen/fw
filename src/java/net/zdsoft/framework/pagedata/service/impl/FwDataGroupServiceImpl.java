package net.zdsoft.framework.pagedata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.zdsoft.framework.pagedata.dao.FwDataGroupJpaDao;
import net.zdsoft.framework.pagedata.entity.FwDataGroup;
import net.zdsoft.framework.pagedata.service.FwDataGroupService;

@Service
public class FwDataGroupServiceImpl implements FwDataGroupService {

    @Resource
    private FwDataGroupJpaDao fwDataGroupJpaDao;

    @Override
    public FwDataGroup findByCode(String code) {
        return fwDataGroupJpaDao.findByCode(code);
    }

    @Override
    public List<FwDataGroup> findAll() {
        return fwDataGroupJpaDao.findAll();
    }

    @Override
    public FwDataGroup findOne(String id) {
        return fwDataGroupJpaDao.findOne(id);
    }

    @Override
    public List<FwDataGroup> save(List<FwDataGroup> groups) {
        return fwDataGroupJpaDao.save(groups);

    }

    @Override
    public FwDataGroup save(FwDataGroup group) {
        return fwDataGroupJpaDao.save(group);
    }

}

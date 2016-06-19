package net.zdsoft.basedata.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.zdsoft.basedata.common.service.RegionService;
import net.zdsoft.basedata.dao.BaseJapRepositoryDao;
import net.zdsoft.basedata.dao.RegionDao;
import net.zdsoft.basedata.entity.Region;

import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl extends BaseServiceImpl<Region, String> implements RegionService {

	@Resource
	private RegionDao regionDao;

	@Override
	public List<Region> findByType(String type) {
		return regionDao.findByType(type);
	}

	@Override
	protected BaseJapRepositoryDao<Region, String> getBaseJapRepositoryDao() {
		return regionDao;
	}

	@Override
	public List<Region> findTop20() {
		return regionDao.findTop20By();
	}
}

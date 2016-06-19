package net.zdsoft.basedata.common.service;

import java.util.List;

import net.zdsoft.basedata.entity.Region;

public interface RegionService extends BaseService<Region, String> {
	public List<Region> findByType(String type);

	public List<Region> findTop20();
}

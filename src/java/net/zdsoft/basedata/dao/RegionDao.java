package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Region;

public interface RegionDao extends BaseJapRepositoryDao<Region, String> {

	public List<Region> findByType(String type);

	public List<Region> findTop20By();
}

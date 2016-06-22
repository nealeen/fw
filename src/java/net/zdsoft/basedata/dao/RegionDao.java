package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Region;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionDao extends JpaRepository<Region, String> {

	public List<Region> findByType(String type);

	public List<Region> findTop20By();
}

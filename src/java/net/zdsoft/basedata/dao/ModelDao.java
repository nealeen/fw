package net.zdsoft.basedata.dao;

import java.util.List;

import net.zdsoft.basedata.entity.Model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelDao extends JpaRepository<Model, Integer> {
    List<Model> findBySubSystem(Integer subSystem);
    
    List<Model> findByUnitClass(Integer unitClass);
}

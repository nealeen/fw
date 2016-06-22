package net.zdsoft.framework.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract interface BaseJapRepositoryDao<T, K extends Serializable> extends
        JpaRepository<T, K>, JpaSpecificationExecutor<T> {
	
}

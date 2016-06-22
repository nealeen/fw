package net.zdsoft.basedata.common.service;

import java.io.Serializable;
import java.util.List;

import net.zdsoft.framework.utils.Pagination;

import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T, K extends Serializable> {

    public List<T> findAll(Specification<T> s);
    
    public List<T> findAll();
    
    public T findOne(Specification<T> s);

    public List<T> findByIdIn(K[] ids);

    public List<T> findByIn(String name, K[] params);

    public List<T> findAll(Specification<T> s, Pagination page);

    public T findOne(K id);

    public List<T> findAllByPage(Pagination page);

    public void delete(K id);

    public void remove(@SuppressWarnings("unchecked") K... ids);
    
    public List<String[]> findBySql(String sql, Object[] params);

    public T save(T t);

    public List<T> saves(@SuppressWarnings("unchecked") T... ts);

}

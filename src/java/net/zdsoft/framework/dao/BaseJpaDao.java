package net.zdsoft.framework.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * @author linqz
 * @param <T>
 */
public class BaseJpaDao<T> {

    @Resource
    private EntityManagerFactory entityManagerFactory;

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    @SuppressWarnings("unchecked")
    protected T query(String psql, Object... objs) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery(psql);
        for(int i = 0; i < objs.length; i ++) {
            query.setParameter(i + 1, objs[i]);
        }
        List<T> list = query.getResultList();
        if(list.size() > 0) {
            return list.get(0);
        }
        else {
            return null;
        }
    }
}

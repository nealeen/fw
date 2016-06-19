package net.zdsoft.framework.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Specifications<T> {

    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();
    private List<Class<?>> classes = new ArrayList<Class<?>>();

    public Specifications<T> addEq(String name, Object value, Class<?> clazz) {
        names.add(name);
        values.add(value);
        classes.add(clazz);
        return this;
    }

    public Specifications<T> addEq(String name, Object value) {
        return addEq(name, value, value == null ? String.class : value.getClass());
    }
    
    public Specification<T> getSpecification(boolean autoFillIsDeleted) {
        if (autoFillIsDeleted) {
            names.add("isDeleted");
            values.add(0);
            classes.add(Integer.class);
        }
        Specification<T> s = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                for (int index = 0; index < names.size(); index++) {
                    String name = names.get(index);
                    Predicate p = cb.equal(root.get(name).as(classes.get(index)), values.get(index));
                    predicates.add(p);
                }
                return cq.where(cb.and(predicates.toArray(new Predicate[0]))).getRestriction();
            }
        };
        return s;
    }

    public Specification<T> getSpecification() {
        return getSpecification(true);
    }

}

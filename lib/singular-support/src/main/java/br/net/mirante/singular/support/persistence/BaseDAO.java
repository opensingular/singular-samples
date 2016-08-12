package br.net.mirante.singular.support.persistence;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.net.mirante.singular.commons.util.Loggable;
import br.net.mirante.singular.support.persistence.entity.BaseEntity;


public class BaseDAO<T extends BaseEntity, ID extends Serializable> extends SimpleDAO {

    protected Class<T> tipo;

    public BaseDAO(Class<T> tipo) {
        this.tipo = tipo;
    }

    public ID save(T novoObj) {
        return (ID) getSession().save(novoObj);
    }

    public void saveOrUpdate(T novoObj) {
        getSession().saveOrUpdate(novoObj);
    }

    public T get(ID id) {
        if (id == null) {
            return null;
        } else {
            return (T) getSession().get(tipo, id);
        }
    }

    public T find(Long id) {
        if (id == null) {
            return null;
        } else {
            return (T) getSession().createCriteria(tipo).add(Restrictions.idEq(id)).uniqueResult();
        }
    }

    public List<T> listAll() {
        return getSession().createCriteria(tipo).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public T merge(T novoObj) {
        return (T) getSession().merge(novoObj);
    }

    public void delete(T obj) {
        getSession().delete(obj);
    }

    public void evict(Object o) {
        getSession().evict(o);
    }

    public <T> List<T> findByProperty(String propertyName, String value) {
        return findByProperty(propertyName, value, null, null);
    }

    public <T> List<T> findByProperty(String propertyName, String value, Integer maxResults) {
        return findByProperty(propertyName, value, null, maxResults);
    }

    public <T> T findByUniqueProperty(String propertyName, Object value) {
        return (T) getSession().createCriteria(tipo).add(Restrictions.eq(propertyName, value)).setMaxResults(1).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByProperty(String propertyName, String value, MatchMode matchMode, Integer maxResults) {
        Criteria criteria = getSession().createCriteria(tipo);

        if (matchMode == null) {
            matchMode = MatchMode.EXACT;
        }

        if (value != null && !value.isEmpty()) {
            criteria.add(Restrictions.ilike(propertyName, value, matchMode));
        }

        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }

        return criteria.list();
    }

}

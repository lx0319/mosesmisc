/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.hibernate;

import persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Mao Shijie
 */
public class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;
	@Autowired
	private HibernateTemplate template;

	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(ID id, boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getTemplate().load(getPersistentClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getTemplate().load(getPersistentClass(), id);
		}
		return entity;
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	public List<T> findByExample(T exampleInstance,
			String... excludeProperty) {
		DetachedCriteria crit = DetachedCriteria.forClass(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return getTemplate().findByCriteria(crit);
	}

	public T makePersistent(T entity) {
		getTemplate().saveOrUpdate(entity);
		return entity;
	}

	public void makeTransient(T entity) {
		getTemplate().delete(entity);
	}

	public void flush() {
		getTemplate().flush();
	}

	public void clear() {
		getTemplate().clear();
	}

	protected List<T> findByCriteria(Criterion... criterion) {
		DetachedCriteria crit =
				DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return getTemplate().findByCriteria(crit);
	}

	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
}

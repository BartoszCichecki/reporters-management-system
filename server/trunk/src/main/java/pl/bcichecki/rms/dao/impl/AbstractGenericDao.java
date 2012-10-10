/**
 * Project:   rms-server
 * File:      AbstractGenericDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      17-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.GenericDao;
import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractGenericDao<T extends AbstractEntity> implements GenericDao<T> {

	@PersistenceContext
	protected transient EntityManager manager;

	private transient final Class<T> entityClazz;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		entityClazz = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public boolean contains(T entity) {
		return manager.contains(entity);
	}

	@Override
	public void create(T entity) {
		manager.persist(entity);
	}

	@Override
	public void delete(T entity) {
		manager.remove(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> getAll() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClazz);
		Root<T> root = criteria.from(entityClazz);
		criteria.select(root);
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> getAllByCriteria(CriteriaQuery<T> criteria) {
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public T getByCriteria(CriteriaQuery<T> criteria) {
		List<T> result = getAllByCriteria(criteria);
		if (result.size() > 1) {
			throw new IllegalStateException();
		}
		if (result.size() != 1) {
			return null;
		}
		return result.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public T getById(Serializable id) {
		return manager.find(entityClazz, id);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return manager.getCriteriaBuilder();
	}

	@Override
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void update(T entity) {
		manager.merge(entity);
	}

}

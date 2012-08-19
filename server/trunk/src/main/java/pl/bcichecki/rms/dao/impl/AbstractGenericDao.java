/**
 * Project:   Reporters Management System - Server
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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
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
		entityClazz = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Override
	public void create(T entity) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		manager.persist(entity);
	}

	@Override
	public void delete(T entity) throws IllegalArgumentException, TransactionRequiredException {
		manager.remove(entity);
	}

	@Override
	public List<T> getAll() throws IllegalStateException, IllegalArgumentException, QueryTimeoutException,
			TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClazz);
		Root<T> root = criteria.from(entityClazz);
		criteria.select(root);
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> getAllByCriteria(CriteriaQuery<T> criteria) throws IllegalStateException, QueryTimeoutException,
			TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public T getByCriteria(CriteriaQuery<T> criteria) throws NoResultException, NonUniqueResultException,
			IllegalStateException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException,
			LockTimeoutException, PersistenceException {
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public T getById(Serializable id) throws IllegalArgumentException {
		return manager.find(entityClazz, id);
	}

	@Override
	public CriteriaBuilder getQueryBuilder() throws IllegalStateException {
		return manager.getCriteriaBuilder();
	}

	@Override
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void update(T entity) throws IllegalArgumentException, TransactionRequiredException {
		manager.merge(entity);
	}

}

/**
 * Project:   Reporters Management System - Server
 * File:      GenericDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
public interface GenericDao<T extends AbstractEntity> {

	public void create(T entity) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException;

	public void delete(T entity) throws IllegalArgumentException, TransactionRequiredException, PersistenceException;

	public List<T> getAll() throws IllegalStateException, IllegalArgumentException, QueryTimeoutException,
			TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;

	public List<T> getAllByCriteria(CriteriaQuery<T> criteria) throws IllegalStateException, QueryTimeoutException,
			TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;

	public T getByCriteria(CriteriaQuery<T> criteria) throws NoResultException, NonUniqueResultException,
			IllegalStateException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException,
			LockTimeoutException, PersistenceException;

	public T getById(Serializable id) throws IllegalArgumentException;

	public CriteriaBuilder getCriteriaBuilder() throws IllegalStateException;

	public void setEntityManager(EntityManager manager);

	public void update(T entity) throws IllegalArgumentException, TransactionRequiredException;

}

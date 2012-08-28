/**
 * Project:   Reporters Management System - Server
 * File:      UsersDaoImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.model.impl.UserEntity_;

/**
 * @author Bartosz Cichecki
 */
public class UsersDaoImpl extends AbstractGenericDao<UserEntity> implements UsersDao {

	@Override
	public UserEntity getByUsername(String username) throws NoResultException, NonUniqueResultException,
			IllegalStateException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException,
			LockTimeoutException, PersistenceException {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(UserEntity_.username), username);
		criteriaQuery.where(predicate);
		return getByCriteria(criteriaQuery);
	}

}

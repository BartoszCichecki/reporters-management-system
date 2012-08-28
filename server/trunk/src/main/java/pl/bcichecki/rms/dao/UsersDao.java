/**
 * Project:   Reporters Management System - Server
 * File:      UsersDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public interface UsersDao extends GenericDao<UserEntity> {

	public UserEntity getByUsername(String username) throws NoResultException, NonUniqueResultException,
			IllegalStateException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException,
			LockTimeoutException, PersistenceException;

}

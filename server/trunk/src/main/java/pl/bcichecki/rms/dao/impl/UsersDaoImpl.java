/**
 * Project:   rms-server
 * File:      UsersDaoImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import java.util.List;

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
	public List<UserEntity> getAllUndeleted() {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(UserEntity_.deleted), false);
		criteriaQuery.where(predicate);
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public UserEntity getByEmail(String email) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(UserEntity_.email), email);
		criteriaQuery.where(predicate);
		return getByCriteria(criteriaQuery);
	}

	@Override
	public UserEntity getByUsername(String username) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(UserEntity_.username), username);
		criteriaQuery.where(predicate);
		return getByCriteria(criteriaQuery);
	}

	@Override
	public List<UserEntity> getUsersWithRole(Long roleId) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get(UserEntity_.role), roleId));
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public boolean hasUsersWithRole(Long roleId) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(UserEntity_.role), roleId);
		criteriaQuery.where(predicate);
		List<UserEntity> result = getAllByCriteria(criteriaQuery);
		return result != null && result.size() > 0;
	}

}

/**
 * Project:   rms-server
 * File:      RolesDaoImpl.java
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
import javax.persistence.criteria.Subquery;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.model.AbstractEntity_;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.RoleEntity_;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.model.impl.UserEntity_;

/**
 * @author Bartosz Cichecki
 */
public class RolesDaoImpl extends AbstractGenericDao<RoleEntity> implements RolesDao {

	@Override
	public RoleEntity getByName(String name) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
		Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(RoleEntity_.name), name);
		criteriaQuery.where(predicate);
		return getByCriteria(criteriaQuery);
	}

	@Override
	public List<RoleEntity> getByUserId(Long id) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
		Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);

		Subquery<RoleEntity> subquery = criteriaQuery.subquery(RoleEntity.class);
		Root<UserEntity> subqueryRoot = subquery.from(UserEntity.class);
		subquery.select(subqueryRoot.get(UserEntity_.role));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(AbstractEntity_.id), id));

		criteriaQuery.select(root).where(criteriaBuilder.in(root).value(subquery));

		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public List<RoleEntity> getByUsername(String username) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
		Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);

		Subquery<RoleEntity> subquery = criteriaQuery.subquery(RoleEntity.class);
		Root<UserEntity> subqueryRoot = subquery.from(UserEntity.class);
		subquery.select(subqueryRoot.get(UserEntity_.role));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(UserEntity_.username), username));

		criteriaQuery.select(root).where(criteriaBuilder.in(root).value(subquery));

		return getAllByCriteria(criteriaQuery);
	}
}

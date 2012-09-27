/**
 * Project: Reporters Management System - Server
 * File:    RolesDaoImplTest.java
 *
 * Author:  Bartosz Cichecki
 * Date:    19-08-2012
 */
package pl.bcichecki.rms.dao.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.RoleEntity_;

/**
 * @author Bartosz Cichecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RolesDaoImplTest {

	@Autowired
	private RolesDao rolesDao;

	@Test
	public void testRolesDao() {
		int initialSize = rolesDao.getAll(false).size();

		RoleEntity role = new RoleEntity();
		String testRoleName = "testRole" + System.currentTimeMillis();
		role.setName(testRoleName);
		Set<PrivilegeType> privileges = new HashSet<>();
		privileges.add(PrivilegeType.VIEW_PROFILE);
		role.setPrivileges(privileges);
		rolesDao.create(role);

		int sizeAfterInsertion = rolesDao.getAll(false).size();
		Assert.assertEquals(initialSize + 1, sizeAfterInsertion);

		CriteriaBuilder queryBuilder = rolesDao.getCriteriaBuilder();
		CriteriaQuery<RoleEntity> criteriaQuery = queryBuilder.createQuery(RoleEntity.class);
		Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
		Predicate predicate = queryBuilder.equal(root.get(RoleEntity_.name), testRoleName);
		criteriaQuery.where(predicate);

		RoleEntity retrievedRole = rolesDao.getByCriteria(criteriaQuery);
		Assert.assertEquals(retrievedRole.getName(), testRoleName);
		Assert.assertEquals(retrievedRole.getPrivileges().size(), 1);

		rolesDao.delete(retrievedRole);

		int sizeAfterRemoval = rolesDao.getAll(false).size();
		Assert.assertEquals(initialSize, sizeAfterRemoval);
	}
}

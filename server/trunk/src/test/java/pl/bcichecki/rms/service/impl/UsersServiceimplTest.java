/**
 * Project:   rms-server
 * File:      UsersServiceimplTest.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      02-10-2012
 */

package pl.bcichecki.rms.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.AddressDataContactEntity;
import pl.bcichecki.rms.model.impl.AddressDataEntity;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.RolesService;
import pl.bcichecki.rms.services.UsersService;

/**
 * @author Bartosz Cichecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UsersServiceimplTest {

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolesService rolesService;

	@Test
	public void testCreateUserWithNewRole() throws ServiceException {
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		privileges.add(PrivilegeType.CLEAN_UP_MESSAGES);
		privileges.add(PrivilegeType.CREATE_DEVICES);

		RoleEntity role = new RoleEntity("testRole", privileges);

		List<AddressDataContactEntity> list = new ArrayList<AddressDataContactEntity>();

		AddressDataEntity address = new AddressDataEntity();
		address.setContacts(list);

		UserEntity user = new UserEntity();
		user.setId("22");
		user.setUsername("asdalkgjfkljsbnlmvx");
		user.setPassword("123");
		user.setEmail("aa@aa.pl");
		user.setDeleted(false);
		user.setLocked(false);
		user.setRole(role);
		user.setAddress(address);

		usersService.createUser(user);
	}
}

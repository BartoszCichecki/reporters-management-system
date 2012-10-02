/**
 * Project:   rms-server
 * File:      GsonTest.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      25-09-2012
 */

package pl.bcichecki.rms.ws.rest;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;

/**
 * @author Bartosz Cichecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class GsonTest {

	private static final Logger log = LoggerFactory.getLogger(GsonTest.class);

	@Test
	public void gsonTest() {
		Gson gson = new Gson();

		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		privileges.add(PrivilegeType.VIEW_PROFILE);
		privileges.add(PrivilegeType.VIEW_USERS);
		RoleEntity roleEntity = new RoleEntity("testRole" + System.currentTimeMillis(), privileges);

		String json = gson.toJson(roleEntity);

		log.info("Json: " + json);

		RoleEntity roleEntity2 = gson.fromJson(json, RoleEntity.class);

		Assert.assertEquals(roleEntity, roleEntity2);
	}

}

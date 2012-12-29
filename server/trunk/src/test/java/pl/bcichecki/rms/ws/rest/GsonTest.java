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

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.utils.SecurityUtils;
import pl.bcichecki.rms.ws.rest.json.gson.exclusionStrategies.PasswordExclusionStrategy;

/**
 * @author Bartosz Cichecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-beans.xml", "file:src/main/webapp/WEB-INF/jpa-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class GsonTest {

	private static final Logger log = LoggerFactory.getLogger(GsonTest.class);

	@Test
	public void gsonTest1() {
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

	@Test
	public void gsonTest2() throws UnsupportedEncodingException {
		Gson gson = new Gson();

		UserEntity user = new UserEntity();
		user.setUsername("123");
		user.setPassword(SecurityUtils.hashSHA512Base64("123"));

		String json = gson.toJson(user);

		UserEntity fromJson = gson.fromJson(json, UserEntity.class);

		Assert.assertEquals(user.getPassword(), fromJson.getPassword());
	}

	@Test
	public void gsonTest3() {

		String json = "{\"address\":{},\"username\":\"Sejm\",\"email\":\"dj.cym@uj.chin\",\"password\":\"RRijsHJ2V0SxkDr5IZE0Ekwafq+2VzzLgI4KI89cbdKHvEHa9Cw2SgS2h8Zzj8oSshwzU4LHgHNBFDQfLjnPZg\u003d\u003d\",\"locked\":false,\"deleted\":false}";

		Gson gson = new Gson();

		UserEntity fromJson = gson.fromJson(json, UserEntity.class);

		System.out.println(fromJson.getPassword());

		Assert.assertEquals("RRijsHJ2V0SxkDr5IZE0Ekwafq+2VzzLgI4KI89cbdKHvEHa9Cw2SgS2h8Zzj8oSshwzU4LHgHNBFDQfLjnPZg==",
		        fromJson.getPassword());
	}

	@Test
	public void gsonTest4() {

		String json = "{\"address\":{},\"username\":\"Sejm\",\"email\":\"dj.cym@uj.chin\",\"password\":\"RRijsHJ2V0SxkDr5IZE0Ekwafq+2VzzLgI4KI89cbdKHvEHa9Cw2SgS2h8Zzj8oSshwzU4LHgHNBFDQfLjnPZg\u003d\u003d\",\"locked\":false,\"deleted\":false}";

		GsonBuilder gsonBuilder = new GsonBuilder();
		try {
			ExclusionStrategy passwordExclusionStrategy = new PasswordExclusionStrategy(UserEntity.class,
			        UserEntity.class.getDeclaredField("password"));
			gsonBuilder.setExclusionStrategies(passwordExclusionStrategy);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
		Gson gson = gsonBuilder.create();

		UserEntity fromJson = gson.fromJson(json, UserEntity.class);

		Assert.assertEquals("RRijsHJ2V0SxkDr5IZE0Ekwafq+2VzzLgI4KI89cbdKHvEHa9Cw2SgS2h8Zzj8oSshwzU4LHgHNBFDQfLjnPZg==",
		        fromJson.getPassword());
	}

}

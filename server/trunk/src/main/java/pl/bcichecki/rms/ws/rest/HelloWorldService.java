/**
 * Project:   Reporters Management System - Server
 * File:      HelloWorldService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      09-08-2012
 */

package pl.bcichecki.rms.ws.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.Role;
import pl.bcichecki.rms.model.impl.Role_;

/**
 * @author Bartosz Cichecki
 */
@Controller
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class HelloWorldService {

	private static Logger log = LoggerFactory.getLogger(HelloWorldService.class);

	@Autowired
	private RolesDao rolesDao;

	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public @ResponseBody
	void addRole() {
		Role role = new Role();
		String testRoleName = "testRole2";
		role.setName(testRoleName);
		Set<PrivilegeType> privileges = new HashSet<>();
		privileges.add(PrivilegeType.ADD_USER);
		role.setPrivileges(privileges);
		rolesDao.create(role);
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	String handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return ex.getMessage();
	}

	@RequestMapping(value = "/remRole", method = RequestMethod.GET)
	public @ResponseBody
	void remRole() {
		CriteriaBuilder queryBuilder = rolesDao.getQueryBuilder();
		CriteriaQuery<Role> criteriaQuery = queryBuilder.createQuery(Role.class);
		Root<Role> root = criteriaQuery.from(Role.class);
		String testRoleName = "testRole2";
		Predicate predicate = queryBuilder.equal(root.get(Role_.name), testRoleName);
		criteriaQuery.where(predicate);

		Role retrievedRole = rolesDao.getByCriteria(criteriaQuery);

		rolesDao.delete(retrievedRole);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody
	String sayHello() {
		log.debug("hello");
		log.info("hello");
		return "hello";
	}

	@RequestMapping(value = "/err", method = RequestMethod.GET)
	public @ResponseBody
	String sendError() throws Exception {
		throw new Exception("hurray!");
	}
}

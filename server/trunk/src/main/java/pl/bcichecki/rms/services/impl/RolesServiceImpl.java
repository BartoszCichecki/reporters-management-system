/**
 * Project:   rms-server
 * File:      RolesServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.services.RolesService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesDao rolesDao;

	@Autowired
	private UsersDao usersDao;

	@Override
	public boolean createRole(RoleEntity role) throws ServiceException {
		if (rolesDao.getByName(role.getName()) != null) {
			throw new ServiceException("Role with such name already exist! Role must have unique names.",
			        "exceptions.serviceExceptions.roles.duplicateName");
		}
		rolesDao.create(role);
		return true;
	}

	@Override
	public boolean deleteRole(String id) throws ServiceException {
		RoleEntity role = rolesDao.getById(id);
		if (role == null) {
			throw new ServiceException("You can't delete role that does not exist!",
			        "exceptions.serviceExceptions.roles.cantDeleteNotExisting");
		}
		if (usersDao.hasUsersWithRole(id)) {
			throw new ServiceException("Can't delete role that is assigned to user(s)!",
			        "exceptions.serviceExceptions.roles.cantDeleteStillAssigned");
		}
		rolesDao.delete(role);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEntity> getAllRoles() {
		return rolesDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public RoleEntity getRoleById(String id) throws ServiceException {
		RoleEntity role = rolesDao.getById(id);
		if (role == null) {
			throw new ServiceException("Role with this name does not exist!", "exceptions.serviceExceptions.roles.notExistName");
		}
		return role;
	}

	@Override
	@Transactional(readOnly = true)
	public RoleEntity getRoleByName(String name) throws ServiceException {
		RoleEntity role = rolesDao.getByName(name);
		if (role == null) {
			throw new ServiceException("Role with this name does not exist!", "exceptions.serviceExceptions.roles.notExistId");
		}
		return role;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEntity> getUsersRoles(String id) throws ServiceException {
		if (usersDao.getById(id) == null) {
			throw new ServiceException("User with this ID does not exist!", "exceptions.serviceExceptions.users.notExistId");
		}
		return rolesDao.getByUserId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEntity> getUsersRolesByUsername(String username) throws ServiceException {
		if (usersDao.getByUsername(username) == null) {
			throw new ServiceException("User with this name does not exist!", "exceptions.serviceExceptions.users.notExistName");
		}
		return rolesDao.getByUsername(username);
	}

	@Override
	public boolean updateRole(RoleEntity role) throws ServiceException {
		if (rolesDao.getByName(role.getName()) != null) {
			throw new ServiceException("Role with such name already exist! Roles must have unique names.",
			        "exceptions.serviceExceptions.roles.duplicateName");
		}
		RoleEntity retrieved = rolesDao.getById(role.getId());
		if (retrieved == null) {
			throw new ServiceException("You can't update role that does not exist!",
			        "exceptions.serviceExceptions.roles.cantUpdateNotExisting");
		}
		retrieved.merge(role);
		rolesDao.update(retrieved);
		return true;
	}
}

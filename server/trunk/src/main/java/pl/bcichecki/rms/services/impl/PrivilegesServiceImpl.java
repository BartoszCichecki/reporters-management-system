/**
 * Project:   rms-server
 * File:      PrivilegesServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.services.PrivilegesService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PrivilegesServiceImpl implements PrivilegesService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private RolesDao rolesDao;

	private Set<PrivilegeType> extractPrivilegesFromRoles(List<RoleEntity> roles) {
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		for (RoleEntity roleEntity : roles) {
			privileges.addAll(roleEntity.getPrivileges());
		}
		return privileges;
	}

	@Override
	public Set<PrivilegeType> getAllPrivileges() {
		return new HashSet<PrivilegeType>(Arrays.asList(PrivilegeType.values()));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<PrivilegeType> getAuthenticatedUsersPrivileges() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return SetUtils.EMPTY_SET;
		}
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		for (GrantedAuthority g : authentication.getAuthorities()) {
			privileges.add(PrivilegeType.fromString(g.getAuthority()));
		}
		return privileges;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<PrivilegeType> getUsersPrivileges(String userId) throws ServiceException {
		if (usersDao.getById(userId) == null) {
			throw new ServiceException("User with this ID does not exist!", "exceptions.serviceExceptions.users.notExistId");

		}
		List<RoleEntity> roles = rolesDao.getByUserId(userId);
		return extractPrivilegesFromRoles(roles);
	}

}

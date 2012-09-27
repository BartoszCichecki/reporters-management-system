/**
 * Project:   Reporters Management System - Server
 * File:      EmergencyAdminServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      20-08-2012
 */

package pl.bcichecki.rms.services.special;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.EmergencyAdminService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
public class EmergencyAdminServiceImpl implements EmergencyAdminService, InitializingBean {

	private UserEntity EmergencyAdmin;

	public EmergencyAdminServiceImpl() {
	}

	@Override
	public void afterPropertiesSet() {
		EmergencyAdmin.setRole(new RoleEntity("Master Admin", getEmergencyAdminPrivilages()));
		EmergencyAdmin
				.setPassword(SecurityUtils.hashSHA512(EmergencyAdmin.getPassword(), EmergencyAdmin.getUsername()));
	}

	@Override
	public UserEntity getEmergencyAdmin() {
		return EmergencyAdmin;
	}

	private Set<PrivilegeType> getEmergencyAdminPrivilages() {
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		// FIXME Replace after managing users is finished!!!
		// Master admin has been changed to emergency admin who can now only
		// manage users.
		// privileges.add(PrivilegeType.GET_USERS);
		// privileges.add(PrivilegeType.MANAGE_USERS);
		privileges.addAll(PrivilegeUtils.getAllPrivileges());
		return privileges;
	}

	@Override
	public boolean isEmergencyAdmin(String username) {
		return EmergencyAdmin.getUsername().equalsIgnoreCase(StringUtils.defaultString(username));
	}

	public void setEmergencyAdmin(UserEntity EmergencyAdmin) {
		this.EmergencyAdmin = EmergencyAdmin;
	}

}

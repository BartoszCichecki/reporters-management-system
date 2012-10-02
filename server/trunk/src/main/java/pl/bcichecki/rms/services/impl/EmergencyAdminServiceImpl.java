/**
 * Project:   rms-server
 * File:      EmergencyAdminServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      20-08-2012
 */

package pl.bcichecki.rms.services.impl;

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

	private UserEntity emergencyAdmin;

	private boolean grantAllPrivileges;

	public EmergencyAdminServiceImpl() {
	}

	@Override
	public void afterPropertiesSet() {
		emergencyAdmin.setRole(new RoleEntity("Master Admin", getEmergencyAdminPrivilages()));
		emergencyAdmin.setPassword(SecurityUtils.hashSHA512(emergencyAdmin.getPassword(), emergencyAdmin.getUsername()));
	}

	@Override
	public UserEntity getEmergencyAdmin() {
		return emergencyAdmin;
	}

	private Set<PrivilegeType> getEmergencyAdminPrivilages() {
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		if (grantAllPrivileges) {
			privileges.addAll(PrivilegeUtils.getAllPrivileges());
		} else {
			privileges.add(PrivilegeType.VIEW_USERS);
			privileges.add(PrivilegeType.MANAGE_USERS);
		}
		return privileges;
	}

	@Override
	public boolean isEmergencyAdmin(String username) {
		return emergencyAdmin.getUsername().equalsIgnoreCase(StringUtils.defaultString(username));
	}

	public void setEmergencyAdmin(UserEntity EmergencyAdmin) {
		emergencyAdmin = EmergencyAdmin;
	}

	public void setGrantAllPrivileges(boolean grantAllPrivileges) {
		this.grantAllPrivileges = grantAllPrivileges;
	}

}

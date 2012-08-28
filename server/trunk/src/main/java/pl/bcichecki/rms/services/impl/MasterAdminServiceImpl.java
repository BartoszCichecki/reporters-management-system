/**
 * Project:   Reporters Management System - Server
 * File:      MasterAdminServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      20-08-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.MasterAdminService;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
public class MasterAdminServiceImpl implements MasterAdminService, InitializingBean {

	private UserEntity masterAdmin;

	public MasterAdminServiceImpl() {
	}

	@Override
	public void afterPropertiesSet() {
		masterAdmin.setRole(new RoleEntity("Master Admin", getMasterAdminPrivilages()));
		masterAdmin.setPassword(SecurityUtils.hash(masterAdmin.getPassword(), masterAdmin.getUsername()));
	}

	@Override
	public UserEntity getMasterAdmin() {
		return masterAdmin;
	}

	private Set<PrivilegeType> getMasterAdminPrivilages() {
		Set<PrivilegeType> privileges = new HashSet<PrivilegeType>();
		privileges.addAll(Arrays.asList(PrivilegeType.values()));
		return privileges;
	}

	@Override
	public boolean isMasterAdmin(String username) {
		return masterAdmin.getUsername().equalsIgnoreCase(StringUtils.defaultString(username));
	}

	public void setMasterAdmin(UserEntity masterAdmin) {
		this.masterAdmin = masterAdmin;
	}

}

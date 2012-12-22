/**
 * Project:   rms-client-android
 * File:      RolesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.services;

import java.util.List;

import pl.bcichecki.rms.client.android.exceptions.impl.ServiceException;
import pl.bcichecki.rms.client.android.model.impl.RoleEntity;

/**
 * @author Bartosz Cichecki
 */
public interface RolesService {

	boolean createRole(RoleEntity role) throws ServiceException;

	boolean deleteRole(String id) throws ServiceException;

	List<RoleEntity> getAllRoles();

	RoleEntity getRoleById(String id) throws ServiceException;

	RoleEntity getRoleByName(String name) throws ServiceException;

	List<RoleEntity> getUsersRoles(String id) throws ServiceException;

	List<RoleEntity> getUsersRolesByUsername(String username) throws ServiceException;

	boolean updateRole(RoleEntity role) throws ServiceException;

}

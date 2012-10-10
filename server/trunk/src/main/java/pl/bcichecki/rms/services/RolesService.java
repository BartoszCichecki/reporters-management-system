/**
 * Project:   rms-server
 * File:      RolesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.RoleEntity;

/**
 * @author Bartosz Cichecki
 */
public interface RolesService {

	boolean createRole(RoleEntity role) throws ServiceException;

	boolean deleteRole(Long id) throws ServiceException;

	List<RoleEntity> getAllRoles();

	RoleEntity getRoleById(Long id) throws ServiceException;

	RoleEntity getRoleByName(String name) throws ServiceException;

	List<RoleEntity> getUsersRoles(Long id) throws ServiceException;

	List<RoleEntity> getUsersRoles(String username) throws ServiceException;

	boolean updateRole(RoleEntity role) throws ServiceException;

}

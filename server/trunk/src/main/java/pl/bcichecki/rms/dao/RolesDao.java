/**
 * Project:   Reporters Management System - Server
 * File:      RolesDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.util.List;

import pl.bcichecki.rms.model.impl.RoleEntity;

/**
 * @author Bartosz Cichecki
 */
public interface RolesDao extends GenericDao<RoleEntity> {

	RoleEntity getByName(String name);

	List<RoleEntity> getByUserId(Long id);

	List<RoleEntity> getByUsername(String username);
}

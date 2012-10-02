/**
 * Project:   rms-server
 * File:      UsersDao.java
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
import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public interface UsersDao extends GenericDao<UserEntity> {

	List<UserEntity> getAllUndeleted(boolean idAndVersionOnly);

	UserEntity getByEmail(String email);

	UserEntity getByUsername(String username);

	List<UserEntity> getUsersWithRole(Long roleId, boolean idAndVersionOnly);

	boolean hasUsersWithRole(RoleEntity role);

}

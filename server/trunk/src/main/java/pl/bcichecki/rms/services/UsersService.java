/**
 * Project:   rms-server
 * File:      UsersService.java
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
import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public interface UsersService {

	boolean createUser(UserEntity user) throws ServiceException;

	boolean deleteUser(Long id, boolean markDeleted) throws ServiceException;

	List<UserEntity> getAllActiveUsers();

	List<UserEntity> getAllUsers();

	UserEntity getUserById(Long id) throws ServiceException;

	UserEntity getUserByUsername(String username) throws ServiceException;

	List<UserEntity> getUsersWithRole(Long roleId) throws ServiceException;

	String recreatePassword(String username) throws ServiceException;

	boolean registerUser(UserEntity user) throws ServiceException;

	boolean updateUser(UserEntity user) throws ServiceException;

	boolean updateUserWithRestrictions(UserEntity user, UserEntity oldUser) throws ServiceException;

}

/**
 * Project:   rms-client-android
 * File:      UsersService.java
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
import pl.bcichecki.rms.client.android.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public interface UsersService {

	boolean createUser(UserEntity user) throws ServiceException;

	boolean deleteUser(String id, boolean markDeleted) throws ServiceException;

	List<UserEntity> getAllActiveUsers();

	List<UserEntity> getAllUsers();

	UserEntity getUserById(String id) throws ServiceException;

	UserEntity getUserByUsername(String username) throws ServiceException;

	List<UserEntity> getUsersWithRole(String roleId) throws ServiceException;

	String recreatePassword(String username) throws ServiceException;

	boolean registerUser(UserEntity user) throws ServiceException;

	boolean updateUser(UserEntity user) throws ServiceException;

	boolean updateUserWithRestrictions(UserEntity user, UserEntity oldUser) throws ServiceException;

}

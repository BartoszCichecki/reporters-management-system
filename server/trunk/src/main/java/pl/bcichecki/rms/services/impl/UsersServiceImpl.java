/**
 * Project:   rms-server
 * File:      UsersServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.CustomUser;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.EmergencyAdminService;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UsersServiceImpl implements UsersService, UserDetailsService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private RolesDao rolesDao;

	@Autowired
	private EmergencyAdminService emergencyAdminService;

	private UserDetails buildUser(UserEntity user) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRole() != null && user.getRole().getPrivileges() != null) {
			for (PrivilegeType privilege : user.getRole().getPrivileges()) {
				authorities.add(new SimpleGrantedAuthority(privilege.toString()));
			}
		}
		return new CustomUser(user, user.getId(), user.getUsername(), user.getPassword(), !user.isDeleted(), !user.isLocked(),
		        !user.isLocked(), !user.isLocked(), authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean createUser(UserEntity user) throws ServiceException {
		if (usersDao.getByUsername(user.getUsername()) != null) {
			throw new ServiceException("User with such username already exist! Users must have unique usernames.",
			        "exceptions.serviceExceptions.users.duplicateUsername");
		}
		if (usersDao.getByEmail(user.getEmail()) != null) {
			throw new ServiceException("User with such email already exist! Users must have unique emails.",
			        "exceptions.serviceExceptions.users.duplicateEmail");
		}
		user.setRole(reloadRole(user.getRole()));
		usersDao.create(user);
		return true;
	}

	@Override
	public boolean deleteUser(String id, boolean markDeleted) throws ServiceException {
		UserEntity user = usersDao.getById(id);
		if (user == null) {
			throw new ServiceException("You can't delete user that does not exist!",
			        "exceptions.serviceExceptions.users.cantDeleteNotExisting");
		}
		if (markDeleted) {
			user.setDeleted(true);
			usersDao.update(user);
		} else {
			usersDao.delete(user);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserEntity> getAllActiveUsers() {
		return usersDao.getAll(false, false);
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return usersDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserEntity getUserById(String id) throws ServiceException {
		UserEntity user = usersDao.getById(id);
		if (user == null) {
			throw new ServiceException("User with this ID does not exist!", "exceptions.serviceExceptions.users.notExistId");
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public UserEntity getUserByUsername(String username) throws ServiceException {
		UserEntity user = usersDao.getByUsername(username);
		if (user == null) {
			throw new ServiceException("User with this username does not exist!", "exceptions.serviceExceptions.users.notExistUsername");
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserEntity> getUsersWithRole(String roleId) throws ServiceException {
		if (rolesDao.getById(roleId) == null) {
			throw new ServiceException("Role with this ID does not exist!", "exceptions.serviceExceptions.roles.notExistId");
		}
		return usersDao.getUsersWithRole(roleId);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (emergencyAdminService.isEmergencyAdmin(username) && !emergencyAdminService.getEmergencyAdmin().isLocked()) {
			return buildUser(emergencyAdminService.getEmergencyAdmin());
		}
		UserEntity user = usersDao.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User was not found in database.");
		}
		return buildUser(usersDao.getByUsername(username));
	}

	@Override
	public String recreatePassword(String username) throws ServiceException {
		UserEntity user = usersDao.getByUsername(username);
		if (user == null) {
			throw new ServiceException("User with this username does not exist!", "exceptions.serviceExceptions.users.notExistUsername");
		}
		String newPassword = SecurityUtils.getRandomPassword();
		try {
			user.setPassword(SecurityUtils.hashSHA512Base64(newPassword));
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("This system is unable to hash passwords properly.", ex);
		}
		usersDao.update(user);
		return newPassword;
	}

	@Override
	public boolean registerUser(UserEntity user) throws ServiceException {
		if (usersDao.getByUsername(user.getUsername()) != null) {
			throw new ServiceException("User with such username already exist! Users must have unique usernames.",
			        "exceptions.serviceExceptions.users.duplicateUsername");
		}
		if (usersDao.getByEmail(user.getEmail()) != null) {
			throw new ServiceException("User with such email already exist! Users must have unique emails.",
			        "exceptions.serviceExceptions.users.duplicateEmail");
		}
		if (user.getAddress() == null || user.getEmail() == null) {
			throw new ServiceException("You must provide address data while creating account. At least email one address is required.",
			        "exceptions.serviceExceptions.users.registration.missingAddressData");
		}
		user.setRole(null);
		user.setDeleted(false);
		user.setLocked(true);

		usersDao.create(user);
		return true;
	}

	private RoleEntity reloadRole(RoleEntity role) throws ServiceException {
		RoleEntity reloadedRole = rolesDao.getById(role.getId());
		if (reloadedRole == null) {
			throw new ServiceException("Role with this ID does not exist!", "exceptions.serviceExceptions.roles.notExistId");
		}
		return reloadedRole;
	}

	@Override
	public boolean updateUser(UserEntity user) throws ServiceException {
		if (usersDao.getByUsername(user.getUsername()) != null) {
			throw new ServiceException("User with such username already exist! Users must have unique usernames.",
			        "exceptions.serviceExceptions.users.duplicateUsername");
		}
		if (usersDao.getByEmail(user.getEmail()) != null) {
			throw new ServiceException("User with such email already exist! Users must have unique emails.",
			        "exceptions.serviceExceptions.users.duplicateEmail");
		}
		UserEntity retrieved = usersDao.getById(user.getId());
		if (retrieved == null) {
			throw new ServiceException("You can't update user that does not exist!",
			        "exceptions.serviceExceptions.users.cantUpdateNotExisting");
		}
		user.setRole(reloadRole(user.getRole()));
		retrieved.merge(user);
		usersDao.update(retrieved);
		return true;
	}

	@Override
	public boolean updateUserWithRestrictions(UserEntity user, UserEntity oldUser) throws ServiceException {
		user.setId(oldUser.getId());
		user.setUsername(oldUser.getUsername());
		user.setRole(oldUser.getRole());
		user.setLocked(oldUser.isLocked());
		user.setDeleted(oldUser.isDeleted());
		user.getAddress().setId(oldUser.getAddress().getId());
		return updateUser(user);
	}

}

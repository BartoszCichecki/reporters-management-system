/**
 * Project:   Reporters Management System - Server
 * File:      UserDetailsServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      21-08-2012
 */

package pl.bcichecki.rms.services.special;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.model.impl.PrivilegeType;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.EmergencyAdminService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmergencyAdminService emergencyAdminService;
	@Autowired
	private UsersDao usersDao;

	private UserDetails buildUser(UserEntity user) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (PrivilegeType privilege : user.getRole().getPrivileges()) {
			authorities.add(new SimpleGrantedAuthority(privilege.toString()));
		}
		return new User(user.getUsername(), user.getPassword(), !user.isDeleted(), !user.isLocked(), !user.isLocked(),
				!user.isLocked(), authorities);
	}

	@Override
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

}

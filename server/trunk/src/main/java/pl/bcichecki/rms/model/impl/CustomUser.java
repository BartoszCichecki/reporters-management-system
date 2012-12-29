/**
 * Project:   rms-server
 * File:      CustomUser.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      29-12-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Bartosz Cichecki
 */
public class CustomUser extends User {

	private static final long serialVersionUID = -7462814601093909465L;

	private UserEntity userEntity;

	private String id;

	public CustomUser(UserEntity userEntity, String id, String username, String password, boolean enabled, boolean accountNonExpired,
	        boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		setId(id);
		setUserEntity(userEntity);
	}

	public CustomUser(UserEntity userEntity, String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		setId(id);
		setUserEntity(userEntity);
	}

	public String getId() {
		return id;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}

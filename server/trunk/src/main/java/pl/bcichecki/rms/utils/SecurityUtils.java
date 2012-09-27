/**
 * Project:   Reporters Management System - Server
 * File:      SecurityUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      20-08-2012
 */

package pl.bcichecki.rms.utils;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.UsersService;

/**
 * @author Bartosz Cichecki
 */
public class SecurityUtils {

	@Autowired
	private static UsersService usersService;

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static UserEntity getCurrentUser() {
		// TODO Implement
		// Keep emergency admin in mind.
		return null;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String hash(String stringToHash, String salt) {
		return DigestUtils.sha512Hex(stringToHash + ":" + salt);
	}
}

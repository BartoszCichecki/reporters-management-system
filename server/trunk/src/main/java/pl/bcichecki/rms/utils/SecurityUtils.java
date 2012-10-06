/**
 * Project:   rms-server
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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.UsersService;

/**
 * @author Bartosz Cichecki
 */
public class SecurityUtils {

	@Autowired
	private static UsersService usersService;

	public static UserEntity getCurrentUser() {
		try {
			String username = SecurityUtils.getCurrentUserUsername();
			if (username == null) {
				return null;
			}
			return usersService.getUserByUsername(username);
		} catch (ServiceException e) {
			return null;
		}
	}

	public static Long getCurrentUserId() {
		try {
			String username = SecurityUtils.getCurrentUserUsername();
			if (username == null) {
				return null;
			}
			UserEntity user = usersService.getUserByUsername(username);
			return user.getId();
		} catch (ServiceException e) {
			return null;
		}
	}

	public static String getCurrentUserUsername() {
		if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getName() == null) {
			throw new IllegalStateException("There is user authenticated without username?");
		}
		return authentication.getName();
	}

	public static String getRandomPassword() {
		return RandomStringUtils.random(8, true, true);
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String hashMD5(String stringToHash) {
		return DigestUtils.md5Hex(stringToHash);
	}

	public static String hashSHA512(String stringToHash, String salt) {
		return DigestUtils.sha512Hex(stringToHash + ":" + salt);
	}
}

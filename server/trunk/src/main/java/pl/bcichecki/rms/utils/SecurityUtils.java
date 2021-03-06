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

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.bcichecki.rms.model.impl.CustomUser;
import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public class SecurityUtils {

	private static final String CHARSET_UTF8 = "UTF-8";

	public static UserEntity getCurrentUser() {
		if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null
		        || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
			return null;
		}
		if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser)) {
			return null;
		}
		CustomUser authenticatedUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return authenticatedUser.getUserEntity() == null ? null : authenticatedUser.getUserEntity();
	}

	public static String getCurrentUserId() {
		if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null
		        || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
			return null;
		}
		if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser)) {
			return null;
		}
		return ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
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

	public static String hashMD5Base64(String stringToHash) throws UnsupportedEncodingException {
		byte[] bytes = stringToHash.getBytes(CHARSET_UTF8);
		byte[] md5bytes = DigestUtils.md5(bytes);
		byte[] base64bytes = Base64.encodeBase64(md5bytes);
		return new String(base64bytes, CHARSET_UTF8);
	}

	public static String hashSHA512Base64(String stringToHash) throws UnsupportedEncodingException {
		byte[] bytes = stringToHash.getBytes(CHARSET_UTF8);
		byte[] sha512bytes = DigestUtils.sha512(bytes);
		byte[] base64bytes = Base64.encodeBase64(sha512bytes);
		return new String(base64bytes, CHARSET_UTF8);
	}

}

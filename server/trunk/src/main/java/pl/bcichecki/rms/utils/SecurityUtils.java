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

/**
 * @author Bartosz Cichecki
 */
public class SecurityUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String hash(String stringToHash, String salt) {
		return DigestUtils.sha512Hex(stringToHash + ":" + salt);
	}
}

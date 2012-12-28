/**
 * Project:   rms-client-android
 * File:      SecurityUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-12-2012
 */

package pl.bcichecki.rms.client.android.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import android.util.Log;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SecurityUtils {

	private static final String TAG = "SecurityUtils";

	private static final String CHARSET_UTF8 = "UTF-8";

	public static String getRandomPassword() {
		return RandomStringUtils.random(8, true, true);
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String hashMD5Base64(String stringToHash) {
		try {
			byte[] bytes = stringToHash.getBytes(CHARSET_UTF8);
			byte[] sha512bytes = DigestUtils.md5(bytes);
			byte[] base64bytes = Base64.encodeBase64(sha512bytes);
			return new String(base64bytes, CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required hashing algorithms.", e);
			throw new IllegalStateException("This system does not support required hashing algorithms.", e);
		}
	}

	public static String hashSHA512Base64(String stringToHash) {
		try {
			byte[] bytes = stringToHash.getBytes(CHARSET_UTF8);
			byte[] sha512bytes = DigestUtils.sha512(bytes);
			byte[] base64bytes = Base64.encodeBase64(sha512bytes);
			return new String(base64bytes, CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required hashing algorithms.", e);
			throw new IllegalStateException("This system does not support required hashing algorithms.", e);
		}
	}

}

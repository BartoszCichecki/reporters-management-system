/**
 * Project:   rms-client-android
 * File:      UserProfileHolder.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.holders;

import pl.bcichecki.rms.client.android.model.impl.User;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UserProfileHolder {

	private static User userProfile;

	private static String username;

	private static String password;

	public static void clear() {
		username = null;
		password = null;
		userProfile = null;
	}

	public static String getPassword() {
		return password;
	}

	public static String getUsername() {
		return username;
	}

	public static User getUserProfile() {
		return userProfile;
	}

	public static void setPassword(String password) {
		UserProfileHolder.password = password;
	}

	public static void setUsername(String username) {
		UserProfileHolder.username = username;
	}

	public static void setUserProfile(User userProfile) {
		UserProfileHolder.userProfile = userProfile;
	}

}

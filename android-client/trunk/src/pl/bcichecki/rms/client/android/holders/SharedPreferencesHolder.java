/**
 * Project:   rms-client-android
 * File:      SharedPreferencesHolder.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.holders;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import pl.bcichecki.rms.client.android.application.Application;

public class SharedPreferencesHolder {

	public static class Defaults {

		public static final String USER_USERNAME = "";

		public static final String USER_PASSWORD_HASH = "";

		public static final String SERVER_ADDRESS = "127.0.0.1";

		public static final String SERVER_REALM = "rms";

		public static final int SERVER_PORT = 443;
	}

	public static class Keys {

		public static final String USER_USERNAME = "user.username";

		public static final String USER_PASSWORD_HASH = "user.pass_hash";

		public static final String SERVER_ADDRESS = "server.address";

		public static final String SERVER_REALM = "server.realm";

		public static final String SERVER_PORT = "server.port";
	}

	public static final String PREFERENCES_NAME = "preferences";

	private static SharedPreferences sharedPreferences = Application.getContext().getSharedPreferences(PREFERENCES_NAME,
	        Context.MODE_PRIVATE);

	public static SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public static Editor getSharedPreferencesEditor() {
		return sharedPreferences.edit();
	}

}

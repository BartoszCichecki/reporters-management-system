/**
 * Project:   rms-client-android
 * File:      SharedPreferencesWrapper.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.holders;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import pl.bcichecki.rms.client.android.application.Application;

public class SharedPreferencesWrapper {

	public static class Defaults {

		public static final String SERVER_ADDRESS = "127.0.0.1";

		public static final boolean SERVER_INCOMING_MD5 = false;

		public static final boolean SERVER_OUTGOING_MD5 = false;

		public static final int SERVER_PORT = 443;

		public static final String SERVER_REALM = "rms";

		public static final String SERVER_WEBSERVICE_CONTEXT_PATH = "/rest";

		public static final String USER_PASSWORD_HASH = "";

		public static final boolean USER_REMEMBER_USER = false;

		public static final String USER_USERNAME = "";
	}

	public static class Keys {

		public static final String SERVER_ADDRESS = "server.address";

		public static final String SERVER_INCOMING_MD5 = "server.incoming_md5";

		public static final String SERVER_OUTGOING_MD5 = "server.outgoing_md5";

		public static final String SERVER_PORT = "server.port";

		public static final String SERVER_REALM = "server.realm";

		public static final String SERVER_WEBSERVICE_CONTEXT_PATH = "server.ws_context_path";

		public static final String USER_PASSWORD_HASH = "user.pass_hash";

		public static final String USER_REMEMBER_USER = "user.remember_user";

		public static final String USER_USERNAME = "user.username";
	}

	private static final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());

	public static String getPasswordHash() {
		return sharedPreferences.getString(Keys.USER_PASSWORD_HASH, Defaults.USER_PASSWORD_HASH);
	}

	public static boolean getRememberUser() {
		return sharedPreferences.getBoolean(Keys.USER_REMEMBER_USER, Defaults.USER_REMEMBER_USER);
	}

	public static String getServerAddress() {
		return sharedPreferences.getString(Keys.SERVER_ADDRESS, Defaults.SERVER_ADDRESS);
	}

	public static boolean getServerIncomingMD5() {
		return sharedPreferences.getBoolean(Keys.SERVER_INCOMING_MD5, Defaults.SERVER_INCOMING_MD5);
	}

	public static boolean getServerOutgoingMD5() {
		return sharedPreferences.getBoolean(Keys.SERVER_OUTGOING_MD5, Defaults.SERVER_OUTGOING_MD5);
	}

	public static int getServerPort() {
		return Integer.parseInt(sharedPreferences.getString(Keys.SERVER_PORT, String.valueOf(Defaults.SERVER_PORT)));
	}

	public static String getServerRealm() {
		return sharedPreferences.getString(Keys.SERVER_REALM, Defaults.SERVER_REALM);
	}

	public static SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public static String getUsername() {
		return sharedPreferences.getString(Keys.USER_USERNAME, Defaults.USER_USERNAME);
	}

	public static String getWebserviceContextPath() {
		return sharedPreferences.getString(Keys.SERVER_WEBSERVICE_CONTEXT_PATH, Defaults.SERVER_WEBSERVICE_CONTEXT_PATH);
	}

	public static boolean setPasswordHash(String passwordHash) {
		return sharedPreferences.edit().putString(Keys.USER_PASSWORD_HASH, passwordHash).commit();
	}

	public static boolean setRememberUser(boolean remember) {
		return sharedPreferences.edit().putBoolean(Keys.USER_REMEMBER_USER, remember).commit();
	}

	public static boolean setServerAddress(String address) {
		return sharedPreferences.edit().putString(Keys.SERVER_ADDRESS, address).commit();
	}

	public static boolean setServerIncomingMD5(boolean enabled) {
		return sharedPreferences.edit().putBoolean(Keys.SERVER_INCOMING_MD5, enabled).commit();
	}

	public static boolean setServerOutgoingMD5(boolean enabled) {
		return sharedPreferences.edit().putBoolean(Keys.SERVER_OUTGOING_MD5, enabled).commit();
	}

	public static boolean setServerPort(int port) {
		return sharedPreferences.edit().putInt(Keys.SERVER_PORT, port).commit();
	}

	public static boolean setServerRealm(String realm) {
		return sharedPreferences.edit().putString(Keys.SERVER_REALM, realm).commit();
	}

	public static boolean setUsername(String username) {
		return sharedPreferences.edit().putString(Keys.USER_USERNAME, username).commit();
	}

	public static boolean setWebserviceContextPath(String contextPath) {
		return sharedPreferences.edit().putString(Keys.SERVER_WEBSERVICE_CONTEXT_PATH, contextPath).commit();
	}

}

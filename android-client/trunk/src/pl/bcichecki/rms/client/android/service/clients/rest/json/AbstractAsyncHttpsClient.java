/**
 * Project:   rms-client-android
 * File:      AbstractAsyncHttpsClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.service.clients.rest.json;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;

import pl.bcichecki.rms.client.android.holders.SharedPreferencesHolder;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.service.https.SimpleAsyncHttpsClient;
import pl.bcichecki.rms.client.android.service.https.authentication.HttpBasicAuthenticatable;

/**
 * @author Bartosz Cichecki
 * 
 */
public abstract class AbstractAsyncHttpsClient implements HttpBasicAuthenticatable, GsonAware {

	private AsyncHttpClient asyncHttpClient;

	private Context context;

	private Gson gson;

	private boolean isClientAuthenticated;

	public AbstractAsyncHttpsClient(Context context) {
		this.context = context;
		asyncHttpClient = new SimpleAsyncHttpsClient();
		isClientAuthenticated = false;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	@Override
	public void authenticate() {
		if (isAuthenticated()) {
			asyncHttpClient = new SimpleAsyncHttpsClient();
			setAuthenticated(true);
		}
		asyncHttpClient.setBasicAuth(getUsername(), getPassword(), new AuthScope(getHost(), getPort(), getRealm()));
	}

	protected String getAbsoluteAddress(String... resourceSuffixes) {
		StringBuilder absoluteAddress = new StringBuilder();
		absoluteAddress.append("https://").append(getServerAddress()).append(":").append(getServerPort());
		for (String resourceSuffix : resourceSuffixes) {
			absoluteAddress.append(resourceSuffix);
		}
		return absoluteAddress.toString();
	}

	public AsyncHttpClient getAsyncHttpsClient() {
		return asyncHttpClient;
	}

	public Context getContext() {
		return context;
	}

	@Override
	public Gson getGson() {
		return gson;
	}

	@Override
	public String getHost() {
		return getServerAddress();
	}

	@Override
	public String getPassword() {
		return StringUtils.defaultString(UserProfileHolder.getPasswordHash());
	}

	@Override
	public int getPort() {
		return getServerPort();
	}

	@Override
	public String getRealm() {
		return SharedPreferencesHolder.getSharedPreferences().getString(SharedPreferencesHolder.Keys.SERVER_REALM,
		        SharedPreferencesHolder.Defaults.SERVER_REALM);
	}

	protected String getServerAddress() {
		return SharedPreferencesHolder.getSharedPreferences().getString(SharedPreferencesHolder.Keys.SERVER_ADDRESS,
		        SharedPreferencesHolder.Defaults.SERVER_ADDRESS);
	}

	protected int getServerPort() {
		return SharedPreferencesHolder.getSharedPreferences().getInt(SharedPreferencesHolder.Keys.SERVER_PORT,
		        SharedPreferencesHolder.Defaults.SERVER_PORT);
	}

	@Override
	public String getUsername() {
		return StringUtils.defaultString(UserProfileHolder.getUsername());
	}

	@Override
	public boolean isAuthenticated() {
		return isClientAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		isClientAuthenticated = isAuthenticated;
	}

}

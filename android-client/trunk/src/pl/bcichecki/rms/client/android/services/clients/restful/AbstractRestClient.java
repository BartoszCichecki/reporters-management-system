/**
 * Project:   rms-client-android
 * File:      AbstractRestClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.bcichecki.rms.client.android.holders.SharedPreferencesHolder;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.https.SimpleAsyncHttpsClient;

/**
 * @author Bartosz Cichecki
 * 
 */
public abstract class AbstractRestClient implements GsonAware {

	private SimpleAsyncHttpsClient simpleAsyncHttpsClient;

	private Context context;

	private static Gson gson;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	public AbstractRestClient(Context context, SimpleAsyncHttpsClient simpleAsyncHttpsClient) {
		this.context = context;
		this.simpleAsyncHttpsClient = simpleAsyncHttpsClient;
	}

	protected String getAbsoluteAddress(String... resourceSuffixes) {
		StringBuilder absoluteAddress = new StringBuilder();
		absoluteAddress.append(HttpConstants.PROTOCOL_HTTPS).append(getServerAddress()).append(HttpConstants.COLON).append(getServerPort())
		        .append(getWebserviceContextPath());
		for (String resourceSuffix : resourceSuffixes) {
			if (!resourceSuffix.startsWith(HttpConstants.PATH_SEPARATOR)) {
				absoluteAddress.append(HttpConstants.PATH_SEPARATOR);
			}
			if (resourceSuffix.endsWith(HttpConstants.PATH_SEPARATOR)) {
				resourceSuffix = StringUtils.substringBeforeLast(resourceSuffix, HttpConstants.PATH_SEPARATOR);
			}
			absoluteAddress.append(resourceSuffix);
		}
		return absoluteAddress.toString();
	}

	public Context getContext() {
		return context;
	}

	@Override
	public Gson getGson() {
		return gson;
	}

	protected String getServerAddress() {
		return SharedPreferencesHolder.getSharedPreferences().getString(SharedPreferencesHolder.Keys.SERVER_ADDRESS,
		        SharedPreferencesHolder.Defaults.SERVER_ADDRESS);
	}

	protected int getServerPort() {
		return SharedPreferencesHolder.getSharedPreferences().getInt(SharedPreferencesHolder.Keys.SERVER_PORT,
		        SharedPreferencesHolder.Defaults.SERVER_PORT);
	}

	public SimpleAsyncHttpsClient getSimpleAsyncHttpsClient() {
		return simpleAsyncHttpsClient;
	}

	protected String getWebserviceContextPath() {
		return SharedPreferencesHolder.getSharedPreferences().getString(SharedPreferencesHolder.Keys.SERVER_WEBSERVICE_CONTEXT_PATH,
		        SharedPreferencesHolder.Defaults.SERVER_WEBSERVICE_CONTEXT_PATH);
	}

}

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

import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.https.SimpleAsyncHttpsClient;

/**
 * @author Bartosz Cichecki
 * 
 */
public abstract class AbstractRestClient extends SimpleAsyncHttpsClient implements GsonAware {

	protected String webServiceContextPath;

	private static Gson gson;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	private Context context;

	public AbstractRestClient(Context context) {
		this(context, SharedPreferencesWrapper.getUsername(), SharedPreferencesWrapper.getPasswordHash(), SharedPreferencesWrapper
		        .getServerRealm(), SharedPreferencesWrapper.getServerAddress(), SharedPreferencesWrapper.getServerPort(),
		        SharedPreferencesWrapper.getWebserviceContextPath());
	}

	public AbstractRestClient(Context context, String username, String password) {
		this(context, username, password, SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
	}

	public AbstractRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(username, password, realm, host, port);
		this.context = context;
		this.webServiceContextPath = webServiceContextPath;
	}

	protected String getAbsoluteAddress(String... resourceSuffixes) {
		StringBuilder absoluteAddress = new StringBuilder();
		absoluteAddress.append(HttpConstants.PROTOCOL_HTTPS).append(host).append(HttpConstants.COLON).append(port)
		        .append(webServiceContextPath);
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

}

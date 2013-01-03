/**
 * Project:   rms-server
 * File:      UtilitiesRestClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.GsonHolder;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
public class UtilitiesRestClient extends AbstractRestClient {

	private static final String TAG = "UtilitiesRestClient";

	public UtilitiesRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public UtilitiesRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void forgotPassword(String username, AsyncHttpResponseHandler handler) {
		post(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_FORGOT_PASSWORD, username), null, handler);
	}

	public void registerUser(User user, AsyncHttpResponseHandler handler) {
		String userAsJson = GsonHolder.getGson().toJson(user);
		HttpEntity userAsHttpEntity;

		try {
			userAsHttpEntity = new StringEntity(userAsJson, HttpConstants.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required encoding.", e);
			throw new IllegalStateException("This system does not support required encoding.", e);
		}

		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, userAsJson);

		put(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_REGISTER), getHeadersAsArray(headers), userAsHttpEntity,
		        HttpConstants.CONTENT_TYPE_APPLICATION_JSON_CHARSET_UTF8, handler);
	}
}

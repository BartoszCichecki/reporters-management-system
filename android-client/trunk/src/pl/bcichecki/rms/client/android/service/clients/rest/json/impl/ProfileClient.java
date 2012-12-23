/**
 * Project:   rms-server
 * File:      ProfileClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.client.android.service.clients.rest.json.impl;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.service.HttpConstants;
import pl.bcichecki.rms.client.android.service.clients.rest.json.AbstractAsyncHttpsClient;

/**
 * @author Bartosz Cichecki
 */
public class ProfileClient extends AbstractAsyncHttpsClient {

	private static final String RESOURCE_MY = "/my";

	private static final String RESOURCE_PROFILE = "/profile";

	public ProfileClient(Context context) {
		super(context);
	}

	public void getProfile(AsyncHttpResponseHandler handler) {
		getAsyncHttpsClient().get(getContext(), getAbsoluteAddress(RESOURCE_PROFILE, RESOURCE_MY), handler);
	}

	public void updateProfile(User profile, AsyncHttpResponseHandler handler) throws UnsupportedEncodingException {
		String profileAsJson = getGson().toJson(profile);
		HttpEntity profileAsHttpEntity = new StringEntity(profileAsJson, HttpConstants.CHARSET_UTF8);
		getAsyncHttpsClient().post(getContext(), getAbsoluteAddress(RESOURCE_PROFILE, RESOURCE_MY), profileAsHttpEntity,
		        HttpConstants.CONTENT_TYPE_APPLICATION_JSON, handler);
	}

}

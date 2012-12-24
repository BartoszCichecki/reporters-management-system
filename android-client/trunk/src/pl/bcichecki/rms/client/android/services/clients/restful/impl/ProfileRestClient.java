/**
 * Project:   rms-server
 * File:      ProfileRestClient.java
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

import com.google.inject.Inject;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
public class ProfileRestClient extends AbstractRestClient {

	private static final String RESOURCE_PATH_MY = "my";

	private static final String RESOURCE_PATH_PROFILE = "profile";

	@Inject
	public ProfileRestClient(Context context) {
		super(context);
	}

	public void getProfile(AsyncHttpResponseHandler handler) {
		getSimpleAsyncHttpsClient().get(getContext(), getAbsoluteAddress(RESOURCE_PATH_PROFILE, RESOURCE_PATH_MY), handler);
	}

	public void updateProfile(User profile, AsyncHttpResponseHandler handler) throws UnsupportedEncodingException {
		String profileAsJson = getGson().toJson(profile);
		HttpEntity profileAsHttpEntity = new StringEntity(profileAsJson, HttpConstants.CHARSET_UTF8);

		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, profileAsJson);

		getSimpleAsyncHttpsClient().post(getContext(), getAbsoluteAddress(RESOURCE_PATH_PROFILE, RESOURCE_PATH_MY),
		        (Header[]) headers.toArray(), profileAsHttpEntity, HttpConstants.CONTENT_TYPE_APPLICATION_JSON, handler);
	}

}

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

import com.google.inject.Inject;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
public class UtilitiesRestClient extends AbstractRestClient {

	private static final String RESOURCE_PATH_FORGOT_PASSWORD = "forgotPassword";

	private static final String RESOURCE_PATH_REGISTER = "register";

	@Inject
	public UtilitiesRestClient(Context context) {
		super(context);
	}

	public void forgotPassword(String username, AsyncHttpResponseHandler handler) {
		getSimpleAsyncHttpsClient().post(getContext(), getAbsoluteAddress(RESOURCE_PATH_FORGOT_PASSWORD, username), null, handler);
	}

	public void registerUser(User user, AsyncHttpResponseHandler handler) throws UnsupportedEncodingException {
		String userAsJson = getGson().toJson(user);
		HttpEntity userAsHttpEntity = new StringEntity(userAsJson, HttpConstants.CHARSET_UTF8);

		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, userAsJson);

		getSimpleAsyncHttpsClient().put(getContext(), getAbsoluteAddress(RESOURCE_PATH_REGISTER), (Header[]) headers.toArray(),
		        userAsHttpEntity, HttpConstants.CONTENT_TYPE_APPLICATION_JSON, handler);
	}
}

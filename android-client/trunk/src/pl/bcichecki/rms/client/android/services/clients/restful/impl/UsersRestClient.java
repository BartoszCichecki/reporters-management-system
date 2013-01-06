/**
 * Project:   rms-client-android
 * File:      UsersRestClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      06-01-2013
 */

package pl.bcichecki.rms.client.android.services.clients.restful.impl;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UsersRestClient extends AbstractRestClient {

	public UsersRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public UsersRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void getAllUsers(AsyncHttpResponseHandler handler) {
		get(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_USERS, RestConstants.RESOURCE_PATH_ALL), handler);
	}

}

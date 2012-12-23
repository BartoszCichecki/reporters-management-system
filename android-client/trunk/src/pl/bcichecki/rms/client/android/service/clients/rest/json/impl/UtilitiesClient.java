/**
 * Project:   rms-server
 * File:      UtilitiesClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.client.android.service.clients.rest.json.impl;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.service.clients.rest.json.AbstractAsyncHttpsClient;

/**
 * @author Bartosz Cichecki
 */
public class UtilitiesClient extends AbstractAsyncHttpsClient {

	public UtilitiesClient(Context context) {
		super(context);
	}

	public void forgotPassword(String username, AsyncHttpResponseHandler handler) {
	}

	public void registerUser(User user, AsyncHttpResponseHandler handler) {
	}

}

/**
 * Project:   rms-client-android
 * File:      EventsRestClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.impl;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesRestClient extends AbstractRestClient {

	public DevicesRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public DevicesRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void deleteDevice(Device deviceToDelete, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_DEVICES, deviceToDelete.getId()), handler);
	}

	public void getAllDevices(AsyncHttpResponseHandler handler) {
		get(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_DEVICES, RestConstants.RESOURCE_PATH_ALL), handler);
	}

}

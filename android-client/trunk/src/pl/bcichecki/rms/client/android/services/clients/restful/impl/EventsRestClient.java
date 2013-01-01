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

import java.util.Date;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsRestClient extends AbstractRestClient {

	public EventsRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public EventsRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void deleteMyEvent(Event eventToDelete, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_MY, eventToDelete.getId()),
		        handler);
	}

	public void getAllEvents(Date from, Date till, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		if (from != null) {
			params.put(RestConstants.PARAM_FROM, String.valueOf(from.getTime()));
		}
		if (till != null) {
			params.put(RestConstants.PARAM_TILL, String.valueOf(till.getTime()));
		}
		get(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_ALL), params, handler);
	}

}

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

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsRestClient extends AbstractRestClient {

	private static final String RESOURCE_PATH_EVENTS = "events";

	private static final String RESOURCE_PATH_ALL = "all";

	private static final String RESOURCE_PATH_MY = "my";

	private static final String PARAM_FROM = "from";

	private static final String PARAM_TILL = "till";

	public EventsRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public EventsRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void deleteMyEvent(Event eventToDelete, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RESOURCE_PATH_EVENTS, RESOURCE_PATH_MY, eventToDelete.getId()), handler);
	}

	public void getAllEvents(Date from, Date till, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		if (from != null) {
			params.put(PARAM_FROM, String.valueOf(from.getTime()));
		}
		if (till != null) {
			params.put(PARAM_TILL, String.valueOf(till.getTime()));
		}
		get(getContext(), getAbsoluteAddress(RESOURCE_PATH_EVENTS, RESOURCE_PATH_ALL), params, handler);
	}

}

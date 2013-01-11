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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.GsonHolder;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsRestClient extends AbstractRestClient {

	private static final String TAG = "EventsRestClient";

	public EventsRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public EventsRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void archiveEvent(Event event, AsyncHttpResponseHandler handler) {
		post(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_ARCHIVED, event.getId()),
		        handler);
	}

	public void createEvent(Event event, AsyncHttpResponseHandler handler) {
		String eventAsJson = GsonHolder.getGson().toJson(event);
		HttpEntity eventAsHttpEntity;

		try {
			eventAsHttpEntity = new StringEntity(eventAsJson, HttpConstants.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required encoding.", e);
			throw new IllegalStateException("This system does not support required encoding.", e);
		}
		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, eventAsJson);

		put(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS), getHeadersAsArray(headers), eventAsHttpEntity,
		        HttpConstants.CONTENT_TYPE_APPLICATION_JSON_CHARSET_UTF8, handler);
	}

	public void deleteMyEvent(Event eventToDelete, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, eventToDelete.getId()), handler);
	}

	public void getAllArchivedEvents(Date from, Date till, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		if (from != null) {
			params.put(RestConstants.PARAM_FROM, String.valueOf(from.getTime()));
		}
		if (till != null) {
			params.put(RestConstants.PARAM_TILL, String.valueOf(till.getTime()));
		}
		get(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_ARCHIVED,
		                RestConstants.RESOURCE_PATH_ALL), params, handler);
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

	public void getDevicesEvents(Device device, Date from, Date till, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		if (from != null) {
			params.put(RestConstants.PARAM_FROM, String.valueOf(from.getTime()));
		}
		if (till != null) {
			params.put(RestConstants.PARAM_TILL, String.valueOf(till.getTime()));
		}
		get(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_DEVICES, device.getId()),
		        params, handler);
	}

	public void getEvent(Event event, AsyncHttpResponseHandler handler) {
		get(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, event.getId()), handler);
	}

	public void lockEvent(Event event, boolean lock, AsyncHttpResponseHandler handler) {
		post(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, lock ? RestConstants.RESOURCE_PATH_LOCK
		                : RestConstants.RESOURCE_PATH_UNLOCK, event.getId()), handler);
	}

	public void signUpForEvent(Event event, boolean signUp, AsyncHttpResponseHandler handler) {
		post(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, signUp ? RestConstants.RESOURCE_PATH_SIGN_UP
		                : RestConstants.RESOURCE_PATH_SIGN_OUT, event.getId()), handler);
	}

	public void updateMyEvent(Event event, AsyncHttpResponseHandler handler) {
		String eventAsJson = GsonHolder.getGson().toJson(event);
		HttpEntity eventAsHttpEntity;

		try {
			eventAsHttpEntity = new StringEntity(eventAsJson, HttpConstants.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required encoding.", e);
			throw new IllegalStateException("This system does not support required encoding.", e);
		}
		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, eventAsJson);

		post(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_EVENTS, RestConstants.RESOURCE_PATH_MY),
		        getHeadersAsArray(headers), eventAsHttpEntity, HttpConstants.CONTENT_TYPE_APPLICATION_JSON_CHARSET_UTF8, handler);
	}

}

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
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.services.clients.restful.AbstractRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.GsonHolder;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestConstants;
import pl.bcichecki.rms.client.android.services.clients.restful.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class MessagesRestClient extends AbstractRestClient {

	private static final String TAG = "MessagesRestClient";

	public MessagesRestClient(Context context, String host, int port, String webServiceContextPath) {
		super(context, host, port, webServiceContextPath);
	}

	public MessagesRestClient(Context context, String username, String password, String realm, String host, int port,
	        String webServiceContextPath) {
		super(context, username, password, realm, host, port, webServiceContextPath);
	}

	public void archiveMessage(Message message, AsyncHttpResponseHandler handler) {
		post(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_ARCHIVED, message.getId()),
		        handler);
	}

	public void deleteArchivedInboxMessage(Message message, AsyncHttpResponseHandler handler) {
		delete(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_ARCHIVED,
		                RestConstants.RESOURCE_PATH_INBOX, message.getId()), handler);
	}

	public void deleteArchivedOutboxMessage(Message message, AsyncHttpResponseHandler handler) {
		delete(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_ARCHIVED,
		                RestConstants.RESOURCE_PATH_OUTBOX, message.getId()), handler);
	}

	public void deleteInboxMessage(Message message, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_INBOX, message.getId()),
		        handler);
	}

	public void deleteOutboxMessage(Message message, AsyncHttpResponseHandler handler) {
		delete(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_OUTBOX, message.getId()),
		        handler);
	}

	public void getAllArchivedInboxMessages(AsyncHttpResponseHandler handler) {
		get(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_ARCHIVED,
		                RestConstants.RESOURCE_PATH_INBOX, RestConstants.RESOURCE_PATH_ALL), handler);
	}

	public void getAllArchivedOutboxMessages(AsyncHttpResponseHandler handler) {
		get(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_ARCHIVED,
		                RestConstants.RESOURCE_PATH_OUTBOX, RestConstants.RESOURCE_PATH_ALL), handler);
	}

	public void getAllInboxMessages(AsyncHttpResponseHandler handler) {
		get(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_INBOX, RestConstants.RESOURCE_PATH_ALL),
		        handler);
	}

	public void getAllOutboxMessages(AsyncHttpResponseHandler handler) {
		get(getContext(),
		        getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_OUTBOX,
		                RestConstants.RESOURCE_PATH_ALL), handler);
	}

	public void markMessageRead(Message message, AsyncHttpResponseHandler handler) {
		post(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_INBOX, message.getId()),
		        handler);
	}

	public void sendMessage(Message message, AsyncHttpResponseHandler handler) {
		String messageAsJson = GsonHolder.getGson().toJson(message);
		HttpEntity messageAsHttpEntity;

		try {
			messageAsHttpEntity = new StringEntity(messageAsJson, HttpConstants.CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "This system does not support required encoding.", e);
			throw new IllegalStateException("This system does not support required encoding.", e);
		}
		List<Header> headers = new ArrayList<Header>();
		RestUtils.decorareHeaderWithMD5(headers, messageAsJson);

		put(getContext(), getAbsoluteAddress(RestConstants.RESOURCE_PATH_MESSAGES, RestConstants.RESOURCE_PATH_OUTBOX, message.getId()),
		        getHeadersAsArray(headers), messageAsHttpEntity, HttpConstants.CONTENT_TYPE_APPLICATION_JSON_CHARSET_UTF8, handler);
	}

}

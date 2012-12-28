/**
 * Project:   rms-client-android
 * File:      GsonHttpResponseHandler.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.https;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author Bartosz Cichecki
 * 
 */
public class GsonHttpResponseHandler<T> extends AsyncHttpResponseHandler {

	private static final String TAG = "GsonHttpResponseHandler";

	private Gson gson;

	private Class<T> clazz;

	private boolean strictMode;

	@SuppressWarnings("unchecked")
	public GsonHttpResponseHandler(Gson gson, boolean strictMode) {
		super();
		this.gson = gson;
		this.strictMode = strictMode;
		clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	protected void handleSuccessMessage(int statusCode, String responseBody) {
		prepareSuccessMessage(statusCode, responseBody);
	}

	@Override
	public void onFailure(Throwable error, String content) {
	};

	@Override
	public void onSuccess(int statusCode, String content) {

	};

	public void onSuccess(int statusCode, T jsonObject) {
	};

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated This method is dead, use {@link #onSuccess(int, String)}
	 */
	@Deprecated
	@Override
	public void onSuccess(String content) {
	}

	private void prepareSuccessMessage(int statusCode, String responseBody) {
		try {
			T jsonObject = gson.fromJson(responseBody, clazz);

			Log.d(TAG, "Valid JSON response, proceeding to onSuccess(int, " + clazz.getSimpleName() + ")...");
			onSuccess(statusCode, jsonObject);
		} catch (JsonParseException ex) {
			if (strictMode) {
				Log.d(TAG, "Not a valid JSON response, proceeding to onFailure(Throwable, String)...");
				onFailure(ex, responseBody);
			} else {
				Log.d(TAG, "Not a valid JSON response, proceeding to onSuccess(int, String)...");
				onSuccess(statusCode, responseBody);
			}
		}
	};

}

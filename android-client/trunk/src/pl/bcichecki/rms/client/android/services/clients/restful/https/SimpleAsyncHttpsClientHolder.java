/**
 * Project:   rms-client-android
 * File:      SimpleAsyncHttpsClientHolder.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.https;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SimpleAsyncHttpsClientHolder {

	private static final SimpleAsyncHttpsClient SIMPLE_ASYNC_HTTPS_CLIENT = new SimpleAsyncHttpsClient();

	public static final SimpleAsyncHttpsClient getInstance() {
		return SIMPLE_ASYNC_HTTPS_CLIENT;
	}

}

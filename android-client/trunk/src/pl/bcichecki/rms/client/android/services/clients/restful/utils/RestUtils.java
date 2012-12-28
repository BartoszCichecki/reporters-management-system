/**
 * Project:   rms-client-android
 * File:      RestUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.utils;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.services.clients.restful.https.HttpConstants;
import pl.bcichecki.rms.client.android.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class RestUtils {

	public static void decorareHeaderWithContentType(List<Header> headers, String contentType) {
		headers.add(new BasicHeader(HttpConstants.CONTENT_TYPE, contentType));
	}

	public static void decorareHeaderWithMD5(List<Header> headers, String content) {
		if (SharedPreferencesWrapper.getSharedPreferences().getBoolean(SharedPreferencesWrapper.Keys.SERVER_OUTGOING_MD5,
		        SharedPreferencesWrapper.Defaults.SERVER_OUTGOING_MD5)) {
			headers.add(new BasicHeader(HttpConstants.CONTENT_MD5, SecurityUtils.hashMD5Base64(content)));
		}
	}
}

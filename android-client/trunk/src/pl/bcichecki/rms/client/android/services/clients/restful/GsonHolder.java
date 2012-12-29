/**
 * Project:   rms-client-android
 * File:      GsonHolder.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Bartosz Cichecki
 * 
 */
public class GsonHolder {

	private static Gson gson;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}

	public static Gson getGson() {
		return gson;
	}

	public static void setGson(Gson gson) {
		GsonHolder.gson = gson;
	}

}

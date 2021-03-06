/**
 * Project:   rms-client-android
 * File:      PojoUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      03-01-2013
 */

package pl.bcichecki.rms.client.android.model.utils;

import com.google.gson.Gson;

import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;

/**
 * @author Bartosz Cichecki
 * 
 */
public class PojoUtils {

	private static Gson gson = new Gson();

	public static Device createDefensiveCopy(Device deviceToCopy) {
		if (deviceToCopy == null) {
			return null;
		}
		return gson.fromJson(gson.toJson(deviceToCopy), Device.class);
	}

	public static Event createDefensiveCopy(Event eventToCopy) {
		if (eventToCopy == null) {
			return null;
		}
		return gson.fromJson(gson.toJson(eventToCopy), Event.class);
	}

}

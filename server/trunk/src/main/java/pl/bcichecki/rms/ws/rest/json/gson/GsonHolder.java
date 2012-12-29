/**
 * Project:   rms-server
 * File:      GsonHolder.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      29-12-2012
 */

package pl.bcichecki.rms.ws.rest.json.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.ws.rest.json.gson.exclusionStrategies.PasswordExclusionStrategy;

/**
 * @author Bartosz Cichecki
 */
public class GsonHolder {

	public static final int DEFAULT = 137;

	public static final int RESTRICTED = 227;

	public static final int PRETTY_PRINTING = 313;

	public static final Gson defaultGson;

	public static final Gson restrictedGson;

	public static final Gson prettyPrintingGson;

	static {
		defaultGson = new Gson();

		GsonBuilder gsonBuilder;
		try {
			gsonBuilder = new GsonBuilder();
			ExclusionStrategy passwordExclusionStrategy = new PasswordExclusionStrategy(UserEntity.class,
			        UserEntity.class.getDeclaredField("password"));
			gsonBuilder.setExclusionStrategies(passwordExclusionStrategy);
			restrictedGson = gsonBuilder.create();
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}

		gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		prettyPrintingGson = gsonBuilder.create();
	}

	public static final Gson getGson() {
		return getGson(DEFAULT);
	}

	public static Gson getGson(int mode) {
		switch (mode) {
			case RESTRICTED:
				return restrictedGson;
			case PRETTY_PRINTING:
				return prettyPrintingGson;
			default:
				return defaultGson;
		}
	}

}

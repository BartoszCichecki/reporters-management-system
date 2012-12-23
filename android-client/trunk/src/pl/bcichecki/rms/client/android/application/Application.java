/**
 * Project:   rms-client-android
 * File:      Application.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.application;

import android.content.Context;

/**
 * @author Bartosz Cichecki
 * 
 */
public class Application extends android.app.Application {

	private static Context context;

	public static Context getContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}

}

/**
 * Project:   rms-server
 * File:      AbstractPrettyPrinter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.prettyPrinters;

import android.content.Context;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractPrettyPrinter<T> {

	private Context context;

	public AbstractPrettyPrinter(Context context) {
		this.setContext(context);
	}

	public Context getContext() {
		return context;
	}

	public abstract String print(T object);

	public void setContext(Context context) {
		this.context = context;
	}

}

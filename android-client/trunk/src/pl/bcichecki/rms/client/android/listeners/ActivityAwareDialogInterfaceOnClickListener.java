/**
 * Project:   rms-client-android
 * File:      ActivityAwareDialogInterfaceOnClickListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.listeners;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;

/**
 * @author Bartosz Cichecki
 * 
 */
public abstract class ActivityAwareDialogInterfaceOnClickListener implements OnClickListener {

	private Activity activity;

	public ActivityAwareDialogInterfaceOnClickListener(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}

}

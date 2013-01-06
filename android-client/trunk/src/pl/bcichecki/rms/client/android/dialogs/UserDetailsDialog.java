/**
 * Project:   rms-client-android
 * File:      DeviceDetailsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.model.impl.User;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UserDetailsDialog extends DialogFragment {

	private User user;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (user == null) {
			throw new IllegalStateException("User has not been set!");
		}

		// TODO expand details

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(getString(R.string.dialog_device_details_title, user.getUsername()));
		dialogBuilder.setMessage(user.toString());
		dialogBuilder.setPositiveButton(R.string.general_close, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		AlertDialog dialog = dialogBuilder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				// Nothing to do...
			}
		});

		return dialog;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

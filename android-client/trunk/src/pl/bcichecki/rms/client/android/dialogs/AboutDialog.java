/**
 * Project:   rms-client-android
 * File:      AboutDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      29-12-2012
 */

package pl.bcichecki.rms.client.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import pl.bcichecki.rms.client.android.R;

/**
 * @author Bartosz Cichecki
 * 
 */
public class AboutDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder aboutDialog = new AlertDialog.Builder(getActivity());
		aboutDialog.setTitle(R.string.dialog_about_title);

		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		aboutDialog.setView(layoutInflater.inflate(R.layout.dialog_about, null));

		aboutDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		return aboutDialog.create();
	}
}

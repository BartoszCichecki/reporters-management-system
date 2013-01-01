/**
 * Project:   rms-client-android
 * File:      RemindPasswordDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-12-2012
 */

package pl.bcichecki.rms.client.android.dialogs;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UtilitiesRestClient;
import pl.bcichecki.rms.client.android.utils.UiUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class RemindPasswordDialog extends DialogFragment {

	protected static final String TAG = "RemindPasswordDialog";

	private UtilitiesRestClient utilitiesRestClient;

	private void cancelRequests() {
		if (utilitiesRestClient != null) {
			utilitiesRestClient.cancelRequests(getActivity(), true);
			Toast.makeText(getActivity(), getString(R.string.dialog_remind_password_recovery_aborted), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		cancelRequests();
		super.onCancel(dialog);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getString(R.string.dialog_remind_password_title));
		builder.setMessage(getString(R.string.dialog_remind_password_message));

		final LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		int space = (int) UiUtils.convertDpToPixel(getActivity(), 16);
		layout.setPadding(space, 0, space, 0);

		final EditText usernameEditText = new EditText(getActivity());
		usernameEditText.setHint(getString(R.string.dialog_remind_password_enter_username_hint));
		usernameEditText.setMaxLines(1);
		usernameEditText.setSingleLine();
		usernameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		usernameEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
			}
		});
		layout.addView(usernameEditText);

		builder.setView(layout);

		builder.setPositiveButton(getString(R.string.dialog_remind_password_ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				return;
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		builder.setCancelable(false);

		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialogInterface) {
				utilitiesRestClient = new UtilitiesRestClient(getActivity(), SharedPreferencesWrapper.getServerAddress(),
				        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

				final Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				positiveButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!UiUtils.checkInternetConnection(getActivity())) {
							Log.d(TAG, "There is NO network connected!");
							return;
						}

						usernameEditText.setError(null);

						if (StringUtils.isBlank(usernameEditText.getText().toString())) {
							usernameEditText.setError(getString(R.string.dialog_remind_password_field_required));
							return;
						}

						final String username = usernameEditText.getText().toString();

						utilitiesRestClient.forgotPassword(username, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(Throwable error, String content) {
								Log.d(TAG, "Reminding password failed. [error=" + error + ", content=" + content + "]");
								Toast.makeText(getActivity(), getString(R.string.dialog_remind_password_recovery_failed), Toast.LENGTH_LONG)
								        .show();
							}

							@Override
							public void onFinish() {
								positiveButton.setEnabled(true);
							}

							@Override
							public void onStart() {
								Log.d(TAG, "Reminding password for user: " + username);
								Toast.makeText(getActivity(), getString(R.string.dialog_remind_password_recovery_in_progress),
								        Toast.LENGTH_SHORT).show();
								positiveButton.setEnabled(false);
							}

							@Override
							public void onSuccess(int statusCode, String content) {
								Log.d(TAG, "Reminding password success.");
								Toast.makeText(getActivity(), getString(R.string.dialog_remind_password_recovery_successful),
								        Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}

						});

					}
				});

				final Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
				negativeButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						cancelRequests();
						dialog.dismiss();
					}
				});

			}
		});

		return dialog;
	}

	@Override
	public void onStop() {
		if (utilitiesRestClient != null) {
			utilitiesRestClient.cancelRequests(getActivity(), true);
		}
		super.onStop();
	}
}

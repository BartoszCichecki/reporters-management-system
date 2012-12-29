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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UtilitiesRestClient;
import pl.bcichecki.rms.client.android.utils.UiUtils;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectResource;

/**
 * @author Bartosz Cichecki
 * 
 */
public class RemindPasswordDialog extends RoboDialogFragment {

	protected static final String TAG = "RemindPasswordDialog";

	@InjectResource(R.string.dialog_remind_password_title)
	private String titleText;

	@InjectResource(R.string.dialog_remind_password_message)
	private String enterUsernameMessageText;

	@InjectResource(R.string.dialog_remind_password_enter_username_hint)
	private String enterUsernameHintText;

	@InjectResource(R.string.dialog_remind_password_ok)
	private String okText;

	@InjectResource(R.string.dialog_remind_password_field_required)
	private String fieldRequiredText;

	@InjectResource(R.string.dialog_remind_password_recovery_successful)
	private String recoverySuccessful;

	@InjectResource(R.string.dialog_remind_password_recovery_failed)
	private String recoveryFailed;

	@InjectResource(R.string.dialog_remind_password_recovery_aborted)
	private String recoveryAborted;

	@InjectResource(R.string.dialog_remind_password_recovery_in_progress)
	private String recoveryInProgress;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(titleText);
		builder.setMessage(enterUsernameMessageText);

		final LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		int space = (int) UiUtils.convertDpToPixel(getActivity(), 16);
		layout.setPadding(space, 0, space, 0);

		final EditText usernameEditText = new EditText(getActivity());
		usernameEditText.setHint(enterUsernameHintText);
		usernameEditText.setMaxLines(1);
		usernameEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
			}
		});
		layout.addView(usernameEditText);

		builder.setView(layout);

		builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {

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

				final UtilitiesRestClient utilitiesRestClient = new UtilitiesRestClient(getActivity(), SharedPreferencesWrapper
				        .getServerAddress(), SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

				final Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				positiveButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						usernameEditText.setError(null);

						if (StringUtils.isBlank(usernameEditText.getText().toString())) {
							usernameEditText.setError(fieldRequiredText);
							return;
						}

						final String username = usernameEditText.getText().toString();

						utilitiesRestClient.forgotPassword(username, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(Throwable error, String content) {
								Log.d(TAG, "Reminding password failed. [error=" + error + ", content=" + content + "]");
								Toast.makeText(getActivity(), recoveryFailed, Toast.LENGTH_LONG).show();
							}

							@Override
							public void onFinish() {
								positiveButton.setEnabled(true);
							}

							@Override
							public void onStart() {
								Log.d(TAG, "Reminding password for user: " + username);
								Toast.makeText(getActivity(), recoveryInProgress, Toast.LENGTH_SHORT).show();
								positiveButton.setEnabled(false);
							}

							@Override
							public void onSuccess(int statusCode, String content) {
								Log.d(TAG, "Reminding password success.");
								Toast.makeText(getActivity(), recoverySuccessful, Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}

						});

					}
				});

				final Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
				negativeButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (utilitiesRestClient != null) {
							utilitiesRestClient.cancelRequests(getActivity(), true);
							Toast.makeText(getActivity(), recoveryAborted, Toast.LENGTH_LONG).show();
						}
						dialog.dismiss();
					}
				});

			}
		});

		return dialog;
	}
}

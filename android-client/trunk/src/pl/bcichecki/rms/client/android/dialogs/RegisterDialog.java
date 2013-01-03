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
import android.text.InputType;
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
import pl.bcichecki.rms.client.android.model.impl.AddressData;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UtilitiesRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;
import pl.bcichecki.rms.client.android.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class RegisterDialog extends DialogFragment {

	protected static final String TAG = "RegisterDialog";

	private UtilitiesRestClient utilitiesRestClient;

	private void cancelRequests() {
		if (utilitiesRestClient != null) {
			utilitiesRestClient.cancelRequests(getActivity(), true);
			AppUtils.showCenteredToast(getActivity(), getString(R.string.dialog_register_aborted), Toast.LENGTH_LONG);
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
		builder.setTitle(getString(R.string.dialog_register_title));
		builder.setMessage(getString(R.string.dialog_register_message));

		final EditText usernameEditText = new EditText(getActivity());
		usernameEditText.setHint(getString(R.string.dialog_register_enter_username_hint));
		usernameEditText.setMaxLines(1);
		usernameEditText.setSingleLine();
		usernameEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		usernameEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
			}

		});

		final EditText passwordEditText = new EditText(getActivity());
		passwordEditText.setHint(getString(R.string.dialog_register_enter_password_hint));
		passwordEditText.setMaxLines(1);
		passwordEditText.setSingleLine();
		passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passwordEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		passwordEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				passwordEditText.setError(null);
			}

		});

		final EditText emailEditText = new EditText(getActivity());
		emailEditText.setHint(getString(R.string.dialog_register_enter_email_hint));
		emailEditText.setMaxLines(1);
		emailEditText.setSingleLine();
		emailEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		emailEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				emailEditText.setError(null);
			}

		});

		final LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		int space = (int) AppUtils.convertDpToPixel(getActivity(), 16);
		layout.setPadding(space, 0, space, 0);

		layout.addView(usernameEditText);
		layout.addView(passwordEditText);
		layout.addView(emailEditText);

		builder.setView(layout);

		builder.setPositiveButton(getString(R.string.dialog_register_enter_email_hint), new DialogInterface.OnClickListener() {

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
						if (!AppUtils.checkInternetConnection(getActivity())) {
							Log.d(TAG, "There is NO network connected!");
							return;
						}

						usernameEditText.setError(null);
						passwordEditText.setError(null);
						emailEditText.setError(null);

						if (StringUtils.isBlank(usernameEditText.getText().toString())) {
							usernameEditText.setError(getString(R.string.dialog_register_field_required));
							return;
						}
						if (StringUtils.isBlank(passwordEditText.getText().toString())) {
							passwordEditText.setError(getString(R.string.dialog_register_field_required));
							return;
						}
						if (StringUtils.isBlank(emailEditText.getText().toString())) {
							emailEditText.setError(getString(R.string.dialog_register_field_required));
							return;
						}
						if (!AppUtils.validateEmail(emailEditText.getText().toString())) {
							emailEditText.setError(getString(R.string.dialog_register_email_not_valid));
							return;
						}

						final User user = new User();
						user.setUsername(usernameEditText.getText().toString());
						user.setPassword(SecurityUtils.hashSHA512Base64(passwordEditText.getText().toString()));
						user.setEmail(StringUtils.lowerCase(emailEditText.getText().toString()));
						user.setAddress(new AddressData());

						utilitiesRestClient.registerUser(user, new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(Throwable error, String content) {
								Log.d(TAG, "Registering user failed. [error= " + error + ", content=" + content + "]");
								AppUtils.showCenteredToast(getActivity(), getString(R.string.dialog_register_failed), Toast.LENGTH_LONG);
							}

							@Override
							public void onFinish() {
								positiveButton.setEnabled(true);
							}

							@Override
							public void onStart() {
								Log.d(TAG, "Registering user: " + user.toString());
								AppUtils.showCenteredToast(getActivity(), getString(R.string.dialog_register_in_progress),
								        Toast.LENGTH_SHORT);
								positiveButton.setEnabled(false);
							}

							@Override
							public void onSuccess(int statusCode, String content) {
								Log.d(TAG, "Registered user successfully.");
								AppUtils.showCenteredToast(getActivity(), getString(R.string.dialog_register_successful),
								        Toast.LENGTH_SHORT);

								SharedPreferencesWrapper.setUsername(user.getUsername());
								SharedPreferencesWrapper.setPasswordHash(user.getPassword());
								SharedPreferencesWrapper.setRememberUser(true);

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

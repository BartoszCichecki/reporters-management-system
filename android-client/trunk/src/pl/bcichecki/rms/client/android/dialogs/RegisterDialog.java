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
import android.text.InputType;
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
import pl.bcichecki.rms.client.android.model.impl.AddressData;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UtilitiesRestClient;
import pl.bcichecki.rms.client.android.utils.SecurityUtils;
import pl.bcichecki.rms.client.android.utils.UiUtils;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectResource;

/**
 * @author Bartosz Cichecki
 * 
 */
public class RegisterDialog extends RoboDialogFragment {

	protected static final String TAG = "RegisterDialog";

	@InjectResource(R.string.dialog_register_title)
	private String titleText;

	@InjectResource(R.string.dialog_register_message)
	private String messageText;

	@InjectResource(R.string.dialog_register_enter_username_hint)
	private String enterUsernameHintText;

	@InjectResource(R.string.dialog_register_enter_password_hint)
	private String enterPasswordHintText;

	@InjectResource(R.string.dialog_register_enter_email_hint)
	private String enterEmailHintText;

	@InjectResource(R.string.dialog_register_ok)
	private String okText;

	@InjectResource(R.string.dialog_register_field_required)
	private String fieldRequiredText;

	@InjectResource(R.string.dialog_register_email_not_valid)
	private String emailNotValid;

	@InjectResource(R.string.dialog_register_successful)
	private String registerSuccessful;

	@InjectResource(R.string.dialog_register_failed)
	private String registerFailed;

	@InjectResource(R.string.dialog_register_aborted)
	private String registerAborted;

	@InjectResource(R.string.dialog_register_in_progress)
	private String registerInProgress;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(titleText);
		builder.setMessage(messageText);

		final EditText usernameEditText = new EditText(getActivity());
		usernameEditText.setHint(enterUsernameHintText);
		usernameEditText.setMaxLines(1);
		usernameEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
			}

		});

		final EditText passwordEditText = new EditText(getActivity());
		passwordEditText.setHint(enterPasswordHintText);
		passwordEditText.setMaxLines(1);
		passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passwordEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				passwordEditText.setError(null);
			}

		});

		final EditText emailEditText = new EditText(getActivity());
		emailEditText.setHint(enterEmailHintText);
		emailEditText.setMaxLines(1);
		emailEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				emailEditText.setError(null);
			}

		});

		final LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		int space = (int) UiUtils.convertDpToPixel(getActivity(), 16);
		layout.setPadding(space, 0, space, 0);

		layout.addView(usernameEditText);
		layout.addView(passwordEditText);
		layout.addView(emailEditText);

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
						passwordEditText.setError(null);
						emailEditText.setError(null);

						if (StringUtils.isBlank(usernameEditText.getText().toString())) {
							usernameEditText.setError(fieldRequiredText);
							return;
						}
						if (StringUtils.isBlank(passwordEditText.getText().toString())) {
							passwordEditText.setError(fieldRequiredText);
							return;
						}
						if (StringUtils.isBlank(emailEditText.getText().toString())) {
							emailEditText.setError(fieldRequiredText);
							return;
						}
						if (!UiUtils.validateEmail(emailEditText.getText().toString())) {
							emailEditText.setError(emailNotValid);
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
								Toast.makeText(getActivity(), registerFailed, Toast.LENGTH_LONG).show();
							}

							@Override
							public void onFinish() {
								positiveButton.setEnabled(true);
							}

							@Override
							public void onStart() {
								Log.d(TAG, "Registering user: " + user.toString());
								Toast.makeText(getActivity(), registerInProgress, Toast.LENGTH_SHORT).show();
								positiveButton.setEnabled(false);
							}

							@Override
							public void onSuccess(int statusCode, String content) {
								Log.d(TAG, "Registered user successfully.");
								Toast.makeText(getActivity(), registerSuccessful, Toast.LENGTH_SHORT).show();

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
						if (utilitiesRestClient != null) {
							utilitiesRestClient.cancelRequests(getActivity(), true);
							Toast.makeText(getActivity(), registerAborted, Toast.LENGTH_LONG).show();
						}
						dialog.dismiss();
					}
				});

			}
		});

		return dialog;
	}
}

/**
 * Project:   rms-client-android
 * File:      LoginActivity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-12-2012
 */

package pl.bcichecki.rms.client.android.activities;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.dialogs.AboutDialog;
import pl.bcichecki.rms.client.android.dialogs.RegisterDialog;
import pl.bcichecki.rms.client.android.dialogs.RemindPasswordDialog;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.ProfileRestClient;
import pl.bcichecki.rms.client.android.utils.SecurityUtils;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * @author Bartosz Cichecki
 * 
 */
public class LoginActivity extends RoboFragmentActivity {

	private static final String TAG = "LoginActivity";

	private Context CONTEXT;

	private static final String FAKE_PASS = "*******";

	private static final int ACTIVITY_LOGIN_VIEW_ID = R.layout.activity_login;

	private static final int ACTIVITY_LOGIN_MENU_ID = R.menu.activity_login;

	private static final int FORGOT_PASSWORD_MENU_ITEM = R.id.activity_login_menu_forgot_password;

	private static final int REGISTER_MENU_ITEM = R.id.activity_login_menu_register;

	private static final int SETTINGS_MENU_ITEM = R.id.activity_login_menu_settings;

	private static final int ABOUT_MENU_ITEM = R.id.activity_login_menu_about;

	@InjectResource(R.string.activity_login_check_your_internet_connection)
	private String checkYourInternetConnectionText;

	@InjectResource(R.string.activity_login_error_field_required)
	private String errorFieldRequiredText;

	@InjectResource(R.string.activity_login_login_successful)
	private String loginSuccessfulText;

	@InjectResource(R.string.activity_login_unsuccessful_login_message_title)
	private String unsuccessfulLoginMessageTitleText;

	@InjectResource(R.string.activity_login_unsuccessful_login_message_content)
	private String unsuccessfulLoginMessageContentText;

	@InjectResource(R.string.general_unknown_error_message_title)
	private String unknownErrorMessageTitleText;

	@InjectResource(R.string.general_unknown_error_message_content)
	private String unknownErrorMessageConentText;

	@InjectView(R.id.activity_login_login_form)
	private View loginFormView;

	@InjectView(R.id.activity_login_login_progress)
	private View loginProgressView;

	@InjectView(R.id.activity_login_login_status_text_view)
	private TextView loginStatusTextView;

	@InjectView(R.id.activity_login_password_edit_text)
	private EditText passwordEditText;

	@InjectView(R.id.activity_login_remember_user_check_box)
	private CheckBox rememberUserCheckBox;

	@InjectView(R.id.activity_login_sign_in_button)
	private Button signInButton;

	@InjectView(R.id.activity_login_username_edit_text)
	private EditText usernameEditText;

	private Boolean rememberUser;

	private String username;

	private String password;

	private Boolean isPasswordHashed = false;

	private ProfileRestClient profileRestClient;

	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			Log.d(TAG, "There is a network connected.");
			return true;
		} else {
			Log.d(TAG, "There is NO network connected!");

			Toast checkYourInternetConnectionToast = Toast.makeText(getApplicationContext(), checkYourInternetConnectionText,
			        Toast.LENGTH_LONG);
			checkYourInternetConnectionToast.show();

			return false;
		}
	}

	public String getPassword() {
		return password;
	}

	public Boolean getRememberUser() {
		return rememberUser;
	}

	public String getUsername() {
		return username;
	}

	private void hashPassword() {
		if (!isPasswordHashed) {
			Log.d(TAG, "Hashing password...");
			password = SecurityUtils.hashSHA512Base64(passwordEditText.getText().toString());
			isPasswordHashed = true;
		} else {
			Log.d(TAG, "Will not hash. Password already hashed!");
		}

	}

	@Override
	public void onBackPressed() {
		if (loginProgressView.getVisibility() == View.GONE || profileRestClient == null) {
			super.onBackPressed();
		}
		if (loginProgressView.getVisibility() == View.VISIBLE && profileRestClient != null) {
			Log.d(TAG, "Cancelling all requests.");
			showProgress(false);
			profileRestClient.cancelRequests(CONTEXT, true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CONTEXT = this;

		setContentView(ACTIVITY_LOGIN_VIEW_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(ACTIVITY_LOGIN_MENU_ID, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == FORGOT_PASSWORD_MENU_ITEM) {
			Log.v(TAG, "Showing Remind Password Dialog...");

			RemindPasswordDialog remindPasswordDialog = new RemindPasswordDialog();
			remindPasswordDialog.show(getSupportFragmentManager(), TAG);
			return true;
		}
		if (item.getItemId() == REGISTER_MENU_ITEM) {
			Log.v(TAG, "Showing Register Dialog...");

			RegisterDialog registerDialog = new RegisterDialog();
			registerDialog.show(getSupportFragmentManager(), TAG);
			return true;
		}
		if (item.getItemId() == SETTINGS_MENU_ITEM) {
			Log.d(TAG, "Moving to Settings Activity...");

			Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsActivityIntent);
			return true;
		}
		if (item.getItemId() == ABOUT_MENU_ITEM) {
			Log.v(TAG, "Showing about dialog...");

			AboutDialog aboutDialog = new AboutDialog();
			aboutDialog.show(getSupportFragmentManager(), TAG);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		recoverCredentials();

		rememberUserCheckBox.setChecked(rememberUser);

		usernameEditText.setText(username);
		usernameEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
			}
		});

		if (isPasswordHashed) {
			Log.d(TAG, "Password is hashed setting field to a fake one.");
			passwordEditText.setText(FAKE_PASS);
		}
		passwordEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				usernameEditText.setError(null);
				if (isPasswordHashed) {
					Log.d(TAG, "Clearing password field, because it was filled with fake pass.");
					passwordEditText.setText(StringUtils.EMPTY);
					isPasswordHashed = false;
				}

			}
		});

		signInButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!checkInternetConnection()) {
					return;
				}
				if (!validateForm()) {
					return;
				}
				hashPassword();
				saveCredentials();
				performLogin();
			}

		});
	}

	protected void performLogin() {
		Log.d(TAG, "Performing login...");
		profileRestClient = new ProfileRestClient(CONTEXT, username, password, SharedPreferencesWrapper.getServerRealm(),
		        SharedPreferencesWrapper.getServerAddress(), SharedPreferencesWrapper.getServerPort(),
		        SharedPreferencesWrapper.getWebserviceContextPath());
		profileRestClient.getProfile(new GsonHttpResponseHandler<User>(false) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Getting profile failed! [error=" + error + ", content=" + content + "]");
				AlertDialog.Builder errorDialog = new AlertDialog.Builder(CONTEXT);
				errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						errorDialog.setTitle(unsuccessfulLoginMessageTitleText);
						errorDialog.setMessage(unsuccessfulLoginMessageContentText);
					} else {
						errorDialog.setTitle(unknownErrorMessageTitleText);
						errorDialog.setMessage(String.format(unknownErrorMessageConentText, (HttpResponseException) error));
					}
				} else {
					errorDialog.setTitle(unknownErrorMessageTitleText);
					errorDialog.setMessage(String.format(unknownErrorMessageConentText, error));
				}
				errorDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = errorDialog.show();
				TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
				messageText.setGravity(Gravity.CENTER);
			}

			@Override
			public void onFinish() {
				showProgress(false);
				Log.d(TAG, "Getting profile finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Getting profile started.");
				showProgress(true);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Success [statusCode=" + statusCode + ", content=" + content + "]");
				UserProfileHolder.setUserProfile(null);
				UserProfileHolder.setUsername(username);
				UserProfileHolder.setPassword(password);
				Toast.makeText(CONTEXT, loginSuccessfulText, Toast.LENGTH_SHORT).show();
				Intent mainActivityIntent = new Intent(CONTEXT, MainActivity.class);
				startActivity(mainActivityIntent);
			}

			@Override
			public void onSuccess(int statusCode, User jsonObject) {
				Log.d(TAG, "Success [statusCode=" + statusCode + ", jsonObject=" + jsonObject.toString() + "]");
				UserProfileHolder.setUserProfile(jsonObject);
				UserProfileHolder.setUsername(username);
				UserProfileHolder.setPassword(password);
				Toast.makeText(CONTEXT, loginSuccessfulText, Toast.LENGTH_SHORT).show();
				Intent mainActivityIntent = new Intent(CONTEXT, MainActivity.class);
				startActivity(mainActivityIntent);
			}
		});
	}

	private void recoverCredentials() {
		rememberUser = SharedPreferencesWrapper.getRememberUser();
		username = SharedPreferencesWrapper.getUsername();
		password = SharedPreferencesWrapper.getPasswordHash();

		isPasswordHashed = !StringUtils.isEmpty(password);

		Log.d(TAG, "Recovered credentials from Shared Preferences. Assuming saved password is hashed! [rememberUser=" + rememberUser
		        + ", username=" + username + ", password=" + password + ", isPasswordHashed=" + isPasswordHashed + "]");
		Log.d(TAG, "NOTE: Recovered credentials may be default!");
	}

	protected void saveCredentials() {
		Editor editor = SharedPreferencesWrapper.getSharedPreferences().edit();
		rememberUser = rememberUserCheckBox.isChecked();
		if (rememberUser) {
			username = usernameEditText.getText().toString();

			Log.d(TAG, "Saving credentials to Shared Preferences. Assuming password has been hashed already! [rememberUser=" + rememberUser
			        + ", username=" + username + ", password=" + password + "]");

			editor.putBoolean(SharedPreferencesWrapper.Keys.USER_REMEMBER_USER, rememberUser);
			editor.putString(SharedPreferencesWrapper.Keys.USER_USERNAME, username);
			editor.putString(SharedPreferencesWrapper.Keys.USER_PASSWORD_HASH, password);
		} else {
			Log.d(TAG, "User does not want to remember credentials. Removing them from Shared Preferences. Setting \'rememberUser\' to "
			        + rememberUser);

			editor.putBoolean(SharedPreferencesWrapper.Keys.USER_REMEMBER_USER, rememberUser);
			editor.remove(SharedPreferencesWrapper.Keys.USER_USERNAME);
			editor.remove(SharedPreferencesWrapper.Keys.USER_PASSWORD_HASH);
		}
		editor.commit();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberUser(Boolean rememberUser) {
		this.rememberUser = rememberUser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			loginProgressView.setVisibility(View.VISIBLE);
			loginProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

				@Override
				public void onAnimationEnd(Animator animation) {
					loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});

			loginFormView.setVisibility(View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

				@Override
				public void onAnimationEnd(Animator animation) {
					loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});
		} else {
			loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	private boolean validateForm() {
		usernameEditText.setError(null);
		passwordEditText.setError(null);

		boolean isUsernameBlank = StringUtils.isBlank(usernameEditText.getText().toString());

		if (isUsernameBlank) {
			Log.d(TAG, "Username field is empty!");

			usernameEditText.setError(errorFieldRequiredText);
			return false;
		}

		boolean isPasswordBlank = StringUtils.isBlank(passwordEditText.getText().toString());
		if (isPasswordBlank) {
			Log.d(TAG, "Password field is empty!");

			passwordEditText.setError(errorFieldRequiredText);
			return false;
		}

		Log.d(TAG, "Form looks ok.");

		return true;
	}

}

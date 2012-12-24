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

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.ProfileRestClient;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * @author Bartosz Cichecki
 * 
 */
public class LoginActivity extends RoboActivity {

	private static final String TAG = "LoginActivity";

	private static final int ACTIVITY_LOGIN_VIEW_ID = R.layout.activity_login;

	private static final int ACTIVITY_LOGIN_MENU_ID = R.menu.activity_login;

	private static final int FORGOT_PASSWORD_MENU_ITEM = R.id.menu_forgot_password;

	private static final int REGISTER_MENU_ITEM = R.id.menu_register;

	@InjectView(R.id.login_status)
	private View loginStatusView;

	@InjectView(R.id.login_status_text_view)
	private TextView loginStatusTextView;

	@InjectView(R.id.login_form)
	private View loginFormView;

	@InjectView(R.id.username_edit_text)
	private EditText usernameEditText;

	@InjectView(R.id.password_edit_text)
	private EditText passwordEditText;

	@InjectView(R.id.remember_user_check_box)
	private CheckBox rememberUserCheckBox;

	@Inject
	private ProfileRestClient profileRestClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(ACTIVITY_LOGIN_VIEW_ID);

		// TODO
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
			Log.d(TAG, "forgotPasswordMenuItem");
			return true;
		}
		if (item.getItemId() == REGISTER_MENU_ITEM) {
			Log.d(TAG, "registerMenuItem");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

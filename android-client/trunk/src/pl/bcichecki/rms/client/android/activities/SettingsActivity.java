/**
 * Project:   rms-client-android
 * File:      SettingsActivity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      26-12-2012
 */

package pl.bcichecki.rms.client.android.activities;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.utils.SecurityUtils;

public class SettingsActivity extends PreferenceActivity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class ServerPreferencesFragment extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_server);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class UserAccountPreferencesFragment extends PreferenceFragment {

		private EditTextPreference passwordPreference;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_user_account);

			passwordPreference = (EditTextPreference) findPreference(SharedPreferencesWrapper.Keys.USER_PASSWORD_HASH);
			passwordPreference.setOnPreferenceChangeListener(onPassHashPreferenceChangeListener);
			passwordPreference.setOnPreferenceClickListener(onPassHashedPreferenceClickListener);
		}
	}

	private static Preference.OnPreferenceClickListener onPassHashedPreferenceClickListener = new Preference.OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			EditTextPreference editTextPreference = (EditTextPreference) preference;
			editTextPreference.getEditText().setText(StringUtils.EMPTY);
			return false;
		}
	};

	private static Preference.OnPreferenceChangeListener onPassHashPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// Can't allow persisting entered value, because we need to hash it first!
			Log.d(TAG, "Hashing updated password...");
			String hashedPassword = SecurityUtils.hashSHA512Base64(newValue.toString());
			SharedPreferencesWrapper.setPasswordHash(hashedPassword);
			return false;
		}
	};

	private static final String TAG = "SettingsActivity";

	private static final int ANDROID_HOME_ID = android.R.id.home;

	private static boolean isSimplePreferences(Context context) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB || !isXLargeTablet(context);
	}

	private static boolean isXLargeTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	private EditTextPreference passwordPreference;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void goBack() {
		if (NavUtils.getParentActivityIntent(this) == null) {
			finish();
		} else {
			NavUtils.navigateUpFromSameTask(this);
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.preferences_headers, target);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
	}

	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == ANDROID_HOME_ID) {
			goBack();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			Log.d(TAG, "Will not create simple preferences.");
			return;
		}

		Log.d(TAG, "Creating simple preferences.");

		addPreferencesFromResource(R.xml.preferences_dummy);

		PreferenceCategory fakeHeader;

		fakeHeader = new PreferenceCategory(this);
		fakeHeader.setTitle(R.string.activity_settings_header_user_account);
		getPreferenceScreen().addPreference(fakeHeader);
		addPreferencesFromResource(R.xml.preferences_user_account);

		fakeHeader = new PreferenceCategory(this);
		fakeHeader.setTitle(R.string.activity_settings_header_server);
		getPreferenceScreen().addPreference(fakeHeader);
		addPreferencesFromResource(R.xml.preferences_server);

		passwordPreference = (EditTextPreference) findPreference(SharedPreferencesWrapper.Keys.USER_PASSWORD_HASH);
		passwordPreference.setOnPreferenceChangeListener(onPassHashPreferenceChangeListener);
		passwordPreference.setOnPreferenceClickListener(onPassHashedPreferenceClickListener);
	}

}

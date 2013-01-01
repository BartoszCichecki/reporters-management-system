/**
 * Project:   rms-client-android
 * File:      MainActivity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.activities;

import org.apache.commons.lang3.StringUtils;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.dialogs.AboutDialog;
import pl.bcichecki.rms.client.android.fragments.DevicesListFragment;
import pl.bcichecki.rms.client.android.fragments.EventsListFragment;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.listeners.ActivityAwareDialogInterfaceOnClickListener;
import pl.bcichecki.rms.client.android.listeners.MainActivityActionBarTabListener;

public class MainActivity extends FragmentActivity {

	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			switch (position) {
				case 0:
					fragment = new EventsListFragment();
					return fragment;
				case 1:
					fragment = new DevicesListFragment();
					return fragment;
				default:
					throw new IllegalArgumentException("Requested position " + position + " out of " + getCount());
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return StringUtils.upperCase(getString(R.string.activity_main_events_section_title));
				case 1:
					return StringUtils.upperCase(getString(R.string.activity_main_devices_section_title));
				default:
					throw new IllegalArgumentException("Requested position " + position + " out of " + getCount());
			}
		}
	}

	private static final String TAG = "MainActivity";

	private ActionBar actionBar;

	private SectionsPagerAdapter sectionsPagerAdapter;

	private ViewPager viewPager;

	private MainActivityActionBarTabListener mainActivityActionBarTabListener;

	private void logout() {
		AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
		logoutDialog.setTitle(R.string.activity_main_logout_dialog_title);
		logoutDialog.setMessage(R.string.activity_main_logout_dialog_message);
		logoutDialog.setPositiveButton(android.R.string.yes, new ActivityAwareDialogInterfaceOnClickListener(this) {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				UserProfileHolder.clear();
				NavUtils.navigateUpFromSameTask(getActivity());
			}
		});
		logoutDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});
		logoutDialog.show();
	}

	@Override
	public void onBackPressed() {
		logout();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);

		sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(sectionsPagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		mainActivityActionBarTabListener = new MainActivityActionBarTabListener(viewPager);

		for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
			Tab tab = actionBar.newTab();
			tab.setText(sectionsPagerAdapter.getPageTitle(i));
			tab.setTabListener(mainActivityActionBarTabListener);
			actionBar.addTab(tab);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home || item.getItemId() == R.id.activity_main_menu_logout) {
			Log.d(TAG, "Logout requested...");
			logout();
			return true;
		}
		if (item.getItemId() == R.id.activity_main_menu_settings) {
			Log.d(TAG, "Moving to Settings Activity...");

			Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsActivityIntent);
			return true;
		}
		if (item.getItemId() == R.id.activity_main_menu_about) {
			Log.v(TAG, "Showing about dialog...");

			AboutDialog aboutDialog = new AboutDialog();
			aboutDialog.show(getSupportFragmentManager(), TAG);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

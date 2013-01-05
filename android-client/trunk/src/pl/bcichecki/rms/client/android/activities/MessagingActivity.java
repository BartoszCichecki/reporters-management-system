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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.InboxMessagesListFragment;
import pl.bcichecki.rms.client.android.fragments.OutboxMessagesListFragment;
import pl.bcichecki.rms.client.android.listeners.MessagingActivityActionBarTabListener;

public class MessagingActivity extends FragmentActivity {

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
					fragment = new InboxMessagesListFragment();
					return fragment;
				case 1:
					fragment = new OutboxMessagesListFragment();
					return fragment;
				default:
					throw new IllegalArgumentException("Requested position " + position + " out of " + getCount());
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return StringUtils.upperCase(getString(R.string.activity_messaging_inbox_section_title));
				case 1:
					return StringUtils.upperCase(getString(R.string.activity_messaging_outbox_section_title));
				default:
					throw new IllegalArgumentException("Requested position " + position + " out of " + getCount());
			}
		}
	}

	private static final String TAG = "MessagingActivity";

	private ActionBar actionBar;

	private SectionsPagerAdapter sectionsPagerAdapter;

	private ViewPager viewPager;

	private MessagingActivityActionBarTabListener messagingActivityActionBarTabListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messaging);

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

		messagingActivityActionBarTabListener = new MessagingActivityActionBarTabListener(viewPager);

		for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
			Tab tab = actionBar.newTab();
			tab.setText(sectionsPagerAdapter.getPageTitle(i));
			tab.setTabListener(messagingActivityActionBarTabListener);
			actionBar.addTab(tab);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_messaging, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Log.d(TAG, "Up tapped...");
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

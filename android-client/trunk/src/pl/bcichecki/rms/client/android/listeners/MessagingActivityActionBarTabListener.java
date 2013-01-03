/**
 * Project:   rms-client-android
 * File:      MainActivityActionBarTabListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.listeners;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * @author Bartosz Cichecki
 * 
 */
public class MessagingActivityActionBarTabListener implements ActionBar.TabListener {

	private ViewPager viewPager;

	public MessagingActivityActionBarTabListener(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Nothing to do here...
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Nothing to do here...
	}

}

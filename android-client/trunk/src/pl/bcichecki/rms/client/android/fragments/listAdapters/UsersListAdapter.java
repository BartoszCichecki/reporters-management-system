/**
 * Project:   rms-client-android
 * File:      DevicesListAdapter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.UsersListComparator;
import pl.bcichecki.rms.client.android.model.impl.User;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UsersListAdapter extends ArrayAdapter<User> {

	private static final int FRAGMENT_USERS_LIST_ITEM = R.layout.fragment_users_list_item;

	private static final int FRAGMENT_USERS_LIST_ITEM_TITLE = R.id.fragment_users_list_item_title;

	private LayoutInflater layoutInflater;

	public UsersListAdapter(Context context, List<User> objects) {
		super(context, FRAGMENT_USERS_LIST_ITEM, FRAGMENT_USERS_LIST_ITEM_TITLE, objects);
	}

	protected LayoutInflater getLayoutInflater() {
		if (layoutInflater == null) {
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return layoutInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = getLayoutInflater().inflate(FRAGMENT_USERS_LIST_ITEM, parent, false);
		} else {
			view = convertView;
		}

		TextView userTitle = (TextView) view.findViewById(FRAGMENT_USERS_LIST_ITEM_TITLE);

		User user = getItem(position);

		if (user.getAddress() != null && user.getAddress().getFirstName() != null && user.getAddress().getLastName() != null) {
			userTitle.setText(user.getAddress().getFirstName() + " " + user.getAddress().getLastName());
		} else {
			userTitle.setText(user.getUsername());
		}

		return view;
	}

	public void refresh() {
		sort(new UsersListComparator());
		notifyDataSetChanged();
	}

}

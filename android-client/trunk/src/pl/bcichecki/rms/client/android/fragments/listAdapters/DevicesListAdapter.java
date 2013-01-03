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
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.DevicesListComparator;
import pl.bcichecki.rms.client.android.model.impl.Device;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesListAdapter extends ArrayAdapter<Device> {

	private static final int FRAGMENT_DEVICES_LIST_ITEM = R.layout.fragment_devices_list_item;

	private static final int FRAGMENT_DEVICES_LIST_ITEM_TITLE = R.id.fragment_devices_list_item_title;

	private LayoutInflater layoutInflater;

	public DevicesListAdapter(Context context, List<Device> objects) {
		super(context, FRAGMENT_DEVICES_LIST_ITEM, FRAGMENT_DEVICES_LIST_ITEM_TITLE, objects);
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
			view = getLayoutInflater().inflate(FRAGMENT_DEVICES_LIST_ITEM, parent, false);
		} else {
			view = convertView;
		}

		TextView eventTitle = (TextView) view.findViewById(FRAGMENT_DEVICES_LIST_ITEM_TITLE);

		Device event = getItem(position);

		eventTitle.setText(event.getName());

		return view;
	}

	public void refresh() {
		sort(new DevicesListComparator());
		notifyDataSetChanged();
	}

}

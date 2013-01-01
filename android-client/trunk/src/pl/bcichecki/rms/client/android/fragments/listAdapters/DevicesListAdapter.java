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

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.DevicesListComparator;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.DevicesRestClient;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesListAdapter extends ArrayAdapter<Device> {

	private static final String TAG = "DevicesListAdapter";

	private static final int FRAGMENT_DEVICES_LIST_ITEM = R.layout.fragment_devices_list_item;

	private static final int FRAGMENT_DEVICES_LIST_ITEM_TITLE = R.id.fragment_devices_list_item_title;

	private LayoutInflater layoutInflater;

	private DevicesRestClient devicesRestClient;

	public DevicesListAdapter(Context context, List<Device> objects, DevicesRestClient devicesRestClient) {
		super(context, FRAGMENT_DEVICES_LIST_ITEM, FRAGMENT_DEVICES_LIST_ITEM_TITLE, objects);
		this.devicesRestClient = devicesRestClient;
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

	@Override
	public void remove(final Device object) {
		if (devicesRestClient == null) {
			throw new IllegalStateException("EventsRestClient must not be null");
		}
		devicesRestClient.deleteDevice(object, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Removing event [event=" + object + "] failed. [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						Toast.makeText(getContext(), R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getContext(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getContext(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFinish() {
				Log.d(TAG, "Attempt to delete event finished. [event=" + object + "]");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to delete event. [event=" + object + "]");
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Attempt to delete event [event=" + object + "] succesful. Removing object locally");
				superRemove(object);
				refresh();
			}

		});
	}

	public void superRemove(Device object) {
		super.remove(object);
	}

}

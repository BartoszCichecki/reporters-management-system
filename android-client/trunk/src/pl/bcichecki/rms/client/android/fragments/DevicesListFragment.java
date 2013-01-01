/**
 * Project:   rms-client-android
 * File:      DevicesListFragment.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.DevicesListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.DevicesRestClient;
import pl.bcichecki.rms.client.android.utils.UiUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesListFragment extends ListFragment {

	private static final String TAG = "DevicesListFragment";

	private DevicesRestClient devicesRestClient;

	private List<Device> devices = new ArrayList<Device>();

	private DevicesListAdapter devicesListAdapter;

	private ProgressDialog progressDialog;

	private void cancelRequests() {
		if (devicesRestClient != null) {
			devicesRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private void downloadData() {
		if (!UiUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		devicesRestClient.getAllDevices(new GsonHttpResponseHandler<List<Device>>(new TypeToken<List<Device>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving devices failed. [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						Toast.makeText(getActivity(), R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFinish() {
				devicesListAdapter.refresh();
				dismissProgressDialog();
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving devices started.");
				showProgressDialog();
				devices.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<Device> object) {
				Log.d(TAG, "Retrieving devices successful. Retrieved " + object.size() + " objects.");
				devices.addAll(object);
			}
		});
	}

	protected void load() {
		devicesRestClient = new DevicesRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		downloadData();
		devicesListAdapter = new DevicesListAdapter(getActivity(), devices, devicesRestClient);
		setListAdapter(devicesListAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_devices_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_devices_list_menu_refresh) {
			Log.d(TAG, "Refreshing devices list...");
			downloadData();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		load();
		super.onStart();
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private void showProgressDialog() {
		progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(getString(R.string.fragment_devices_list_loading));
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancelRequests();
			}
		});
		progressDialog.show();
	}

}

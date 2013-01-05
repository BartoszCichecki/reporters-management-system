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

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.dialogs.DeviceDetailsDialog;
import pl.bcichecki.rms.client.android.fragments.listAdapters.DevicesListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.prettyPrinters.impl.DeviceTextPrettyPrinterPrinter;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.DevicesRestClient;
import pl.bcichecki.rms.client.android.utils.AppConstants;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesListFragment extends ListFragment {

	private static final String TAG = "DevicesListFragment";

	private List<Device> devices = new ArrayList<Device>();

	private DevicesListAdapter devicesListAdapter;

	private DevicesRestClient devicesRestClient;

	private void cancelRequests() {
		if (devicesRestClient != null) {
			devicesRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void downloadData() {
		Log.d(TAG, "Downloading devices list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
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
						AppUtils.showCenteredToast(getActivity(), R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
			}

			@Override
			public void onFinish() {
				devicesListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving devices finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving devices started.");
				showLoadingMessage();
				devices.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<Device> object) {
				Log.d(TAG, "Retrieving devices successful. Retrieved " + object.size() + " objects.");
				devices.addAll(object);
			}
		});
	}

	private Device getFirstCheckedItem() {
		if (getListView().getCheckedItemCount() != 1) {
			return null;
		}

		SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
		for (int i = 0; i < getListAdapter().getCount(); i++) {
			if (checkedItemPositions.get(i)) {
				return (Device) getListAdapter().getItem(i);
			}
		}
		return null;
	}

	private void hideLoadingMessage() {
		setListShown(true);
	}

	protected void load() {
		devicesRestClient = new DevicesRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		downloadData();

		devicesListAdapter = new DevicesListAdapter(getActivity(), devices);
		setListAdapter(devicesListAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_devices_list, menu);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		DeviceDetailsDialog deviceDetailsDialog = new DeviceDetailsDialog();
		deviceDetailsDialog.setDevice(devicesListAdapter.getItem(position));
		deviceDetailsDialog.show(getFragmentManager(), TAG);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_devices_list_menu_new) {
			// TODO performActionNew
			return true;
		}
		if (item.getItemId() == R.id.fragment_devices_list_menu_refresh) {
			downloadData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		load();
		setHasOptionsMenu(true);
		setUpActionModeOnListItems();
		setEmptyText(getString(R.string.fragment_devices_list_empty));
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private boolean performAction(MenuItem item) {
		Device selectedDevice = getFirstCheckedItem();
		if (selectedDevice == null) {
			Log.w(TAG, "Invalid selection. Aborting...");
			return false;
		}

		if (item.getItemId() == R.id.fragment_devices_list_context_menu_delete) {
			performActionDelete(item, selectedDevice);
			return true;
		}
		if (item.getItemId() == R.id.fragment_devices_list_context_menu_edit) {
			// TODO performActionEdit
			return true;
		}
		if (item.getItemId() == R.id.fragment_devices_list_context_menu_share) {
			performActionShare(item, selectedDevice);
			return true;
		}
		return false;
	}

	private void performActionDelete(MenuItem item, final Device selectedDevice) {
		devicesRestClient.deleteDevice(selectedDevice, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Removing event " + selectedDevice + " failed. [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						AppUtils.showCenteredToast(getActivity(), R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
			}

			@Override
			public void onFinish() {
				Log.d(TAG, "Attempt to delete device finished. " + selectedDevice);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to delete device. " + selectedDevice);
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_devices_list_delete_successful, Toast.LENGTH_LONG);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Attempt to delete event " + selectedDevice + " succesful. Removing object locally and refreshing view...");
				devicesListAdapter.remove(selectedDevice);
				devicesListAdapter.refresh();
			}
		});
	}

	private void performActionShare(MenuItem item, final Device selectedDevice) {
		ShareActionProvider shareActionProvider = (ShareActionProvider) item.getActionProvider();
		if (shareActionProvider != null) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType(AppConstants.CONTENT_TEXT_PLAIN);
			intent.putExtra(Intent.EXTRA_TEXT, new DeviceTextPrettyPrinterPrinter(getActivity()).print(selectedDevice));
			shareActionProvider.setShareHistoryFileName(null);
			shareActionProvider.setShareIntent(intent);

			Log.d(TAG, "Device " + selectedDevice + " was succesfully shared.");
		}
	}

	private void setUpActionModeOnListItems() {
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				return performAction(item);
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.fragment_devices_list_context, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				if (checked) {
					for (int i = 0; i < getListView().getCheckedItemPositions().size(); i++) {
						if (i != position) {
							getListView().setItemChecked(i, false);
						}
					}
				}
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
		});
	}

	private void showLoadingMessage() {
		setListShown(false);
	}

}

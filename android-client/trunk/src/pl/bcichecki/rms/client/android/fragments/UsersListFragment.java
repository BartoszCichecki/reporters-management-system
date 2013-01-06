/**
 * Project:   rms-client-android
 * File:      UsersListFragment.java
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

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.dialogs.UserDetailsDialog;
import pl.bcichecki.rms.client.android.fragments.listAdapters.UsersListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UsersRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UsersListFragment extends ListFragment {

	private static final String TAG = "UsersListFragment";

	private List<User> users = new ArrayList<User>();

	private UsersListAdapter usersListAdapter;

	private UsersRestClient usersRestClient;

	private void cancelRequests() {
		if (usersRestClient != null) {
			usersRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void downloadData() {
		Log.d(TAG, "Downloading users list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		usersRestClient.getAllUsers(new GsonHttpResponseHandler<List<User>>(new TypeToken<List<User>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving users failed. [error=" + error + ", content=" + content + "]");
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
				usersListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving users finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving users started.");
				showLoadingMessage();
				users.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<User> object) {
				Log.d(TAG, "Retrieving users successful. Retrieved " + object.size() + " objects.");
				users.addAll(object);
			}
		});
	}

	private void hideLoadingMessage() {
		setListShown(true);
	}

	protected void load() {
		usersRestClient = new UsersRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		downloadData();

		usersListAdapter = new UsersListAdapter(getActivity(), users);
		setListAdapter(usersListAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_users_list, menu);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		UserDetailsDialog deviceDetailsDialog = new UserDetailsDialog();
		deviceDetailsDialog.setUser(usersListAdapter.getItem(position));
		deviceDetailsDialog.show(getFragmentManager(), TAG);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_users_list_menu_refresh) {
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
		setEmptyText(getString(R.string.fragment_users_list_empty));
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private void showLoadingMessage() {
		setListShown(false);
	}

}

/**
 * Project:   rms-client-android
 * File:      EventsListFragment.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
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
import pl.bcichecki.rms.client.android.fragments.listAdapters.EventsListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.EventsRestClient;
import pl.bcichecki.rms.client.android.utils.UiUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsListFragment extends ListFragment {

	private static final String TAG = "EventsListFragment";

	private static final int DAYS_BACK = -3;

	private static final int DAYS_AHEAD = 14;

	private EventsRestClient eventsRestClient;

	private List<Event> events = new ArrayList<Event>();

	private EventsListAdapter eventsListAdapter;

	private ProgressDialog progressDialog;

	private void cancelRequests() {
		if (eventsRestClient != null) {
			eventsRestClient.cancelRequests(getActivity(), true);
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

		final Date now = new Date();
		final Date from = DateUtils.round(DateUtils.addDays(now, DAYS_BACK - 1), Calendar.DAY_OF_MONTH);
		final Date till = DateUtils.round(DateUtils.addDays(now, DAYS_AHEAD + 1), Calendar.DAY_OF_MONTH);

		eventsRestClient.getAllEvents(from, till, new GsonHttpResponseHandler<List<Event>>(new TypeToken<List<Event>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving events from " + from.toString() + " till " + till.toString() + " failed. [error=" + error
				        + ", content=" + content + "]");
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
				eventsListAdapter.refresh();
				dismissProgressDialog();
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving events from " + from.toString() + " till " + till.toString() + " started.");
				showProgressDialog();
				events.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<Event> object) {
				Log.d(TAG,
				        "Retrieving events from " + from.toString() + " till " + till.toString() + " successful. Retrieved "
				                + object.size() + " objects.");
				events.addAll(object);
			}
		});
	}

	protected void load() {
		eventsRestClient = new EventsRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		downloadData();
		eventsListAdapter = new EventsListAdapter(getActivity(), events, eventsRestClient);
		setListAdapter(eventsListAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_events_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_events_list_menu_refresh) {
			Log.d(TAG, "Refreshing events list...");
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
		progressDialog.setMessage(getString(R.string.fragment_events_list_loading));
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancelRequests();
			}
		});
		progressDialog.show();
	}

}

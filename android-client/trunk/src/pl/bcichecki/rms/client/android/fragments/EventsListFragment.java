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
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.EventsListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.model.utils.PojoUtils;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.EventsRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsListFragment extends ListFragment {

	private static final int DAYS_AHEAD = 14;

	private static final int DAYS_BACK = -3;

	private static final String TAG = "EventsListFragment";

	private List<Event> events = new ArrayList<Event>();

	private EventsListAdapter eventsListAdapter;

	private EventsRestClient eventsRestClient;

	private ProgressDialog progressDialog;

	private boolean showArchivedEvents = false;

	private void cancelRequests() {
		if (eventsRestClient != null) {
			eventsRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	private void downloadArchivedData() {
		if (!showArchivedEvents) {
			return;
		}

		Log.d(TAG, "Downloading events list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		final Date now = new Date();
		final Date from = DateUtils.round(DateUtils.addDays(now, DAYS_BACK - 1), Calendar.DAY_OF_MONTH);
		final Date till = DateUtils.round(DateUtils.addDays(now, DAYS_AHEAD + 1), Calendar.DAY_OF_MONTH);

		eventsRestClient.getAllArchivedEvents(from, till, new GsonHttpResponseHandler<List<Event>>(new TypeToken<List<Event>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving archived events from " + from.toString() + " till " + till.toString() + " failed. [error=" + error
				        + ", content=" + content + "]");
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
				eventsListAdapter.refresh();
				dismissProgressDialog();
				Log.d(TAG, "Retrieving archived events finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving archived events from " + from.toString() + " till " + till.toString() + " started.");
				showProgressDialog();
			}

			@Override
			public void onSuccess(int statusCode, List<Event> object) {
				Log.d(TAG, "Retrieving archived events from " + from.toString() + " till " + till.toString() + " successful. Retrieved "
				        + object.size() + " objects.");
				events.addAll(object);
			}
		});
	}

	private void downloadData() {
		Log.d(TAG, "Downloading events list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
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
				eventsListAdapter.refresh();
				dismissProgressDialog();
				Log.d(TAG, "Retrieving events finished.");
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

	private Event getCheckedItem() {
		if (getListView().getCheckedItemCount() != 1) {
			return null;
		}
		for (int i = 0; i < getListView().getCheckedItemPositions().size(); i++) {
			if (getListView().getCheckedItemPositions().get(i)) {
				return (Event) getListAdapter().getItem(i);
			}
		}
		return null;
	}

	protected void load() {
		eventsRestClient = new EventsRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		downloadData();
		downloadArchivedData();

		eventsListAdapter = new EventsListAdapter(getActivity(), events);
		setListAdapter(eventsListAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_events_list, menu);

		MenuItem showArchivedMenuItem = menu.findItem(R.id.fragment_events_list_menu_show_archived);
		showArchivedMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				item.setChecked(!item.isChecked());
				showArchivedEvents = item.isChecked();

				downloadData();
				downloadArchivedData();

				return false;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_events_list_menu_new) {
			// TODO performActionNew
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_menu_refresh) {
			downloadData();
			downloadArchivedData();
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
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private boolean performAction(ActionMode mode, MenuItem item) {
		Event selectedEvent = getCheckedItem();
		if (selectedEvent == null) {
			Log.w(TAG, "Invalid selection. Aborting...");
			return false;
		}

		if (item.getItemId() == R.id.fragment_events_list_context_menu_archive) {
			performActionArchive(mode, item, selectedEvent);
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_context_menu_delete) {
			performActionDelete(mode, item, selectedEvent);
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_context_menu_edit) {
			// TODO performActionEdit
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_context_menu_lock) {
			performActionLock(mode, item, selectedEvent);
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_context_menu_share) {
			performActionShare(mode, item, selectedEvent);
			return true;
		}
		if (item.getItemId() == R.id.fragment_events_list_context_menu_unlock) {
			performActionUnlock(mode, item, selectedEvent);
			return true;
		}
		return false;
	}

	private void performActionArchive(final ActionMode mode, MenuItem item, final Event selectedEvent) {
		final Event selectedEventCopy = PojoUtils.createDefensiveCopy(selectedEvent);
		selectedEventCopy.setArchived(true);

		eventsRestClient.updateMyEvent(selectedEventCopy, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Archive event failed. [error=" + error + ", content=" + content + "]");
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
				dismissProgressDialog();
				Log.d(TAG, "Attempt archive Event " + selectedEvent + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting archive Event " + selectedEvent + " started.");
				showProgressDialog();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				selectedEvent.setArchived(true);
				Log.d(TAG, "Event archive succesfull. Refreshing view.");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_events_list_archive_successful, Toast.LENGTH_LONG);
				eventsListAdapter.refresh();
				mode.finish();
			}
		});
	}

	private void performActionDelete(final ActionMode mode, MenuItem item, final Event selectedEvent) {
		eventsRestClient.deleteMyEvent(selectedEvent, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Removing event " + selectedEvent + " failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt to delete event finished. " + selectedEvent);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to delete event. " + selectedEvent);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				eventsListAdapter.remove(selectedEvent);
				Log.d(TAG, "Attempt to delete event " + selectedEvent + " succesful. Removing object locally and refreshing view...");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_events_list_delete_successful, Toast.LENGTH_LONG);
				eventsListAdapter.refresh();
				mode.finish();
			}
		});
	}

	private void performActionLock(final ActionMode mode, MenuItem item, final Event selectedEvent) {
		final Event selectedEventCopy = PojoUtils.createDefensiveCopy(selectedEvent);
		selectedEventCopy.setLocked(true);

		eventsRestClient.updateMyEvent(selectedEventCopy, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Locking event " + selectedEvent + " failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt to lock event finished. " + selectedEvent);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to lock event. " + selectedEvent);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				selectedEvent.setLocked(true);
				Log.d(TAG, "Attempt to lock event " + selectedEvent + " succesful. Refreshing view...");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_events_list_lock_successful, Toast.LENGTH_LONG);
				eventsListAdapter.refresh();
				mode.finish();
			}
		});
	}

	private void performActionShare(ActionMode mode, MenuItem item, Event selectedEvent) {
		ShareActionProvider shareActionProvider = (ShareActionProvider) item.getActionProvider();
		if (shareActionProvider != null) {
			Intent intent = new Intent();
			intent.setType("text/plain");
			intent.putExtra(android.content.Intent.EXTRA_TEXT, selectedEvent.toString());
			shareActionProvider.setShareIntent(intent);
			mode.finish();

			Log.d(TAG, "Event " + selectedEvent + " was succesfully shared.");
		}
	}

	private void performActionUnlock(final ActionMode mode, MenuItem item, final Event selectedEvent) {
		final Event selectedEventCopy = PojoUtils.createDefensiveCopy(selectedEvent);
		selectedEventCopy.setLocked(false);

		eventsRestClient.updateMyEvent(selectedEventCopy, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Unlocking event " + selectedEvent + " failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt to unlock event finished. " + selectedEvent);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to unlock event. " + selectedEvent);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				selectedEvent.setLocked(false);
				Log.d(TAG, "Attempt to unlock event " + selectedEvent + " succesful. Refreshing view...");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_events_list_unlock_successful, Toast.LENGTH_LONG);
				eventsListAdapter.refresh();
				mode.finish();
			}
		});
	}

	private void setUpActionModeOnListItems() {
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				return performAction(mode, item);
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.fragment_events_list_context, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				if (checked) {
					verifyMenuItems(mode.getMenu());
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

			private void verifyMenuItems(Menu menu) {
				Event checkedEvent = getCheckedItem();
				menu.findItem(R.id.fragment_events_list_context_menu_archive).setEnabled(!checkedEvent.isArchived());
				menu.findItem(R.id.fragment_events_list_context_menu_edit).setEnabled(
				        !checkedEvent.isArchived() || !checkedEvent.isLocked());
				menu.findItem(R.id.fragment_events_list_context_menu_lock).setEnabled(!checkedEvent.isLocked());
				menu.findItem(R.id.fragment_events_list_context_menu_share).setEnabled(!checkedEvent.isArchived());
				menu.findItem(R.id.fragment_events_list_context_menu_unlock).setEnabled(checkedEvent.isLocked());
			}
		});
	}

	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.fragment_events_list_loading));
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancelRequests();
				}
			});
		}
		progressDialog.show();
	}

}

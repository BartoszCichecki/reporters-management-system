/**
 * Project:   rms-client-android
 * File:      InboxMessagesListFragment.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.InboxMessagesListAdapter;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.MessagesRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class InboxMessagesListFragment extends ListFragment {

	private static final String TAG = "InboxMessagesListFragment";

	private List<Message> inboxMessages = new ArrayList<Message>();

	private InboxMessagesListAdapter inboxMessagesListAdapter;

	private MessagesRestClient messagesRestClient;

	private boolean showArchivedEvents = false;

	private void cancelRequests() {
		if (messagesRestClient != null) {
			messagesRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void downloadArchivedData() {
		if (!showArchivedEvents) {
			return;
		}

		Log.d(TAG, "Downloading inbox messages list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		messagesRestClient.getAllArchivedInboxMessages(new GsonHttpResponseHandler<List<Message>>(new TypeToken<List<Message>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving archived inbox messages failed. [error=" + error + ", content=" + content + "]");
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
				inboxMessagesListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving archived inbox messages finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving archived inbox messages started.");
				showLoadingMessage();
			}

			@Override
			public void onSuccess(int statusCode, List<Message> object) {
				Log.d(TAG, "Retrieving archived inbox messages successful. Retrieved " + object.size() + " objects.");
				inboxMessages.addAll(object);
			}
		});
	}

	private void downloadData() {
		Log.d(TAG, "Downloading inbox messages list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		messagesRestClient.getAllInboxMessages(new GsonHttpResponseHandler<List<Message>>(new TypeToken<List<Message>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving inbox messages failed. [error=" + error + ", content=" + content + "]");
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
				inboxMessagesListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving inbox messages finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving inbox messages started.");
				showLoadingMessage();
				inboxMessages.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<Message> object) {
				Log.d(TAG, "Retrieving inbox messages successful. Retrieved " + object.size() + " objects.");
				inboxMessages.addAll(object);
			}
		});
	}

	private Message getFirstCheckedItem() {
		if (getListView().getCheckedItemCount() != 1) {
			return null;
		}

		SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
		for (int i = 0; i < getListAdapter().getCount(); i++) {
			if (checkedItemPositions.get(i)) {
				return (Message) getListAdapter().getItem(i);
			}
		}
		return null;
	}

	private void hideLoadingMessage() {
		setListShown(true);
	}

	protected void load() {
		messagesRestClient = new MessagesRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		downloadData();
		downloadArchivedData();

		inboxMessagesListAdapter = new InboxMessagesListAdapter(getActivity(), inboxMessages);
		setListAdapter(inboxMessagesListAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_inbox_messages_list, menu);

		MenuItem showArchivedMenuItem = menu.findItem(R.id.fragment_inbox_messages_list_menu_show_archived);
		showArchivedMenuItem.setChecked(showArchivedEvents);
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
		if (item.getItemId() == R.id.fragment_inbox_messages_list_menu_refresh) {
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
		setEmptyText(getString(R.string.fragment_inbox_messages_list_empty));
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private boolean performAction(ActionMode mode, MenuItem item) {
		Message selectedEvent = getFirstCheckedItem();
		if (selectedEvent == null) {
			Log.w(TAG, "Invalid selection. Aborting...");
			return false;
		}

		if (item.getItemId() == R.id.fragment_inbox_messages_list_context_menu_archive) {
			performActionArchive(mode, item, selectedEvent);
			return true;
		}
		if (item.getItemId() == R.id.fragment_inbox_messages_list_context_menu_delete) {
			performActionDelete(mode, item, selectedEvent);
			return true;
		}
		return false;
	}

	private void performActionArchive(final ActionMode mode, MenuItem item, final Message selectedEvent) {
		messagesRestClient.archiveMessage(selectedEvent, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Archive inbox messages failed. [error=" + error + ", content=" + content + "]");
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
				hideLoadingMessage();
				Log.d(TAG, "Attempt archive inbox message " + selectedEvent + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting archive inbox message " + selectedEvent + " started.");
				showLoadingMessage();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Inbox message archive succesfull. Refreshing view.");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_inbox_messages_list_archive_successful, Toast.LENGTH_LONG);
				downloadData();
				downloadArchivedData();
			}
		});
	}

	private void performActionDelete(final ActionMode mode, MenuItem item, final Message selectedEvent) {
		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Removing inbox message " + selectedEvent + " failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt to delete inbox message finished. " + selectedEvent);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to delete inbox message. " + selectedEvent);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Attempt to delete inbox message " + selectedEvent
				        + " succesful. Removing object locally and refreshing view...");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_inbox_messages_list_delete_successful, Toast.LENGTH_LONG);
				downloadData();
				downloadArchivedData();
			}
		};

		if (!selectedEvent.isArchivedBySender()) {
			messagesRestClient.deleteInboxMessage(selectedEvent, handler);
		} else {
			messagesRestClient.deleteArchivedInboxMessage(selectedEvent, handler);
		}
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
				inflater.inflate(R.menu.fragment_inbox_messages_list_context, menu);
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
				Message checkedMessage = getFirstCheckedItem();
				menu.findItem(R.id.fragment_inbox_messages_list_context_menu_archive).setVisible(!checkedMessage.isArchivedBySender());
			}
		});
	}

	private void showLoadingMessage() {
		setListShown(false);
	}
}

/**
 * Project:   rms-client-android
 * File:      OutboxMessagesListFragment.java
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
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.dialogs.OutboxMessageDetailsDialog;
import pl.bcichecki.rms.client.android.fragments.listAdapters.OutboxMessagesListAdapter;
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
public class OutboxMessagesListFragment extends ListFragment {

	private static final String TAG = "OutboxMessagesListFragment";

	private List<Message> outboxMessages = new ArrayList<Message>();

	private OutboxMessagesListAdapter outboxMessagesListAdapter;

	private MessagesRestClient messagesRestClient;

	private boolean showArchivedMessages = false;

	private void cancelRequests() {
		if (messagesRestClient != null) {
			messagesRestClient.cancelRequests(getActivity(), true);
		}
	}

	private void downloadArchivedData() {
		if (!showArchivedMessages) {
			return;
		}

		Log.d(TAG, "Downloading outbox messages list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		messagesRestClient.getAllArchivedOutboxMessages(new GsonHttpResponseHandler<List<Message>>(new TypeToken<List<Message>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving archived outbox messages failed. [error=" + error + ", content=" + content + "]");
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
				outboxMessagesListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving archived outbox messages finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving archived outbox messages started.");
				showLoadingMessage();
			}

			@Override
			public void onSuccess(int statusCode, List<Message> object) {
				Log.d(TAG, "Retrieving archived outbox messages successful. Retrieved " + object.size() + " objects.");
				outboxMessages.addAll(object);
			}
		});
	}

	private void downloadData() {
		Log.d(TAG, "Downloading outbox messages list...");

		if (!AppUtils.checkInternetConnection(getActivity())) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		messagesRestClient.getAllOutboxMessages(new GsonHttpResponseHandler<List<Message>>(new TypeToken<List<Message>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving outbox messages failed. [error=" + error + ", content=" + content + "]");
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
				outboxMessagesListAdapter.refresh();
				hideLoadingMessage();
				Log.d(TAG, "Retrieving outbox messages finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving outbox messages started.");
				showLoadingMessage();
				outboxMessages.clear();
			}

			@Override
			public void onSuccess(int statusCode, List<Message> object) {
				Log.d(TAG, "Retrieving outbox messages successful. Retrieved " + object.size() + " objects.");
				outboxMessages.addAll(object);
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

		outboxMessagesListAdapter = new OutboxMessagesListAdapter(getActivity(), outboxMessages);
		setListAdapter(outboxMessagesListAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.fragment_outbox_messages_list, menu);

		MenuItem showArchivedMenuItem = menu.findItem(R.id.fragment_outbox_messages_list_menu_show_archived);
		showArchivedMenuItem.setChecked(showArchivedMessages);
		showArchivedMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				item.setChecked(!item.isChecked());
				showArchivedMessages = item.isChecked();

				downloadData();
				downloadArchivedData();

				return false;
			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		OutboxMessageDetailsDialog outboxMessageDetailsDialog = new OutboxMessageDetailsDialog();
		outboxMessageDetailsDialog.setMessage(outboxMessagesListAdapter.getItem(position));
		outboxMessageDetailsDialog.show(getFragmentManager(), TAG);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.fragment_outbox_messages_list_menu_refresh) {
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
		setEmptyText(getString(R.string.fragment_outbox_messages_list_empty));
	}

	@Override
	public void onStop() {
		cancelRequests();
		super.onStop();
	}

	private boolean performAction(ActionMode mode, MenuItem item) {
		Message selectedMessage = getFirstCheckedItem();
		if (selectedMessage == null) {
			Log.w(TAG, "Invalid selection. Aborting...");
			return false;
		}

		if (item.getItemId() == R.id.fragment_outbox_messages_list_context_menu_archive) {
			performActionArchive(mode, item, selectedMessage);
			return true;
		}
		if (item.getItemId() == R.id.fragment_outbox_messages_list_context_menu_delete) {
			performActionDelete(mode, item, selectedMessage);
			return true;
		}
		return false;
	}

	private void performActionArchive(final ActionMode mode, MenuItem item, final Message selectedMessage) {
		messagesRestClient.archiveMessage(selectedMessage, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Archive outbox messages failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt archive outbox message " + selectedMessage + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting archive outbox message " + selectedMessage + " started.");
				showLoadingMessage();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Outbox message archive succesfull. Refreshing view.");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_outbox_messages_list_archive_successful, Toast.LENGTH_LONG);
				downloadData();
				downloadArchivedData();
				mode.finish();
			}
		});
	}

	private void performActionDelete(final ActionMode mode, MenuItem item, final Message selectedMessage) {
		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Removing outbox message " + selectedMessage + " failed. [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Attempt to delete outbox message finished. " + selectedMessage);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Attempting to delete outbox message. " + selectedMessage);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Attempt to delete outbox message " + selectedMessage
				        + " succesful. Removing object locally and refreshing view...");
				AppUtils.showCenteredToast(getActivity(), R.string.fragment_outbox_messages_list_delete_successful, Toast.LENGTH_LONG);
				downloadData();
				downloadArchivedData();
				mode.finish();
			}
		};

		if (!selectedMessage.isArchivedBySender()) {
			messagesRestClient.deleteOutboxMessage(selectedMessage, handler);
		} else {
			messagesRestClient.deleteArchivedOutboxMessage(selectedMessage, handler);
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
				inflater.inflate(R.menu.fragment_outbox_messages_list_context, menu);
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
				menu.findItem(R.id.fragment_outbox_messages_list_context_menu_archive).setVisible(!checkedMessage.isArchivedBySender());
			}
		});
	}

	private void showLoadingMessage() {
		setListShown(false);
	}
}

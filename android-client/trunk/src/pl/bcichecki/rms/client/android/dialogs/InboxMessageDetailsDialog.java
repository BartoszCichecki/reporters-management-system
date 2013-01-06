/**
 * Project:   rms-client-android
 * File:      InboxMessageDetailsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import java.util.HashSet;
import java.util.Locale;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.activities.NewMessageActivity;
import pl.bcichecki.rms.client.android.fragments.listAdapters.InboxMessagesListAdapter;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.model.impl.MessageRecipent;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.MessagesRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class InboxMessageDetailsDialog extends DialogFragment {

	private Message message;

	private MessagesRestClient messagesRestClient;

	private InboxMessagesListAdapter inboxMessagesListAdapter;

	private Context context;

	private static final String MESSAGE_EXTRA = "MESSAGE_EXTRA";

	private static final int REQUEST_CODE_SEND_MESSAGE = 417;

	private void markRead() {
		if (!message.isReadByRecipent(UserProfileHolder.getUserProfile())) {
			messagesRestClient.markMessageRead(message, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable error, String content) {
					Log.d(getTag(), "Could  not mark message read [error=" + error + ", content=" + content + "]");
					if (error instanceof HttpResponseException) {
						if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
							AppUtils.showCenteredToast(context, R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
						} else {
							AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
						}
					} else {
						AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				}

				@Override
				public void onFinish() {
					Log.d(getTag(), "Attempt markRead on " + message + " finished.");
				}

				@Override
				public void onStart() {
					Log.d(getTag(), "Attempting markRead on " + message);
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					Log.d(getTag(), "Message marked read. Refreshing inboxMessagesListAdapter...");
					refreshInboxMessage(message);
				}

			});
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (message == null) {
			throw new IllegalStateException("Message has not been set!");
		}
		if (messagesRestClient == null) {
			throw new IllegalStateException("MessagesRestClient has not been set!");
		}
		if (inboxMessagesListAdapter == null) {
			throw new IllegalStateException("InboxMessagesListAdapter has not been set!");
		}

		context = getActivity();

		markRead();

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(R.string.dialog_inbox_message_details_title);
		dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_inbox_message_details, null));
		dialogBuilder.setNeutralButton(R.string.dialog_inbox_message_details_reply, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				MessageRecipent messageRecipent = new MessageRecipent();
				messageRecipent.setRecipent(message.getSender());
				HashSet<MessageRecipent> messageRecipents = new HashSet<MessageRecipent>();
				messageRecipents.add(messageRecipent);
				Message replyMessage = new Message();
				replyMessage.setSubject(getString(R.string.dialog_inbox_message_details_re) + ": " + message.getSubject());
				replyMessage.setRecipents(messageRecipents);

				Log.d(getTag(), "Starting reply message activity...");
				Intent newMessageIntent = new Intent(context, NewMessageActivity.class);
				newMessageIntent.putExtra(MESSAGE_EXTRA, replyMessage);
				startActivityForResult(newMessageIntent, REQUEST_CODE_SEND_MESSAGE);
			}
		});
		dialogBuilder.setPositiveButton(R.string.general_close, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		AlertDialog dialog = dialogBuilder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				TextView subjectTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_inbox_message_details_subject_text);
				subjectTextView.setText(message.getSubject());

				StringBuilder sender = new StringBuilder();
				if (message.getSender().getAddress() != null && message.getSender().getAddress().getFirstName() != null
				        && message.getSender().getAddress().getLastName() != null) {
					sender.append(message.getSender().getAddress().getFirstName()).append(" ")
					        .append(message.getSender().getAddress().getLastName());
				} else {
					sender.append(message.getSender().getUsername());
				}

				TextView senderTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_inbox_message_details_sender_text);
				senderTextView.setText(sender.toString());

				TextView receivedDataTextView = (TextView) ((AlertDialog) dialog)
				        .findViewById(R.id.dialog_inbox_message_details_received_date_text);
				receivedDataTextView.setText(AppUtils.getFormattedDateAsString(message.getDate(), Locale.getDefault()));

				TextView contentTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_inbox_message_details_content_text);
				contentTextView.setText(message.getContent());
			}
		});

		return dialog;
	}

	private void refreshInboxMessage(final Message message) {
		final String messageId = message.getId();

		AsyncHttpResponseHandler handler = new GsonHttpResponseHandler<Message>(new TypeToken<Message>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(getTag(), "Could not get message id " + messageId + " [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						AppUtils.showCenteredToast(context, R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
			}

			@Override
			public void onFinish() {
				Log.d(getTag(), "Getting message id " + messageId + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(getTag(), "Started getting message id " + messageId);
			}

			@Override
			public void onSuccess(int statusCode, Message retrievedMessage) {
				Log.d(getTag(), "Got message id " + messageId + ". Refreshing inboxMessagesListAdapter...");
				inboxMessagesListAdapter.remove(message);
				inboxMessagesListAdapter.add(retrievedMessage);
				inboxMessagesListAdapter.refresh();
			}
		};

		if (!message.isArchivedByRecipent(UserProfileHolder.getUserProfile())) {
			messagesRestClient.getInboxMessage(message, handler);
		} else {
			messagesRestClient.getArchivedInboxMessage(message, handler);
		}
	}

	public void setInboxMessagesListAdapter(InboxMessagesListAdapter inboxMessagesListAdapter) {
		this.inboxMessagesListAdapter = inboxMessagesListAdapter;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMessagesRestClient(MessagesRestClient messagesRestClient) {
		this.messagesRestClient = messagesRestClient;
	}

}

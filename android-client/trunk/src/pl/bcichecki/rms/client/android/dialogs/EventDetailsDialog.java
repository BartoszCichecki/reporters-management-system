/**
 * Project:   rms-client-android
 * File:      EventDetailsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import java.util.Iterator;
import java.util.Locale;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.EventsListAdapter;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.EventsRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventDetailsDialog extends DialogFragment {

	private Event event;

	private EventsRestClient eventsRestClient;

	private EventsListAdapter eventsListAdapter;

	private Context context;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (event == null) {
			throw new IllegalStateException("Event has not been set!");
		}
		if (eventsRestClient == null) {
			throw new IllegalStateException("EventsRestClient has not been set!");
		}
		if (eventsListAdapter == null) {
			throw new IllegalStateException("EventsListAdapter has not been set!");
		}

		context = getActivity();

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(getString(R.string.dialog_event_details_title, event.getTitle()));
		dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_event_details, null));

		final boolean isUserSignedUp = event.isUserSignedUp(UserProfileHolder.getUserProfile());
		dialogBuilder.setNeutralButton(isUserSignedUp ? R.string.dialog_event_details_sign_out : R.string.dialog_event_details_sign_up,
		        new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
				        signUp(!isUserSignedUp);
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

				TextView descriptionTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_description_text);
				TextView devicesTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_devices_text);
				TextView endsTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_ends_text);
				TextView participantsTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_participants_text);
				TextView startsTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_starts_text);
				TextView titleTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_event_details_title_text);

				titleTextView.setText(event.getTitle());
				startsTextView.setText(AppUtils.getFormattedDateAsString(event.getStartDate(), Locale.getDefault()));
				endsTextView.setText(AppUtils.getFormattedDateAsString(event.getEndDate(), Locale.getDefault()));
				descriptionTextView.setText(event.getDescription());

				if (event.getParticipants() != null && event.getParticipants().size() > 0) {
					StringBuilder participants = new StringBuilder();
					int counter = 1;
					for (Iterator<User> participantIterator = event.getParticipants().iterator(); participantIterator.hasNext();) {
						User participant = participantIterator.next();
						participants.append(counter).append(". ");
						if (participant.getAddress() != null && participant.getAddress().getFirstName() != null
						        && participant.getAddress().getLastName() != null) {
							participants.append(participant.getAddress().getFirstName()).append(" ")
							        .append(participant.getAddress().getLastName());
						} else {
							participants.append(participant.getUsername());
						}
						if (participantIterator.hasNext()) {
							participants.append("\n");
							counter++;
						}
					}
					participantsTextView.setText(participants);
				} else {
					participantsTextView.setText(R.string.dialog_event_details_no_participants);
				}

				if (event.getDevices() != null && event.getDevices().size() > 0) {
					StringBuilder devices = new StringBuilder();
					int counter = 1;
					for (Iterator<Device> deviceIterator = event.getDevices().iterator(); deviceIterator.hasNext();) {
						Device device = deviceIterator.next();
						devices.append(counter).append(". ");
						devices.append(device.getName());
						if (deviceIterator.hasNext()) {
							devices.append("\n");
							counter++;
						}
					}
					devicesTextView.setText(devices);
				} else {
					devicesTextView.setText(R.string.dialog_event_details_no_devices);
				}

			}
		});

		return dialog;
	}

	private void refreshEvent(final Event event) {
		final String eventId = event.getId();

		eventsRestClient.getEvent(event, new GsonHttpResponseHandler<Event>(new TypeToken<Event>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(getTag(), "Could not get event id " + eventId + " [error=" + error + ", content=" + content + "]");
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
				Log.d(getTag(), "Getting event id " + eventId + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(getTag(), "Started getting event id " + eventId);
			}

			@Override
			public void onSuccess(int statusCode, Event retrievedEvent) {
				Log.d(getTag(), "Got event id " + eventId + ". Refreshing inboxMessagesListAdapter...");
				eventsListAdapter.remove(event);
				eventsListAdapter.add(retrievedEvent);
				eventsListAdapter.refresh();
			}
		});
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setEventsListAdapter(EventsListAdapter eventsListAdapter) {
		this.eventsListAdapter = eventsListAdapter;
	}

	public void setEventsRestClient(EventsRestClient messagesRestClient) {
		eventsRestClient = messagesRestClient;
	}

	private void signUp(final boolean signUp) {
		eventsRestClient.signUpForEvent(event, signUp, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(getTag(), "Could change sign up state to " + signUp + " [error=" + error + ", content=" + content + "]");
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
				Log.d(getTag(), "Changed sign up state to " + signUp + " finished.");
			}

			@Override
			public void onStart() {
				Log.d(getTag(), "Changed sign up state to " + signUp);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(getTag(), "Changed sign up state to " + signUp + ". Refreshing this event...");
				refreshEvent(event);
			}

		});
	}

}

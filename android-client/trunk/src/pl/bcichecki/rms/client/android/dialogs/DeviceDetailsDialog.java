/**
 * Project:   rms-client-android
 * File:      DeviceDetailsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.EventsRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DeviceDetailsDialog extends DialogFragment {

	private static final int DAYS_AHEAD = 14;

	private static final int DAYS_BACK = -3;

	private Device device;

	private EventsRestClient eventsRestClient;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (device == null) {
			throw new IllegalStateException("Device has not been set!");
		}
		eventsRestClient = new EventsRestClient(getActivity(), UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(getString(R.string.dialog_device_details_title, device.getName()));
		dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_device_details, null));
		dialogBuilder.setNeutralButton(R.string.dialog_device_details_btn_show_reservations, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				final Date now = new Date();
				final Date from = DateUtils.round(DateUtils.addDays(now, DAYS_BACK - 1), Calendar.DAY_OF_MONTH);
				final Date till = DateUtils.round(DateUtils.addDays(now, DAYS_AHEAD + 1), Calendar.DAY_OF_MONTH);

				final FragmentManager fragmentManager = getFragmentManager();

				Log.d(getTag(), "Retrieving device's events for " + device);

				eventsRestClient.getDevicesEvents(device, from, till, new GsonHttpResponseHandler<List<Event>>(
				        new TypeToken<List<Event>>() {
				        }.getType(), true) {

					@Override
					public void onFailure(Throwable error, String content) {
						Log.d(getTag(), "Retrieving device's events from " + from.toString() + " till " + till.toString()
						        + " failed. [error=" + error + ", content=" + content + "]");
						if (error instanceof HttpResponseException) {
							if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
								AppUtils.showCenteredToast(getActivity(), R.string.general_unathorized_error_message_title,
								        Toast.LENGTH_LONG);
							} else {
								AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
							}
						} else {
							AppUtils.showCenteredToast(getActivity(), R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
						}
					}

					@Override
					public void onFinish() {
						Log.d(getTag(), "Retrieving device's events finished.");
					}

					@Override
					public void onStart() {
						Log.d(getTag(), "Retrieving device's events from " + from.toString() + " till " + till.toString() + " started.");
					}

					@Override
					public void onSuccess(int statusCode, List<Event> events) {
						Log.d(getTag(), "Retrieving device's events from " + from.toString() + " till " + till.toString()
						        + " successful. Retrieved " + events.size() + " objects.");
						DeviceReservationsDialog deviceReservationsDialog = new DeviceReservationsDialog();
						deviceReservationsDialog.setDevice(device);
						deviceReservationsDialog.setEvents(events);
						deviceReservationsDialog.show(fragmentManager, getTag());
					}

				});
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
				TextView nameTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_device_details_name_text);
				nameTextView.setText(device.getName());

				TextView descriptionTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_device_details_description_text);
				descriptionTextView.setText(device.getName());
			}
		});

		return dialog;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}

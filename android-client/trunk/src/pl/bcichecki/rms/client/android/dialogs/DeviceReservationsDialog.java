/**
 * Project:   rms-client-android
 * File:      DeviceReservationsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.BulletSpan;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DeviceReservationsDialog extends DialogFragment {

	private Device device;

	private List<Event> events;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (device == null) {
			throw new IllegalStateException("Device has not been set!");
		}
		if (events == null) {
			throw new IllegalStateException("Events have not been set!");
		}

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(getString(R.string.dialog_device_reservations_title, device.getName()));

		if (events.size() < 1) {
			dialogBuilder.setMessage(R.string.dialog_device_reservations_empty);
		} else {
			StringBuilder reservations = new StringBuilder();
			for (Event event : events) {
				StringBuilder sb = new StringBuilder();
				sb.append(AppUtils.getFormattedDateAsString(event.getStartDate(), Locale.getDefault()));
				sb.append(" - ");
				sb.append(AppUtils.getFormattedDateAsString(event.getEndDate(), Locale.getDefault()));
				sb.append(" (").append(event.getTitle()).append(")");

				SpannableString reservation = new SpannableString(sb.toString());
				reservation.setSpan(new BulletSpan(), 0, reservation.length(), 0);

				reservations.append(reservation);
			}
			dialogBuilder.setMessage(reservations);
		}

		dialogBuilder.setPositiveButton(R.string.general_back, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DeviceDetailsDialog deviceDetailsDialog = new DeviceDetailsDialog();
				deviceDetailsDialog.setDevice(device);
				deviceDetailsDialog.show(getFragmentManager(), getTag());
			}
		});

		return dialogBuilder.create();
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}

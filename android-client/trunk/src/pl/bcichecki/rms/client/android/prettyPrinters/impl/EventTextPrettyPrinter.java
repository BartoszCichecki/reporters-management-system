/**
 * Project:   rms-client-android
 * File:      EventTextPrettyPrinter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.prettyPrinters.impl;

import java.util.Locale;

import android.content.Context;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.model.impl.EventType;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.prettyPrinters.AbstractPrettyPrinter;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventTextPrettyPrinter extends AbstractPrettyPrinter<Event> {

	public EventTextPrettyPrinter(Context context) {
		super(context);
	}

	@Override
	public String print(Event event) {
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.pretty_printer_event_teaser));
		sb.append(getContext().getString(R.string.pretty_printer_event_title));
		sb.append(event.getTitle());
		sb.append("\n");
		sb.append(getContext().getString(R.string.pretty_printer_event_type));
		if (event.getType().equals(EventType.INTERVIEW)) {
			sb.append(getContext().getString(R.string.pretty_printer_event_type_interview));
		}
		if (event.getType().equals(EventType.MEETING)) {
			sb.append(getContext().getString(R.string.pretty_printer_event_type_meeting));
		}
		sb.append("\n");
		sb.append(getContext().getString(R.string.pretty_printer_event_starts_at));
		sb.append(AppUtils.getFormattedDateAsString(event.getStartDate(), Locale.getDefault()));
		sb.append(getContext().getString(R.string.pretty_printer_event_ends_at));
		sb.append(AppUtils.getFormattedDateAsString(event.getEndDate(), Locale.getDefault()));
		sb.append("\n");
		sb.append(getContext().getString(R.string.pretty_printer_event_description));
		sb.append(event.getDescription());
		sb.append("\n");
		if (event.getParticipants() != null && event.getParticipants().size() > 0) {
			sb.append(getContext().getString(R.string.pretty_printer_event_participants));
			for (User participant : event.getParticipants()) {
				sb.append(" - ");
				if (participant.getAddress() != null && participant.getAddress().getFirstName() != null
				        && participant.getAddress().getLastName() != null) {
					sb.append(participant.getAddress().getFirstName()).append(" ").append(participant.getAddress().getLastName());
				} else {
					sb.append(participant.getUsername());
				}
				sb.append("\n");
			}
		}
		if (event.getDevices() != null && event.getDevices().size() > 0) {
			sb.append(getContext().getString(R.string.pretty_printer_event_devices));
			for (Device device : event.getDevices()) {
				sb.append(" - ").append(device.getName()).append("\n");
			}
		}
		sb.append(getContext().getString(R.string.pretty_printer_event_footer));
		return sb.toString();
	}

}

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

import java.util.List;

import android.content.Context;

import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.prettyPrinters.AbstractPrettyPrinter;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventTextPrettyPrinter extends AbstractPrettyPrinter<Event> {

	@SuppressWarnings("unused")
	private List<Message> messages;

	public EventTextPrettyPrinter(Context context) {
		super(context);
	}

	public EventTextPrettyPrinter addAditionalData(List<Message> messages) {
		this.messages = messages;
		return this;
	}

	@Override
	public String print(Event event) {
		StringBuilder sb = new StringBuilder();
		// TODO print
		return sb.toString();
	}

}

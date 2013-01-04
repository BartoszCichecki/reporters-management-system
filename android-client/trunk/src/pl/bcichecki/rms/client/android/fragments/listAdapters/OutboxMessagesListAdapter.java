/**
 * Project:   rms-client-android
 * File:      OutboxMessagesListAdapter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.OutboxMessagesListComparator;
import pl.bcichecki.rms.client.android.model.impl.AddressData;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.model.impl.MessageRecipent;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class OutboxMessagesListAdapter extends ArrayAdapter<Message> {

	private static final int FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM = R.layout.fragment_outbox_messages_list_item;

	private static final int FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_INDICATORS_ARCHIVED = R.id.fragment_outbox_messages_list_item_indicators_archived;

	private static final int FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_SENT = R.id.fragment_outbox_messages_list_item_sent;

	private static final int FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_SUBJECT = R.id.fragment_outbox_messages_list_item_subject;

	private static final int FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_TO = R.id.fragment_outbox_messages_list_item_to;

	private LayoutInflater layoutInflater;

	public OutboxMessagesListAdapter(Context context, List<Message> objects) {
		super(context, FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM, FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_SUBJECT, objects);
	}

	protected LayoutInflater getLayoutInflater() {
		if (layoutInflater == null) {
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return layoutInflater;
	}

	private String getRecipents(Message event) {
		StringBuilder recipents = new StringBuilder();
		for (Iterator<MessageRecipent> iterator = event.getRecipents().iterator(); iterator.hasNext();) {
			MessageRecipent recipent = iterator.next();
			AddressData address = recipent.getRecipent().getAddress();
			if (address != null && !StringUtils.isBlank(address.getFirstName()) && !StringUtils.isBlank(address.getLastName())) {
				recipents.append(address.getFirstName()).append(" ").append(address.getLastName());
			} else {
				recipents.append(recipent.getRecipent().getUsername());
			}
			if (iterator.hasNext()) {
				recipents.append(",");
			}
			recipents.append(" ");
		}
		return recipents.toString();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = getLayoutInflater().inflate(FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM, parent, false);
		} else {
			view = convertView;
		}

		TextView eventSent = (TextView) view.findViewById(FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_SENT);
		TextView eventSubject = (TextView) view.findViewById(FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_SUBJECT);
		TextView eventTo = (TextView) view.findViewById(FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_TO);

		ImageView archivedIndicator = (ImageView) view.findViewById(FRAGMENT_OUTBOX_MESSAGES_LIST_ITEM_INDICATORS_ARCHIVED);

		Locale locale = getContext().getResources().getConfiguration().locale;

		Message event = getItem(position);

		eventSent.setText(AppUtils.getFormattedDateAsString(event.getDate(), locale));
		eventSubject.setText(event.getSubject());
		eventTo.setText(getRecipents(event));

		archivedIndicator.setVisibility(event.isArchivedBySender() ? View.VISIBLE : View.INVISIBLE);

		return view;
	}

	public void refresh() {
		sort(new OutboxMessagesListComparator());
		notifyDataSetChanged();
	}

}

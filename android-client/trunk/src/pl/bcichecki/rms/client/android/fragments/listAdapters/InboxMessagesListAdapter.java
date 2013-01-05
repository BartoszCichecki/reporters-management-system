/**
 * Project:   rms-client-android
 * File:      InboxMessagesListAdapter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.InboxMessagesListComparator;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class InboxMessagesListAdapter extends ArrayAdapter<Message> {

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM = R.layout.fragment_inbox_messages_list_item;

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM_INDICATORS_ARCHIVED = R.id.fragment_inbox_messages_list_item_indicators_archived;

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM_INDICATORS_UNREAD = R.id.fragment_inbox_messages_list_item_indicators_unread;

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SENDER = R.id.fragment_inbox_messages_list_item_sender;

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SENT_ON = R.id.fragment_inbox_messages_list_item_sent_on;

	private static final int FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SUBJECT = R.id.fragment_inbox_messages_list_item_subject;

	private LayoutInflater layoutInflater;

	public InboxMessagesListAdapter(Context context, List<Message> objects) {
		super(context, FRAGMENT_INBOX_MESSAGES_LIST_ITEM, FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SUBJECT, objects);
	}

	protected LayoutInflater getLayoutInflater() {
		if (layoutInflater == null) {
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return layoutInflater;
	}

	private String getSender(User sender) {
		if (sender.getAddress() != null && sender.getAddress().getFirstName() != null && sender.getAddress().getLastName() != null) {
			return sender.getAddress().getFirstName() + " " + sender.getAddress().getLastName();
		} else {
			return sender.getUsername();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = getLayoutInflater().inflate(FRAGMENT_INBOX_MESSAGES_LIST_ITEM, parent, false);
		} else {
			view = convertView;
		}

		TextView eventSentOn = (TextView) view.findViewById(FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SENT_ON);
		TextView eventSubject = (TextView) view.findViewById(FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SUBJECT);
		TextView eventSender = (TextView) view.findViewById(FRAGMENT_INBOX_MESSAGES_LIST_ITEM_SENDER);

		ImageView unreadIndicator = (ImageView) view.findViewById(FRAGMENT_INBOX_MESSAGES_LIST_ITEM_INDICATORS_UNREAD);
		ImageView archivedIndicator = (ImageView) view.findViewById(FRAGMENT_INBOX_MESSAGES_LIST_ITEM_INDICATORS_ARCHIVED);

		Locale locale = getContext().getResources().getConfiguration().locale;

		Message event = getItem(position);

		boolean isReadByRecipent = event.isReadByRecipent(UserProfileHolder.getUserProfile());
		if (isReadByRecipent) {
			eventSubject.setText(event.getSubject());
		} else {
			SpannableString eventSubjectSpannableString = new SpannableString(event.getSubject());
			eventSubjectSpannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, eventSubjectSpannableString.length(), 0);
			eventSubject.setText(eventSubjectSpannableString);
		}
		eventSentOn.setText(AppUtils.getFormattedDateAsString(event.getDate(), locale));
		eventSender.setText(getSender(event.getSender()));

		unreadIndicator.setVisibility(!isReadByRecipent ? View.VISIBLE : View.INVISIBLE);
		archivedIndicator.setVisibility(event.isArchivedByRecipent(UserProfileHolder.getUserProfile()) ? View.VISIBLE : View.INVISIBLE);

		return view;
	}

	public void refresh() {
		sort(new InboxMessagesListComparator());
		notifyDataSetChanged();
	}

}

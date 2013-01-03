/**
 * Project:   rms-client-android
 * File:      EventsListAdapter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      30-12-2012
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters;

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
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.EventsListComparator;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsListAdapter extends ArrayAdapter<Event> {

	private static final int FRAGMENT_EVENTS_LIST_ITEM = R.layout.fragment_events_list_item;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_TITLE = R.id.fragment_events_list_item_title;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_FROM = R.id.fragment_events_list_item_till;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_TILL = R.id.fragment_events_list_item_from;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_ARCHIVED = R.id.fragment_events_list_item_indicators_archived;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_LOCKED = R.id.fragment_events_list_item_indicators_locked;

	private static final int FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_OWNER = R.id.fragment_events_list_item_indicators_owner;

	private LayoutInflater layoutInflater;

	public EventsListAdapter(Context context, List<Event> objects) {
		super(context, FRAGMENT_EVENTS_LIST_ITEM, FRAGMENT_EVENTS_LIST_ITEM_TITLE, objects);
	}

	protected LayoutInflater getLayoutInflater() {
		if (layoutInflater == null) {
			layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return layoutInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = getLayoutInflater().inflate(FRAGMENT_EVENTS_LIST_ITEM, parent, false);
		} else {
			view = convertView;
		}

		TextView eventTitle = (TextView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_TITLE);
		TextView eventFrom = (TextView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_FROM);
		TextView eventTill = (TextView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_TILL);

		ImageView archivedIndicator = (ImageView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_ARCHIVED);
		ImageView lockedIndicator = (ImageView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_LOCKED);
		ImageView ownerIndicator = (ImageView) view.findViewById(FRAGMENT_EVENTS_LIST_ITEM_INDICATORS_OWNER);

		Locale locale = getContext().getResources().getConfiguration().locale;

		Event event = getItem(position);

		eventTitle.setText(event.getTitle());
		eventFrom.setText(AppUtils.getFormattedDateAsString(event.getStartDate(), locale));
		eventTill.setText(AppUtils.getFormattedDateAsString(event.getEndDate(), locale));

		archivedIndicator.setVisibility(event.isArchived() ? View.VISIBLE : View.INVISIBLE);
		lockedIndicator.setVisibility(event.isLocked() ? View.VISIBLE : View.INVISIBLE);
		ownerIndicator.setVisibility(UserProfileHolder.getUserProfile().getId()
		        .equals(StringUtils.defaultString(event.getCreationUserId())) ? View.VISIBLE : View.INVISIBLE);

		return view;
	}

	public void refresh() {
		sort(new EventsListComparator());
		notifyDataSetChanged();
	}

}

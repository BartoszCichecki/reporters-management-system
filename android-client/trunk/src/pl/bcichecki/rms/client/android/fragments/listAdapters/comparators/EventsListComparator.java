/**
 * Project:   rms-client-android
 * File:      EventsListComparator.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      01-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters.comparators;

import java.util.Comparator;

import pl.bcichecki.rms.client.android.model.impl.Event;

/**
 * @author Bartosz Cichecki
 * 
 */
public class EventsListComparator implements Comparator<Event> {

	@Override
	public int compare(Event lhs, Event rhs) {
		if (lhs.equals(rhs)) {
			return 0;
		}
		return lhs.getStartDate().compareTo(rhs.getStartDate());
	}

}

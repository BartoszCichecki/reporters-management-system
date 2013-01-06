/**
 * Project:   rms-client-android
 * File:      OutboxMessagesListComparator.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters.comparators;

import java.util.Comparator;

import pl.bcichecki.rms.client.android.model.impl.Message;

/**
 * @author Bartosz Cichecki
 * 
 */
public class OutboxMessagesListComparator implements Comparator<Message> {

	@Override
	public int compare(Message lhs, Message rhs) {
		if (lhs.isArchivedBySender() && !rhs.isArchivedBySender()) {
			return 1;
		}
		if (!lhs.isArchivedBySender() && rhs.isArchivedBySender()) {
			return -1;
		}

		if (lhs.equals(rhs)) {
			return 0;
		}
		return rhs.getDate().compareTo(lhs.getDate());
	}

}

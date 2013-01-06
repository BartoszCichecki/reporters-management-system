/**
 * Project:   rms-client-android
 * File:      UsersListComparator.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      06-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters.comparators;

import java.util.Comparator;

import pl.bcichecki.rms.client.android.model.impl.User;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UsersListComparator implements Comparator<User> {

	@Override
	public int compare(User lhs, User rhs) {
		if (lhs.getAddress() != null && lhs.getAddress().getFirstName() != null && lhs.getAddress().getLastName() != null
		        && rhs.getAddress() != null && rhs.getAddress().getFirstName() != null && rhs.getAddress().getLastName() != null) {
			String lhsVal = lhs.getAddress().getFirstName() + " " + lhs.getAddress().getLastName();
			String rhsVal = rhs.getAddress().getFirstName() + " " + rhs.getAddress().getLastName();
			return lhsVal.compareTo(rhsVal);
		} else {
			return lhs.getUsername().compareTo(rhs.getUsername());
		}
	}

}

/**
 * Project:   rms-client-android
 * File:      DevicesListComparator.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      01-01-2013
 */

package pl.bcichecki.rms.client.android.fragments.listAdapters.comparators;

import java.util.Comparator;

import pl.bcichecki.rms.client.android.model.impl.Device;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DevicesListComparator implements Comparator<Device> {

	@Override
	public int compare(Device lhs, Device rhs) {
		if (lhs.equals(rhs)) {
			return 0;
		}
		return lhs.getName().compareTo(rhs.getName());
	}

}

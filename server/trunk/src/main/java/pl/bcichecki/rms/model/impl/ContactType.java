/**
 * Project:   rms-server
 * File:      ContactType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Cichecki
 */
public enum ContactType {

	PHONE("PHONE"), FAX("FAX"), EMAIL("EMAIL"), FACEBOOK("FACEBOOK"), SKYPE("SKYPE");

	public static ContactType fromString(String value) {
		for (ContactType contactType : ContactType.values()) {
			if (contactType.toString().equalsIgnoreCase(StringUtils.defaultString(value))) {
				return contactType;
			}
		}
		throw new IllegalArgumentException();
	}

	private String value;

	private ContactType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

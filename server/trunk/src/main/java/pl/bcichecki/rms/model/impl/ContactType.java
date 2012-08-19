/**
 * Project:   Reporters Management System - Server
 * File:      ContactType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum ContactType {

	PHONE("phone"), FAX("fax"), EMAIL("email"), FACEBOOK("facebook"), SKYPE("skype");

	private String value;

	private ContactType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

/**
 * Project:   Reporters Management System - Server
 * File:      PrivilegeType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum PrivilegeType {

	ADD_USER("addUser");

	private String value;

	private PrivilegeType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

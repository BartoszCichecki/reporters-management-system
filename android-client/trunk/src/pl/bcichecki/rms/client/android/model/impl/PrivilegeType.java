/**
 * Project:   rms-client-android
 * File:      PrivilegeType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Cichecki
 */
public enum PrivilegeType {

	CLEAN_UP_MESSAGES("CLEAN_UP_MESSAGES"),

	CREATE_DEVICES("CREATE_DEVICES"),

	CREATE_ROLES("CREATE_ROLES"),

	CREATE_USERS("CREATE_USERS"),

	DELETE_ACCESS_HISTORY("DELETE_ACCESS_HISTORY"),

	DELETE_DEVICES("DELETE_DEVICES"),

	DELETE_EVENTS("DELETE_EVENTS"),

	DELETE_MESSAGES("DELETE_MESSAGES"),

	DELETE_MY_EVENTS("DELETE_MY_EVENTS"),

	DELETE_ROLES("DELETE_ROLES"),

	DELETE_USERS("DELETE_USERS"),

	LOOK_UP_DEVICE_EVENTS("LOOK_UP_DEVICE_EVENTS"),

	LOOK_UP_ROLE_USERS("LOOK_UP_ROLE_USERS"),

	LOOK_UP_USER_ROLES("LOOK_UP_USER_ROLES"),

	MANAGE_ACCESS_HISTORY("MANAGE_ACCESS_HISTORY"),

	MANAGE_DEVICES("MANAGE_DEVICES"),

	MANAGE_EVENTS("MANAGE_EVENTS"),

	MANAGE_MESSAGES("MANAGE_MESSAGES"),

	MANAGE_PROFILE("MANAGE_PROFILE"),

	MANAGE_ROLES("MANAGE_ROLES"),

	MANAGE_USERS("MANAGE_USERS"),

	MARK_DELETE_DEVICES("MARK_DELETE_DEVICES"),

	MARK_DELETE_MESSAGES("MARK_DELETE_MESSAGES"),

	MARK_DELETE_MY_EVENTS("MARK_DELETE_MY_EVENTS"),

	POST_EVENTS("POST_EVENTS"),

	POST_EVENTS_TO_WAITING_ROOM("POST_EVENTS_TO_WAITING_ROOM"),

	SEND_MESSAGES("SEND_MESSAGES"),

	SIGN_UP_FOR_EVENT("SIGN_UP_FOR_EVENT"),

	UPDATE_DEVICES("UPDATE_DEVICES"),

	UPDATE_MY_EVENTS("UPDATE_MY_EVENTS"),

	UPDATE_OUTGOING_MESSAGES("UPDATE_OUTGOING_MESSAGES"),

	UPDATE_ROLES("UPDATE_ROLES"),

	UPDATE_USERS("UPDATE_USERS"),

	VIEW_ACCESS_HISTORY("VIEW_ACCESS_HISTORY"),

	VIEW_DEVICES("VIEW_DEVICES"),

	VIEW_EVENTS("VIEW_EVENTS"),

	VIEW_MESSAGES("VIEW_MESSAGES"),

	VIEW_PROFILE("VIEW_PROFILE"),

	VIEW_ROLES("VIEW_ROLES"),

	VIEW_USERS("VIEW_USERS");

	public static PrivilegeType fromString(String value) {
		for (PrivilegeType privilegeType : PrivilegeType.values()) {
			if (privilegeType.toString().equalsIgnoreCase(StringUtils.defaultString(value))) {
				return privilegeType;
			}
		}
		throw new IllegalArgumentException();
	}

	private String value;

	private PrivilegeType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

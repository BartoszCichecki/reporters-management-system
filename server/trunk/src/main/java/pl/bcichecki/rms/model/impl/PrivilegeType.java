/**
 * Project:   rms-server
 * File:      PrivilegeType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Cichecki
 */
public enum PrivilegeType {

	CLEAN_UP_MESSAGES, CREATE_DEVICES, CREATE_ROLES, CREATE_USERS, DELETE_ACCESS_HISTORY, DELETE_DEVICES, DELETE_EVENTS, DELETE_MESSAGES,
	DELETE_MY_EVENTS, DELETE_ROLES, DELETE_USERS, LOOK_UP_DEVICE_EVENTS, LOOK_UP_ROLE_USERS, LOOK_UP_USER_ROLES, MANAGE_ACCESS_HISTORY,
	MANAGE_DEVICES, MANAGE_EVENTS, MANAGE_MESSAGES, MANAGE_PROFILE, MANAGE_ROLES, MANAGE_USERS, MARK_DELETE_DEVICES, MARK_DELETE_MESSAGES,
	MARK_DELETE_MY_EVENTS, POST_EVENTS, POST_EVENTS_TO_WAITING_ROOM, SEND_MESSAGES, SIGN_UP_FOR_EVENT, UPDATE_DEVICES, UPDATE_MY_EVENTS,
	UPDATE_OUTGOING_MESSAGES, UPDATE_ROLES, UPDATE_USERS, VIEW_ACCESS_HISTORY, VIEW_DEVICES, VIEW_EVENTS, VIEW_MESSAGES, VIEW_PROFILE,
	VIEW_ROLES, VIEW_USERS;

	public static PrivilegeType fromString(String value) {
		for (PrivilegeType privilegeType : PrivilegeType.values()) {
			if (privilegeType.toString().equalsIgnoreCase(StringUtils.defaultString(value))) {
				return privilegeType;
			}
		}
		throw new IllegalArgumentException();
	}

	private String value;

	private PrivilegeType() {
		/*
		 * HACK This is done to maintain this class and PrivilegeUtils.Values more easily. It is very convenient to persist enums with JPA,
		 * but if we want to use Spring Security in authorization mechanism which requires values to be constants we must use static fields.
		 * With this hack we are sure that this enum and PrivilegeUtils.Value are synchronized. If you think about this solution it is not
		 * that bad after all - these values won't change, so if it runs for the first time, it will run every time and you don't have to
		 * synchronize their values.
		 */
		try {
			java.lang.reflect.Field field = pl.bcichecki.rms.utils.PrivilegeUtils.Values.class.getDeclaredField(name());
			field.setAccessible(true);
			value = (java.lang.String) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException("Could not instantinate enum value " + name()
			        + " with value that ought to be in pl.bcichecki.rms.utils.PrivilegeUtils.Values class.", e);
		}
	}

	@Override
	public String toString() {
		return value;
	}

}

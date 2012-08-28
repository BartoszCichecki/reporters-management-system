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

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Cichecki
 */
public enum PrivilegeType {

	GET_USERS, GET_PROFILE, MANAGE_USERS, MANAGE_PROFILE;

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
		 * *** HACK *** This is done to maintain this class and
		 * PrivilegeUtils.Value more easily. It is very convenient to persist
		 * enums with JPA, but if we want to use Spring Security in
		 * authorization mechanism which requires values to be constants we must
		 * use static fields. With this hack we are sure that this enum and
		 * PrivilegeUtils.Value are synchronized. If you think about this
		 * solution it is not that bad after all - these values won't change, so
		 * if it runs for the first time, it will run every time and you don't
		 * have to synchronize their values.
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

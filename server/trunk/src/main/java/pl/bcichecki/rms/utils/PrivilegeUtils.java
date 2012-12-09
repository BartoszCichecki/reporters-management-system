/**
 * Project:   rms-server
 * File:      PrivilegeUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      21-08-2012
 */

package pl.bcichecki.rms.utils;

import java.util.Arrays;
import java.util.List;

import pl.bcichecki.rms.model.impl.PrivilegeType;

/**
 * @author Bartosz Cichecki
 */
public class PrivilegeUtils {

	public class Values {

		public static final String CLEAN_UP_MESSAGES = "CLEAN_UP_MESSAGES";

		public static final String CREATE_DEVICES = "CREATE_DEVICES";

		public static final String CREATE_ROLES = "CREATE_ROLES";

		public static final String CREATE_USERS = "CREATE_USERS";

		public static final String DELETE_ACCESS_HISTORY = "DELETE_ACCESS_HISTORY";

		public static final String DELETE_DEVICES = "DELETE_DEVICES";

		public static final String DELETE_EVENTS = "DELETE_EVENTS";

		public static final String DELETE_MESSAGES = "DELETE_MESSAGES";

		public static final String DELETE_MY_EVENTS = "DELETE_MY_EVENTS";

		public static final String DELETE_ROLES = "DELETE_ROLES";

		public static final String DELETE_USERS = "DELETE_USERS";

		public static final String LOOK_UP_DEVICE_EVENTS = "LOOK_UP_DEVICE_EVENTS";

		public static final String LOOK_UP_ROLE_USERS = "LOOK_UP_ROLE_USERS";

		public static final String LOOK_UP_USER_ROLES = "LOOK_UP_USER_ROLES";

		public static final String MANAGE_ACCESS_HISTORY = "MANAGE_ACCESS_HISTORY";

		public static final String MANAGE_DEVICES = "MANAGE_DEVICES";

		public static final String MANAGE_EVENTS = "MANAGE_EVENTS";

		public static final String MANAGE_MESSAGES = "MANAGE_MESSAGES";

		public static final String MANAGE_PROFILE = "MANAGE_PROFILE";

		public static final String MANAGE_ROLES = "MANAGE_ROLES";

		public static final String MANAGE_USERS = "MANAGE_USERS";

		public static final String MARK_DELETE_DEVICES = "MARK_DELETE_DEVICES";

		public static final String MARK_DELETE_MESSAGES = "MARK_DELETE_MESSAGES";

		public static final String MARK_DELETE_MY_EVENTS = "MARK_DELETE_MY_EVENTS";

		public static final String POST_EVENTS = "POST_EVENTS";

		public static final String POST_EVENTS_TO_WAITING_ROOM = "POST_EVENTS_TO_WAITING_ROOM";

		public static final String SEND_MESSAGES = "SEND_MESSAGES";

		public static final String SIGN_UP_FOR_EVENT = "SIGN_UP_FOR_EVENT";

		public static final String UPDATE_DEVICES = "UPDATE_DEVICES";

		public static final String UPDATE_MY_EVENTS = "UPDATE_MY_EVENTS";

		public static final String UPDATE_OUTGOING_MESSAGES = "UPDATE_OUTGOING_MESSAGES";

		public static final String UPDATE_ROLES = "UPDATE_ROLES";

		public static final String UPDATE_USERS = "UPDATE_USERS";

		public static final String VIEW_ACCESS_HISTORY = "VIEW_ACCESS_HISTORY";

		public static final String VIEW_DEVICES = "VIEW_DEVICES";

		public static final String VIEW_EVENTS = "VIEW_EVENTS";

		public static final String VIEW_MESSAGES = "VIEW_MESSAGES";

		public static final String VIEW_PROFILE = "VIEW_PROFILE";

		public static final String VIEW_ROLES = "VIEW_ROLES";

		public static final String VIEW_USERS = "VIEW_USERS";

	}

	public static List<PrivilegeType> getAllPrivileges() {
		return Arrays.asList(PrivilegeType.values());
	}
}

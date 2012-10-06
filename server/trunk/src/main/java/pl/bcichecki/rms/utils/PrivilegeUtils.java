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

		public static final String VIEW_DEVICES = "VIEW_DEVICES";

		public static final String MANAGE_DEVICES = "MANAGE_DEVICES";

		public static final String VIEW_MESSAGES = "VIEW_MESSAGES";

		public static final String SEND_MESSAGES = "SEND_MESSAGES";

		public static final String MANAGE_MESSAGES = "MANAGE_MESSAGES";

		public static final String VIEW_PROFILE = "VIEW_PROFILE";

		public static final String MANAGE_PROFILE = "MANAGE_PROFILE";

		public static final String MANAGE_ROLES = "MANAGE_ROLES";

		public static final String VIEW_USERS = "VIEW_USERS";

		public static final String MANAGE_USERS = "MANAGE_USERS";

	}

	public static List<PrivilegeType> getAllPrivileges() {
		return Arrays.asList(PrivilegeType.values());
	}
}

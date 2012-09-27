/**
 * Project:   Reporters Management System - Server
 * File:      AccessHistoryService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-08-2012
 */

package pl.bcichecki.rms.services;

import pl.bcichecki.rms.model.impl.AccessStatus;

/**
 * @author Bartosz Cichecki
 */
public interface AccessHistoryService {

	public boolean logAccess(String username, String userIp, AccessStatus accessStatus);

}
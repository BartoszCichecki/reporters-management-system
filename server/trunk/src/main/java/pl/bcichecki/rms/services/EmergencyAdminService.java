/**
 * Project:   Reporters Management System - Server
 * File:      EmergencyAdminService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      21-08-2012
 */

package pl.bcichecki.rms.services;

import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public interface EmergencyAdminService {

	UserEntity getEmergencyAdmin();

	boolean isEmergencyAdmin(String username);

}

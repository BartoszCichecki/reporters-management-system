/**
 * Project:   rms-server
 * File:      NotificationService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.Locale;

import pl.bcichecki.rms.exceptions.impl.ServiceException;

/**
 * @author Bartosz Cichecki
 */
public interface NotificationService {

	boolean notifyUserAboutPasswordChange(String username, String newPassword, Locale locale) throws ServiceException;

}

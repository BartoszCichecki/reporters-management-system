/**
 * Project:   rms-client-android
 * File:      PrivilegesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.services;

import java.util.Set;

import pl.bcichecki.rms.client.android.exceptions.impl.ServiceException;
import pl.bcichecki.rms.client.android.model.impl.PrivilegeType;

/**
 * @author Bartosz Cichecki
 */
public interface PrivilegesService {

	Set<PrivilegeType> getAllPrivileges();

	Set<PrivilegeType> getAuthenticatedUsersPrivileges();

	Set<PrivilegeType> getUsersPrivileges(String userId) throws ServiceException;

}

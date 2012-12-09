/**
 * Project:   rms-server
 * File:      PrivilegesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.Set;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.PrivilegeType;

/**
 * @author Bartosz Cichecki
 */
public interface PrivilegesService {

	Set<PrivilegeType> getAllPrivileges();

	Set<PrivilegeType> getAuthenticatedUsersPrivileges();

	Set<PrivilegeType> getUsersPrivileges(String userId) throws ServiceException;

}

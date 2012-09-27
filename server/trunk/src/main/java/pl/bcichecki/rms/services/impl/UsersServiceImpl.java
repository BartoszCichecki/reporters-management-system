/**
 * Project:   rms-server
 * File:      UsersServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.RolesDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.UsersService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private RolesDao rolesDao;

	@Override
	public List<UserEntity> getUsersWithRole(Long roleId, boolean idAndVersionOnly) throws ServiceException {
		if (rolesDao.getById(roleId) == null) {
			throw new ServiceException("Role with this ID does not exist!",
					"exceptions.serviceExceptions.roles.notExistId");
		}
		return usersDao.getUsersWithRole(roleId, idAndVersionOnly);
	}

}

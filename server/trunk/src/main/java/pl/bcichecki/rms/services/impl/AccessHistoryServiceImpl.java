/**
 * Project:   Reporters Management System - Server
 * File:      AccessHistoryServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-08-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.AccessHistoryDao;
import pl.bcichecki.rms.model.impl.AccessHistoryEntity;
import pl.bcichecki.rms.model.impl.AccessStatus;
import pl.bcichecki.rms.services.AccessHistoryService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccessHistoryServiceImpl implements AccessHistoryService {

	@Autowired
	private AccessHistoryDao accessHistoryDao;

	@Override
	public boolean logAccess(String username, String userIp, AccessStatus accessStatus) {
		AccessHistoryEntity entity = new AccessHistoryEntity();
		entity.setUsername(username);
		entity.setIp(userIp);
		entity.setAccessDate(new Date());
		entity.setAccessStatus(accessStatus);
		accessHistoryDao.create(entity);
		return true;
	}

}

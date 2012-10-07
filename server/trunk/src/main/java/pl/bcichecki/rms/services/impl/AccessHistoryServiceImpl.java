/**
 * Project:   rms-server
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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.AccessHistoryDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.AccessHistoryEntity;
import pl.bcichecki.rms.model.impl.AuthenticationStatus;
import pl.bcichecki.rms.model.impl.AuthorizationStatus;
import pl.bcichecki.rms.services.AccessHistoryService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccessHistoryServiceImpl implements AccessHistoryService {

	@Autowired
	private AccessHistoryDao accessHistoryDao;

	@Override
	public boolean delete(Long id) throws ServiceException {
		AccessHistoryEntity accessHistory = accessHistoryDao.getById(id);
		if (accessHistory == null) {
			throw new ServiceException("Access history entry with this ID does not exist!",
			        "exceptions.serviceExceptions.accessHistory.notExistsId");
		}
		accessHistoryDao.delete(accessHistory);
		return true;
	}

	@Override
	public boolean deleteAll(Date from, Date till) throws ServiceException {
		return deleteAllByIpUsername(null, null, from, till);
	}

	@Override
	public boolean deleteAllByIp(String ip, Date from, Date till) throws ServiceException {
		return deleteAllByIpUsername(ip, null, from, till);
	}

	@Override
	public boolean deleteAllByIpUsername(String ip, String username, Date from, Date till) throws ServiceException {
		if (from.after(till)) {
			throw new ServiceException("Date from must be before till!", "exceptions.serviceExceptions.general.fromAfterTill");
		}
		List<AccessHistoryEntity> accessHistory = getAllByIpUsername(ip, username, from, till);
		for (AccessHistoryEntity accessHistoryEntity : accessHistory) {
			accessHistoryDao.delete(accessHistoryEntity);
		}
		return true;
	}

	@Override
	public boolean deleteAllByUsername(String username, Date from, Date till) throws ServiceException {
		return deleteAllByIpUsername(null, username, from, till);
	}

	@Override
	@Transactional(readOnly = true)
	public AccessHistoryEntity get(Long id) throws ServiceException {
		AccessHistoryEntity accessHistory = accessHistoryDao.getById(id);
		if (accessHistory == null) {
			throw new ServiceException("Access history entry with this ID does not exist!",
			        "exceptions.serviceExceptions.accessHistory.notExistsId");
		}
		return accessHistory;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistoryEntity> getAll(Date from, Date till) throws ServiceException {
		return getAllByIpUsername(null, null, from, till);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistoryEntity> getAllByIp(String ip, Date from, Date till) throws ServiceException {
		return getAllByIpUsername(ip, null, from, till);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistoryEntity> getAllByIpUsername(String ip, String username, Date from, Date till) throws ServiceException {
		if (from.after(till)) {
			throw new ServiceException("Date from must be before till!", "exceptions.serviceExceptions.general.fromAfterTill");
		}
		return accessHistoryDao.getAllByIpUsername(ip, username, from, till);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistoryEntity> getAllByUsername(String username, Date from, Date till) throws ServiceException {
		return getAllByIpUsername(null, username, from, till);
	}

	@Override
	public boolean logAccess(String username, String userIp, AuthenticationStatus authenticationStatus) {
		return logAccess(username, userIp, authenticationStatus, null);
	}

	@Override
	public boolean logAccess(String username, String userIp, AuthenticationStatus authenticationStatus,
	        AuthorizationStatus authorizationStatus) {
		AccessHistoryEntity entity = new AccessHistoryEntity();
		entity.setUsername(username);
		entity.setIp(userIp);
		entity.setAccessDate(new Date());
		entity.setAuthenticationStatus(authenticationStatus);
		entity.setAuthorizationStatus(authorizationStatus);
		accessHistoryDao.create(entity);
		return true;
	}

}

/**
 * Project:   rms-server
 * File:      AccessHistoryService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-08-2012
 */

package pl.bcichecki.rms.services;

import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.AccessHistoryEntity;
import pl.bcichecki.rms.model.impl.AuthenticationStatus;
import pl.bcichecki.rms.model.impl.AuthorizationStatus;

/**
 * @author Bartosz Cichecki
 */
public interface AccessHistoryService {

	boolean delete(Long id) throws ServiceException;

	boolean deleteAll(Date from, Date till) throws ServiceException;

	boolean deleteAllByIp(String ip, Date from, Date till) throws ServiceException;

	boolean deleteAllByIpUsername(String ip, String username, Date from, Date till) throws ServiceException;

	boolean deleteAllByUsername(String username, Date from, Date till) throws ServiceException;

	AccessHistoryEntity get(Long id) throws ServiceException;

	List<AccessHistoryEntity> getAll(Date from, Date till) throws ServiceException;

	List<AccessHistoryEntity> getAllByIp(String ip, Date from, Date till) throws ServiceException;

	List<AccessHistoryEntity> getAllByIpUsername(String ip, String username, Date from, Date till) throws ServiceException;

	List<AccessHistoryEntity> getAllByUsername(String username, Date from, Date till) throws ServiceException;

	boolean logAccess(String username, String userIp, AuthenticationStatus accessStatus);

	boolean logAccess(String username, String userIp, AuthenticationStatus authenticationStatus, AuthorizationStatus authorizationStatus);

}

/**
 * Project:   rms-server
 * File:      EventsService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.EventEntity;

/**
 * @author Bartosz Cichecki
 */
public interface EventsService {

	boolean archiveEvent(String id) throws ServiceException;

	boolean createEvent(EventEntity event) throws ServiceException;

	boolean deleteEvent(String id, Boolean markDeleted) throws ServiceException;

	boolean deleteMyEvent(String id, Boolean markDeleted) throws ServiceException;

	List<EventEntity> getAllCurrentUserEvents(Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getAllEvents(Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getAllUserEvents(String userId, Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getDevicesEvents(String deviceId, Boolean archived, Boolean deleted, Date eventsFrom, Date eventsTill)
	        throws ServiceException;

	EventEntity getEvent(String id) throws ServiceException;

	boolean lockEvent(String id, boolean lock) throws ServiceException;

	boolean signUpForEvent(String id, boolean signUp) throws ServiceException;

	boolean updateEvent(EventEntity event) throws ServiceException;

	boolean updateMyEvent(EventEntity event) throws ServiceException;

}

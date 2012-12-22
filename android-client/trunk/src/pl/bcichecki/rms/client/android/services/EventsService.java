/**
 * Project:   rms-client-android
 * File:      EventsService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.services;

import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.client.android.exceptions.impl.ServiceException;
import pl.bcichecki.rms.client.android.model.impl.EventEntity;

/**
 * @author Bartosz Cichecki
 */
public interface EventsService {

	boolean createEvent(EventEntity event) throws ServiceException;

	boolean createLockedEvent(EventEntity event) throws ServiceException;

	boolean deleteEvent(String id, Boolean markDeleted) throws ServiceException;

	List<EventEntity> getAllCurrentUserEvents(Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getAllEvents(Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getAllUserEvents(String userId, Boolean archived, Boolean deleted, Date from, Date till);

	List<EventEntity> getDevicesEvents(String deviceId, Boolean archived, Boolean deleted, Date eventsFrom, Date eventsTill)
	        throws ServiceException;

	EventEntity getEvent(String id) throws ServiceException;

	boolean updateEvent(EventEntity event) throws ServiceException;

}

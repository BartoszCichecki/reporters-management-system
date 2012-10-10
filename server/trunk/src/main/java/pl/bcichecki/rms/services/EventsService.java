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

	boolean createEvent(EventEntity event) throws ServiceException;

	boolean createLockedEvent(EventEntity event) throws ServiceException;

	boolean deleteEvent(Long id) throws ServiceException;

	List<EventEntity> getAllCurrentUserEvent(boolean archived, Date from, Date till);

	List<EventEntity> getAllEvents(boolean archived, Date from, Date till);

	List<EventEntity> getAllUserEvent(Long userId, boolean archived, Date from, Date till);

	List<EventEntity> getDevicesEvents(Long deviceId, boolean archived, Date eventsFrom, Date eventsTill) throws ServiceException;

	EventEntity getEvent(Long id) throws ServiceException;

	boolean updateEvent(EventEntity event) throws ServiceException;

}

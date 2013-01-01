/**
 * Project:   rms-server
 * File:      EventsServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.DevicesDao;
import pl.bcichecki.rms.dao.EventsDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.services.EventsService;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventsServiceImpl implements EventsService {

	@Autowired
	private EventsDao eventsDao;

	@Autowired
	private DevicesDao devicesDao;

	@Override
	public boolean createEvent(EventEntity event) throws ServiceException {
		if (eventsDao.contains(event)) {
			throw new ServiceException("Could not create duplicate event!", "exceptions.serviceExceptions.events.duplicateEvent");
		}
		eventsDao.create(event);
		return true;
	}

	@Override
	public boolean createLockedEvent(EventEntity event) throws ServiceException {
		event.setLocked(true);
		if (eventsDao.contains(event)) {
			throw new ServiceException("Could not create duplicate event!", "exceptions.serviceExceptions.events.duplicateEvent");
		}
		eventsDao.create(event);
		return true;
	}

	@Override
	public boolean deleteEvent(String id, Boolean markDeleted) throws ServiceException {
		EventEntity event = eventsDao.getById(id);
		if (event == null) {
			throw new ServiceException("You can't delete event that does not exist!",
			        "exceptions.serviceExceptions.events.cantDeleteNotExisting");
		}
		if (markDeleted) {
			event.setDeleted(true);
			eventsDao.update(event);
		} else {
			eventsDao.delete(event);
		}
		return true;
	}

	@Override
	public boolean deleteMyEvent(String id, Boolean markDeleted) throws ServiceException {
		EventEntity event = eventsDao.getById(id);
		if (event == null) {
			throw new ServiceException("You can't delete event that does not exist!",
			        "exceptions.serviceExceptions.events.cantDeleteNotExisting");
		}
		if (!SecurityUtils.getCurrentUserId().equals(event.getCreationUserId())) {
			throw new ServiceException("You can't delete event that is not yours!",
			        "exceptions.serviceExceptions.events.cantDeleteNotYours");
		}
		if (markDeleted) {
			event.setDeleted(true);
			eventsDao.update(event);
		} else {
			eventsDao.delete(event);
		}
		return true;
	}

	@Override
	public List<EventEntity> getAllCurrentUserEvents(Boolean archived, Boolean deleted, Date from, Date till) {
		String currentUserId = SecurityUtils.getCurrentUserId();
		return getAllUserEvents(currentUserId, archived, deleted, from, till);
	}

	@Override
	public List<EventEntity> getAllEvents(Boolean archived, Boolean deleted, Date from, Date till) {
		return getAllUserEvents(null, archived, deleted, from, till);
	}

	@Override
	public List<EventEntity> getAllUserEvents(String userId, Boolean archived, Boolean deleted, Date from, Date till) {
		return eventsDao.getAllByUser(userId, archived, deleted, from, till);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventEntity> getDevicesEvents(String deviceId, Boolean archived, Boolean deleted, Date eventsFrom, Date eventsTill)
	        throws ServiceException {
		if (eventsFrom != null && eventsTill != null && eventsFrom.after(eventsTill)) {
			throw new ServiceException("Date from must be before till!", "exceptions.serviceExceptions.general.fromAfterTill");
		}
		if (devicesDao.getById(deviceId) == null) {
			throw new ServiceException("Device with this ID does not exist!", "exceptions.serviceExceptions.devices.notExistId");
		}
		return eventsDao.getDevicesEvents(deviceId, archived, deleted, eventsFrom, eventsTill);
	}

	@Override
	public EventEntity getEvent(String id) throws ServiceException {
		EventEntity event = eventsDao.getById(id);
		if (event == null) {
			throw new ServiceException("Event with this ID does not exist!", "exceptions.serviceExceptions.events.notExistId");
		}
		return event;
	}

	@Override
	public boolean updateEvent(EventEntity event) throws ServiceException {
		EventEntity retrieved = eventsDao.getById(event.getId());
		if (retrieved == null) {
			throw new ServiceException("You can't update event that does not exist!",
			        "exceptions.serviceExceptions.events.cantUpdateNotExisting");
		}
		retrieved.merge(event);
		eventsDao.update(retrieved);
		return true;
	}

	@Override
	public boolean updateMyEvent(EventEntity event) throws ServiceException {
		EventEntity retrieved = eventsDao.getById(event.getId());
		if (retrieved == null) {
			throw new ServiceException("You can't update event that does not exist!",
			        "exceptions.serviceExceptions.events.cantUpdateNotExisting");
		}
		if (!SecurityUtils.getCurrentUserId().equals(event.getCreationUserId())) {
			throw new ServiceException("You can't update event that is not yours!",
			        "exceptions.serviceExceptions.events.cantUpdateNotYours");
		}
		retrieved.merge(event);
		eventsDao.update(retrieved);
		return true;
	}

}

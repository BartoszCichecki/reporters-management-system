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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.DevicesDao;
import pl.bcichecki.rms.dao.EventsDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
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

	@Autowired
	private UsersDao usersDao;

	@Override
	public boolean archiveEvent(String id) throws ServiceException {
		String currentUserId = SecurityUtils.getCurrentUserId();
		EventEntity retrievedEvent = eventsDao.getById(id);
		if (retrievedEvent == null) {
			throw new ServiceException("Event with this ID does not exist!", "exceptions.serviceExceptions.events.notExistId");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		if (!retrievedEvent.getCurrentUser().getId().equals(currentUserId)) {
			throw new ServiceException("You are not the owner of this event!", "exceptions.serviceExceptions.events.notAOwner");
		}
		retrievedEvent.setArchived(true);
		eventsDao.update(retrievedEvent);
		return true;
	}

	@Override
	public boolean createEvent(EventEntity event) throws ServiceException {
		if (eventsDao.contains(event)) {
			throw new ServiceException("Could not create duplicate event!", "exceptions.serviceExceptions.events.duplicateEvent");
		}
		event.setParticipants(reloadParticipants(event.getParticipants()));
		event.setDevices(reloadDevices(event.getDevices()));
		eventsDao.create(event);
		return true;
	}

	@Override
	public boolean deleteEvent(String id, Boolean markDeleted) throws ServiceException {
		EventEntity retrievedEvent = eventsDao.getById(id);
		if (retrievedEvent == null) {
			throw new ServiceException("You can't delete event that does not exist!",
			        "exceptions.serviceExceptions.events.cantDeleteNotExisting");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		if (markDeleted) {
			retrievedEvent.setDeleted(true);
			eventsDao.update(retrievedEvent);
		} else {
			eventsDao.delete(retrievedEvent);
		}
		return true;
	}

	@Override
	public boolean deleteMyEvent(String id, Boolean markDeleted) throws ServiceException {
		EventEntity retrievedEvent = eventsDao.getById(id);
		if (retrievedEvent == null) {
			throw new ServiceException("You can't delete event that does not exist!",
			        "exceptions.serviceExceptions.events.cantDeleteNotExisting");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		if (!SecurityUtils.getCurrentUserId().equals(retrievedEvent.getCreationUserId())) {
			throw new ServiceException("You can't delete event that is not yours!",
			        "exceptions.serviceExceptions.events.cantDeleteNotYours");
		}
		if (markDeleted) {
			retrievedEvent.setDeleted(true);
			eventsDao.update(retrievedEvent);
		} else {
			eventsDao.delete(retrievedEvent);
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
	public boolean lockEvent(String id, boolean lock) throws ServiceException {
		String currentUserId = SecurityUtils.getCurrentUserId();
		EventEntity retrievedEvent = eventsDao.getById(id);
		if (retrievedEvent == null) {
			throw new ServiceException("Event with this ID does not exist!", "exceptions.serviceExceptions.events.notExistId");
		}
		if (!retrievedEvent.getCurrentUser().getId().equals(currentUserId)) {
			throw new ServiceException("You are not the owner of this event!", "exceptions.serviceExceptions.events.notAOwner");
		}
		retrievedEvent.setLocked(lock);
		eventsDao.update(retrievedEvent);
		return true;
	}

	private Set<DeviceEntity> reloadDevices(Set<DeviceEntity> devices) throws ServiceException {
		Set<DeviceEntity> reloadedDevices = new HashSet<DeviceEntity>();
		for (DeviceEntity device : devices) {
			DeviceEntity reloadedDevice = devicesDao.getById(device.getId());
			if (reloadedDevice == null) {
				throw new ServiceException("Device with this ID does not exist!", "exceptions.serviceExceptions.devices.notExistId");
			}
			reloadedDevices.add(reloadedDevice);
		}
		return reloadedDevices;
	}

	private Set<UserEntity> reloadParticipants(Set<UserEntity> participants) throws ServiceException {
		Set<UserEntity> reloadedParticipants = new HashSet<UserEntity>();
		for (UserEntity participant : participants) {
			UserEntity reloadedParticipant = usersDao.getById(participant.getId());
			if (reloadedParticipant == null) {
				throw new ServiceException("User with this ID does not exist!", "exceptions.serviceExceptions.users.notExistId");
			}
			reloadedParticipants.add(reloadedParticipant);
		}
		return reloadedParticipants;
	}

	@Override
	public boolean signUpForEvent(String id, boolean signUp) throws ServiceException {
		String currentUserId = SecurityUtils.getCurrentUserId();
		EventEntity retrievedEvent = eventsDao.getById(id);
		if (retrievedEvent == null) {
			throw new ServiceException("Event with this ID does not exist!", "exceptions.serviceExceptions.events.notExistId");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		UserEntity retrievedUser = usersDao.getById(currentUserId);
		if (signUp) {
			if (!retrievedEvent.getParticipants().contains(retrievedUser)) {
				retrievedEvent.getParticipants().add(retrievedUser);
			}
		} else {
			retrievedEvent.getParticipants().remove(retrievedUser);
		}

		eventsDao.update(retrievedEvent);
		return false;
	}

	@Override
	public boolean updateEvent(EventEntity event) throws ServiceException {
		EventEntity retrievedEvent = eventsDao.getById(event.getId());
		if (retrievedEvent == null) {
			throw new ServiceException("You can't update event that does not exist!",
			        "exceptions.serviceExceptions.events.cantUpdateNotExisting");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		event.setParticipants(reloadParticipants(event.getParticipants()));
		event.setDevices(reloadDevices(event.getDevices()));
		retrievedEvent.merge(event);
		eventsDao.update(retrievedEvent);
		return true;
	}

	@Override
	public boolean updateMyEvent(EventEntity event) throws ServiceException {
		EventEntity retrievedEvent = eventsDao.getById(event.getId());
		if (retrievedEvent == null) {
			throw new ServiceException("You can't update event that does not exist!",
			        "exceptions.serviceExceptions.events.cantUpdateNotExisting");
		}
		if (retrievedEvent.isLocked()) {
			throw new ServiceException("This event is locked. You can't alter it!", "exceptions.serviceExceptions.isLocked");
		}
		if (!SecurityUtils.getCurrentUserId().equals(event.getCreationUserId())) {
			throw new ServiceException("You can't update event that is not yours!",
			        "exceptions.serviceExceptions.events.cantUpdateNotYours");
		}
		event.setParticipants(reloadParticipants(event.getParticipants()));
		event.setDevices(reloadDevices(event.getDevices()));
		retrievedEvent.merge(event);
		eventsDao.update(retrievedEvent);
		return true;
	}

}

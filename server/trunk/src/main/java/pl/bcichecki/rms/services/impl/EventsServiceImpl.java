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
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.services.EventsService;

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
	@Transactional(readOnly = true)
	public List<EventEntity> getDevicesEvents(Long deviceId, Date eventsFrom, Date eventsTill) throws ServiceException {
		DeviceEntity device = devicesDao.getById(deviceId);
		if (eventsFrom != null && eventsTill != null && eventsFrom.after(eventsTill)) {
			throw new ServiceException("Date from must be before till!", "exceptions.serviceExceptions.general.fromAfterTill");
		}
		if (device == null) {
			throw new ServiceException("Device with this ID does not exist!", "exceptions.serviceExceptions.devices.notExistId");
		}
		return eventsDao.getDevicesEvents(device, eventsFrom, eventsTill);
	}

}

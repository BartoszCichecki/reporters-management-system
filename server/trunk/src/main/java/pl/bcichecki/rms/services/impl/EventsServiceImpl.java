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

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.services.EventsService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventsServiceImpl implements EventsService {

	@Override
	public List<EventEntity> getDevicesEvents(Long deviceId, Date eventsFrom, Date eventsTill) {
		// TODO Implement
		return null;
	}

	@Override
	public List<EventEntity> getDevicesEvents(String deviceName, Date eventsFrom, Date eventsTill) {
		// TODO Implement
		return null;
	}

}

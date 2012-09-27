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

import pl.bcichecki.rms.model.impl.EventEntity;

/**
 * @author Bartosz Cichecki
 */
public interface EventsService {

	List<EventEntity> getDevicesEvents(Long deviceId, Date eventsFrom, Date eventsTill);

	List<EventEntity> getDevicesEvents(String deviceName, Date eventsFrom, Date eventsTill);

}

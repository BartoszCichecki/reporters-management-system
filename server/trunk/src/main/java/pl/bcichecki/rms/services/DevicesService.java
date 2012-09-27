/**
 * Project:   rms-server
 * File:      DevicesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.DeviceEntity;

/**
 * @author Bartosz Cichecki
 */
public interface DevicesService {

	boolean createDevice(DeviceEntity device) throws ServiceException;

	boolean deleteDevice(Long id) throws ServiceException;

	List<DeviceEntity> getAllDevices(boolean idAndVersionOnly);

	DeviceEntity getDeviceById(Long id) throws ServiceException;

	DeviceEntity getDeviceByName(String name) throws ServiceException;

	boolean updateDevice(DeviceEntity device) throws ServiceException;

}

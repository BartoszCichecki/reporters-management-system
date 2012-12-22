/**
 * Project:   rms-client-android
 * File:      DevicesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.services;

import java.util.List;

import pl.bcichecki.rms.client.android.exceptions.impl.ServiceException;
import pl.bcichecki.rms.client.android.model.impl.DeviceEntity;

/**
 * @author Bartosz Cichecki
 */
public interface DevicesService {

	boolean createDevice(DeviceEntity device) throws ServiceException;

	boolean deleteDevice(String id, boolean markDeleted) throws ServiceException;

	List<DeviceEntity> getAllDevices();

	List<DeviceEntity> getAllDevices(Boolean deleted);

	DeviceEntity getDeviceById(String id) throws ServiceException;

	DeviceEntity getDeviceByName(String name) throws ServiceException;

	boolean updateDevice(DeviceEntity device) throws ServiceException;

}

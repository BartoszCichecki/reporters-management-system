/**
 * Project:   rms-server
 * File:      DevicesServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.DevicesDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.services.DevicesService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DevicesServiceImpl implements DevicesService {

	@Autowired
	private DevicesDao devicesDao;

	@Override
	public boolean createDevice(DeviceEntity device) throws ServiceException {
		if (devicesDao.getByName(device.getName(), false) != null) {
			throw new ServiceException("Device with such name already exist! Devices must have unique names.",
			        "exceptions.serviceExceptions.devices.duplicateName");
		}
		devicesDao.create(device);
		return true;
	}

	@Override
	public boolean deleteDevice(String id, boolean markDeleted) throws ServiceException {
		DeviceEntity device = devicesDao.getById(id);
		if (device == null) {
			throw new ServiceException("You can't delete device that does not exist!",
			        "exceptions.serviceExceptions.devices.cantDeleteNotExisting");
		}
		if (markDeleted) {
			device.setDeleted(true);
			devicesDao.update(device);
		} else {
			devicesDao.delete(device);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DeviceEntity> getAllDevices() {
		return devicesDao.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DeviceEntity> getAllDevices(Boolean deleted) {
		return devicesDao.getAll(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public DeviceEntity getDeviceById(String id) throws ServiceException {
		DeviceEntity device = devicesDao.getById(id);
		if (device == null) {
			throw new ServiceException("Device with this ID does not exist!", "exceptions.serviceExceptions.devices.notExistId");
		}
		return device;
	}

	@Override
	@Transactional(readOnly = true)
	public DeviceEntity getDeviceByName(String name) throws ServiceException {
		DeviceEntity device = devicesDao.getByName(name, false);
		if (device == null) {
			throw new ServiceException("Device with this name does not exist!", "exceptions.serviceExceptions.devices.notExistName");
		}
		return device;
	}

	@Override
	public boolean updateDevice(DeviceEntity device) throws ServiceException {
		if (devicesDao.getByName(device.getName(), true) != null) {
			throw new ServiceException("You can't update device that does not exist!",
			        "exceptions.serviceExceptions.devices.cantUpdateNotExisting");
		}
		DeviceEntity retrieved = devicesDao.getById(device.getId());
		if (retrieved == null) {
			throw new ServiceException("You can't update device that does not exist!",
			        "exceptions.serviceExceptions.devices.cantUpdateNotExisting");
		}
		retrieved.merge(device);
		devicesDao.update(retrieved);
		return true;
	}

}

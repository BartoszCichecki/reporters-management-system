/**
 * Project:   rms-server
 * File:      DevicesDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.util.List;

import pl.bcichecki.rms.model.impl.DeviceEntity;

/**
 * @author Bartosz Cichecki
 */
public interface DevicesDao extends GenericDao<DeviceEntity> {

	List<DeviceEntity> getAll(Boolean deleted);

	DeviceEntity getByName(String name, boolean deleted);

}

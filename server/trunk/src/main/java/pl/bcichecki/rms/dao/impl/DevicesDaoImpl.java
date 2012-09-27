/**
 * Project:   Reporters Management System - Server
 * File:      DevicesDaoImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.bcichecki.rms.dao.DevicesDao;
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.model.impl.DeviceEntity_;

/**
 * @author Bartosz Cichecki
 */
public class DevicesDaoImpl extends AbstractGenericDao<DeviceEntity> implements DevicesDao {

	@Override
	public DeviceEntity getByName(String name) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<DeviceEntity> criteriaQuery = criteriaBuilder.createQuery(DeviceEntity.class);
		Root<DeviceEntity> root = criteriaQuery.from(DeviceEntity.class);
		Predicate predicate = criteriaBuilder.equal(root.get(DeviceEntity_.name), name);
		criteriaQuery.where(predicate);
		return getByCriteria(criteriaQuery);
	}

}

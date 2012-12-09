/**
 * Project:   rms-server
 * File:      EventsDaoImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import pl.bcichecki.rms.dao.DevicesDao;
import pl.bcichecki.rms.dao.EventsDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.model.impl.EventEntity_;

/**
 * @author Bartosz Cichecki
 */
public class EventsDaoImpl extends AbstractGenericDao<EventEntity> implements EventsDao {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private DevicesDao devicesDao;

	@Override
	public List<EventEntity> getAllByUser(String userId, Boolean archived, Boolean deleted, Date from, Date till) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EventEntity> criteriaQuery = criteriaBuilder.createQuery(EventEntity.class);
		Root<EventEntity> root = criteriaQuery.from(EventEntity.class);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(EventEntity_.startDate), from));
		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(EventEntity_.endDate), till));
		if (archived != null) {
			predicates.add(criteriaBuilder.equal(root.get(EventEntity_.archived), archived));
		}
		if (deleted != null) {
			predicates.add(criteriaBuilder.equal(root.get(EventEntity_.deleted), deleted));
		}
		if (userId != null) {
			predicates.add(criteriaBuilder.or(criteriaBuilder.isMember(usersDao.getById(userId), root.get(EventEntity_.participants)),
			        criteriaBuilder.equal(root.get(EventEntity_.creationUser), userId)));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public List<EventEntity> getDevicesEvents(String deviceId, Boolean archived, Boolean deleted, Date from, Date till) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EventEntity> criteriaQuery = criteriaBuilder.createQuery(EventEntity.class);
		Root<EventEntity> root = criteriaQuery.from(EventEntity.class);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(EventEntity_.startDate), from));
		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(EventEntity_.endDate), till));
		if (archived != null) {
			predicates.add(criteriaBuilder.equal(root.get(EventEntity_.archived), archived));
		}
		if (deleted != null) {
			predicates.add(criteriaBuilder.equal(root.get(EventEntity_.deleted), deleted));
		}
		if (deviceId != null) {
			predicates.add(criteriaBuilder.isMember(devicesDao.getById(deviceId), root.get(EventEntity_.devices)));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		return getAllByCriteria(criteriaQuery);
	}

}

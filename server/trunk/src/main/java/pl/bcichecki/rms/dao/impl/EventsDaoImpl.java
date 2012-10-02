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

import pl.bcichecki.rms.dao.EventsDao;
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.model.impl.EventEntity;
import pl.bcichecki.rms.model.impl.EventEntity_;

/**
 * @author Bartosz Cichecki
 */
public class EventsDaoImpl extends AbstractGenericDao<EventEntity> implements EventsDao {

	private Predicate[] getAsArray(List<Predicate> predicates) {
		Predicate[] ret = new Predicate[predicates.size()];
		for (int i = 0; i < predicates.size(); i++) {
			ret[i] = predicates.get(i);
		}
		return ret;
	}

	@Override
	public List<EventEntity> getDevicesEvents(DeviceEntity device, Date eventsFrom, Date eventsTill) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EventEntity> criteriaQuery = criteriaBuilder.createQuery(EventEntity.class);
		Root<EventEntity> root = criteriaQuery.from(EventEntity.class);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.isMember(device, root.get(EventEntity_.devices)));
		if (eventsFrom != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(EventEntity_.startDate), eventsFrom));
		}
		if (eventsTill != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(EventEntity_.endDate), eventsTill));
		}
		criteriaQuery.where(criteriaBuilder.and(getAsArray(predicates)));

		return getAllByCriteria(criteriaQuery);
	}

}

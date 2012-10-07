/**
 * Project:   rms-server
 * File:      AccessHistoryDaoImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.bcichecki.rms.dao.AccessHistoryDao;
import pl.bcichecki.rms.model.impl.AccessHistoryEntity;
import pl.bcichecki.rms.model.impl.AccessHistoryEntity_;

/**
 * @author Bartosz Cichecki
 */
public class AccessHistoryDaoImpl extends AbstractGenericDao<AccessHistoryEntity> implements AccessHistoryDao {

	@Override
	public List<AccessHistoryEntity> getAllByIpUsername(String ip, String username, Date from, Date till) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<AccessHistoryEntity> criteriaQuery = criteriaBuilder.createQuery(AccessHistoryEntity.class);
		Root<AccessHistoryEntity> root = criteriaQuery.from(AccessHistoryEntity.class);
		Collection<Predicate> predicates = new ArrayList<>();
		if (ip != null) {
			predicates.add(criteriaBuilder.equal(root.get(AccessHistoryEntity_.ip), ip));
		}
		if (username != null) {
			predicates.add(criteriaBuilder.equal(root.get(AccessHistoryEntity_.username), username));
		}
		if (from != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(AccessHistoryEntity_.accessDate), from));
		}
		if (till != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(AccessHistoryEntity_.accessDate), till));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		return getAllByCriteria(criteriaQuery);
	}

}

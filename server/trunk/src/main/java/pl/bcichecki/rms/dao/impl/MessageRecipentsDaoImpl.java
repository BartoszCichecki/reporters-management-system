/**
 * Project:   rms-server
 * File:      MessageRecipentsDaoImpl.java
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

import pl.bcichecki.rms.dao.MessageRecipentsDao;
import pl.bcichecki.rms.model.AbstractEntity_;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity_;

/**
 * @author Bartosz Cichecki
 */
public class MessageRecipentsDaoImpl extends AbstractGenericDao<MessageRecipentEntity> implements MessageRecipentsDao {

	@Override
	public MessageRecipentEntity getByIdAndRecipentId(String id, String recipentId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageRecipentEntity> criteriaQuery = criteriaBuilder.createQuery(MessageRecipentEntity.class);
		Root<MessageRecipentEntity> root = criteriaQuery.from(MessageRecipentEntity.class);
		Predicate predicate1 = criteriaBuilder.equal(root.get(AbstractEntity_.id), id);
		Predicate predicate2 = criteriaBuilder.equal(root.get(MessageRecipentEntity_.recipent), recipentId);
		Predicate predicate3 = criteriaBuilder.equal(root.get(MessageRecipentEntity_.archivedByRecipent), archived);
		Predicate predicate4 = criteriaBuilder.equal(root.get(MessageRecipentEntity_.deletedByRecipent), deleted);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3, predicate4));
		return getByCriteria(criteriaQuery);
	}
}

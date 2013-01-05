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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import pl.bcichecki.rms.dao.MessageRecipentsDao;
import pl.bcichecki.rms.model.AbstractEntity_;
import pl.bcichecki.rms.model.impl.MessageEntity;
import pl.bcichecki.rms.model.impl.MessageEntity_;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity_;
import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public class MessageRecipentsDaoImpl extends AbstractGenericDao<MessageRecipentEntity> implements MessageRecipentsDao {

	@Override
	public MessageRecipentEntity getByMessageIdAndRecipentId(String messageId, String recipentId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageRecipentEntity> criteriaQuery = criteriaBuilder.createQuery(MessageRecipentEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		SetJoin<MessageEntity, MessageRecipentEntity> join = root.join(MessageEntity_.recipents);
		Join<MessageRecipentEntity, UserEntity> join2 = join.join(MessageRecipentEntity_.recipent);
		Predicate predicate1 = criteriaBuilder.equal(root.get(AbstractEntity_.id), messageId);
		Predicate predicate2 = criteriaBuilder.equal(join.get(MessageRecipentEntity_.archivedByRecipent), archived);
		Predicate predicate3 = criteriaBuilder.equal(join.get(MessageRecipentEntity_.deletedByRecipent), deleted);
		Predicate predicate4 = criteriaBuilder.equal(join2.get(AbstractEntity_.id), recipentId);
		criteriaQuery.select(join);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3, predicate4));
		return getByCriteria(criteriaQuery);
	}
}

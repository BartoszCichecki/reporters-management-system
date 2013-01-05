/**
 * Project:   rms-server
 * File:      MessagesDaoImpl.java
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import pl.bcichecki.rms.dao.MessagesDao;
import pl.bcichecki.rms.model.AbstractEntity_;
import pl.bcichecki.rms.model.impl.MessageEntity;
import pl.bcichecki.rms.model.impl.MessageEntity_;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity_;
import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
public class MessagesDaoImpl extends AbstractGenericDao<MessageEntity> implements MessagesDao {

	@Override
	public MessageEntity getByIdAndRecipentId(String id, String recipentId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		SetJoin<MessageEntity, MessageRecipentEntity> join = root.join(MessageEntity_.recipents);
		Collection<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(AbstractEntity_.id), id));
		predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.recipent), recipentId));
		predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.archivedByRecipent), archived));
		predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.deletedByRecipent), deleted));
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		return getByCriteria(criteriaQuery);
	}

	@Override
	public MessageEntity getByIdAndSenderId(String id, String senderId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		Join<MessageEntity, UserEntity> join = root.join(MessageEntity_.sender);
		Predicate predicate1 = criteriaBuilder.equal(root.get(AbstractEntity_.id), id);
		Predicate predicate2 = criteriaBuilder.equal(join.get(AbstractEntity_.id), senderId);
		Predicate predicate3 = criteriaBuilder.equal(root.get(MessageEntity_.archivedBySender), archived);
		Predicate predicate4 = criteriaBuilder.equal(root.get(MessageEntity_.deletedBySender), deleted);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3, predicate4));
		return getByCriteria(criteriaQuery);
	}

	@Override
	public List<MessageEntity> getByRecipentId(String recipentId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		SetJoin<MessageEntity, MessageRecipentEntity> join = root.join(MessageEntity_.recipents);
		Join<MessageRecipentEntity, UserEntity> join2 = join.join(MessageRecipentEntity_.recipent);
		Collection<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(join2.get(AbstractEntity_.id), recipentId));
		predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.archivedByRecipent), archived));
		predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.deletedByRecipent), deleted));
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public List<MessageEntity> getBySenderId(String senderId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		Join<MessageEntity, UserEntity> join = root.join(MessageEntity_.sender);
		Predicate predicate1 = criteriaBuilder.equal(join.get(AbstractEntity_.id), senderId);
		Predicate predicate2 = criteriaBuilder.equal(root.get(MessageEntity_.archivedBySender), archived);
		Predicate predicate3 = criteriaBuilder.equal(root.get(MessageEntity_.deletedBySender), deleted);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3));
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public List<MessageEntity> getMessagesReadBefore(Date date, boolean deletedOnly) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		SetJoin<MessageEntity, MessageRecipentEntity> join = root.join(MessageEntity_.recipents);
		Collection<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.lessThanOrEqualTo(join.get(MessageRecipentEntity_.readDate), date));
		if (deletedOnly) {
			predicates.add(criteriaBuilder.equal(join.get(MessageRecipentEntity_.deletedByRecipent), true));
		}
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		return getAllByCriteria(criteriaQuery);
	}
}

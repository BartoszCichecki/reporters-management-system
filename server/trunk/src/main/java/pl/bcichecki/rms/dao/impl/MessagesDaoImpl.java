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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.bcichecki.rms.dao.MessagesDao;
import pl.bcichecki.rms.model.AbstractEntity_;
import pl.bcichecki.rms.model.impl.MessageEntity;
import pl.bcichecki.rms.model.impl.MessageEntity_;

/**
 * @author Bartosz Cichecki
 */
public class MessagesDaoImpl extends AbstractGenericDao<MessageEntity> implements MessagesDao {

	@Override
	public MessageEntity getByIdAndRecipentId(Serializable id, Serializable recipentId, boolean archived, boolean deleted) {
		// TODO Implement method
		return null;
	}

	@Override
	public MessageEntity getByIdAndSenderId(Serializable id, Serializable senderId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		Predicate predicate1 = criteriaBuilder.equal(root.get(AbstractEntity_.id), id);
		Predicate predicate2 = criteriaBuilder.equal(root.get(MessageEntity_.sender), senderId);
		Predicate predicate3 = criteriaBuilder.equal(root.get(MessageEntity_.archivedBySender), archived);
		Predicate predicate4 = criteriaBuilder.equal(root.get(MessageEntity_.deletedBySender), deleted);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3, predicate4));
		return getByCriteria(criteriaQuery);
	}

	@Override
	public List<MessageEntity> getByRecipentId(Long recipentId, boolean archived, boolean deleted) {
		// TODO Implement method
		return null;
	}

	@Override
	public List<MessageEntity> getBySenderId(Long senderId, boolean archived, boolean deleted) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MessageEntity> criteriaQuery = criteriaBuilder.createQuery(MessageEntity.class);
		Root<MessageEntity> root = criteriaQuery.from(MessageEntity.class);
		Predicate predicate1 = criteriaBuilder.equal(root.get(MessageEntity_.sender), senderId);
		Predicate predicate2 = criteriaBuilder.equal(root.get(MessageEntity_.archivedBySender), archived);
		Predicate predicate3 = criteriaBuilder.equal(root.get(MessageEntity_.deletedBySender), deleted);
		criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3));
		return getAllByCriteria(criteriaQuery);
	}

	@Override
	public List<MessageEntity> getMessagesReadBefore(Date date, boolean deletedOnly) {
		// TODO Implement method
		return null;
	}

}

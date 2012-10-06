/**
 * Project:   rms-server
 * File:      MessagesServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.dao.MessageRecipentsDao;
import pl.bcichecki.rms.dao.MessagesDao;
import pl.bcichecki.rms.dao.UsersDao;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.MessageEntity;
import pl.bcichecki.rms.model.impl.MessageRecipentEntity;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.MessagesService;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MessagesServiceImpl implements MessagesService {

	@Autowired
	protected MessagesDao messagesDao;

	@Autowired
	protected MessageRecipentsDao messageRecipentsDao;

	@Autowired
	protected UsersDao usersDao;

	@Override
	public boolean createMessage(MessageEntity message) throws ServiceException {
		messagesDao.create(message);
		return true;
	}

	@Override
	public boolean deleteArchivedInboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageRecipentEntity messageRecipent = messageRecipentsDao.getByIdAndRecipentId(id, currentUserId, true, false);
		if (messageRecipent == null) {
			throw new ServiceException("You can't delete message that does not exist!",
			        "exceptions.serviceExceptions.messages.cantDeleteNotExisting");
		}
		messageRecipent.setDeletedByRecipent(true);
		messageRecipentsDao.update(messageRecipent);
		return true;
	}

	@Override
	public boolean deleteArchivedOutboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageEntity message = messagesDao.getByIdAndSenderId(id, currentUserId, true, false);
		if (message == null) {
			throw new ServiceException("You can't delete message that does not exist!",
			        "exceptions.serviceExceptions.messages.cantDeleteNotExisting");
		}
		message.setDeletedBySender(true);
		messagesDao.update(message);
		return true;
	}

	@Override
	public boolean deleteInboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageRecipentEntity messageRecipent = messageRecipentsDao.getByIdAndRecipentId(id, currentUserId, false, false);
		if (messageRecipent == null) {
			throw new ServiceException("You can't delete message that does not exist!",
			        "exceptions.serviceExceptions.messages.cantDeleteNotExisting");
		}
		messageRecipent.setDeletedByRecipent(true);
		messageRecipentsDao.update(messageRecipent);
		return true;
	}

	@Override
	public boolean deleteMessage(Long id) throws ServiceException {
		MessageEntity message = messagesDao.getById(id);
		if (message == null) {
			throw new ServiceException("You can't delete message that does not exist!",
			        "exceptions.serviceExceptions.messages.cantDeleteNotExisting");
		}
		messagesDao.delete(message);
		return true;
	}

	@Override
	public boolean deleteOutboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageEntity message = messagesDao.getByIdAndSenderId(id, currentUserId, false, false);
		if (message == null) {
			throw new ServiceException("You can't delete message that does not exist!",
			        "exceptions.serviceExceptions.messages.cantDeleteNotExisting");
		}
		message.setDeletedBySender(true);
		messagesDao.update(message);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> getAllArchivedInboxMessages() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getByRecipentId(currentUserId, true, false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> getAllArchivedOutboxMessages() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getBySenderId(currentUserId, true, false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> getAllInboxMessages() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getByRecipentId(currentUserId, false, false);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> getAllMessages(boolean idAndVersionOnly) {
		return messagesDao.getAll(idAndVersionOnly);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageEntity> getAllOutboxMessages() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getBySenderId(currentUserId, false, false);
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getArchivedInboxMessage(Long id) {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getByIdAndRecipentId(id, currentUserId, true, false);
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getArchivedOutboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageEntity message = messagesDao.getByIdAndSenderId(id, currentUserId, true, false);
		if (message == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		return message;
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getInboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return messagesDao.getByIdAndRecipentId(id, currentUserId, false, false);
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getMessage(Long id) throws ServiceException {
		MessageEntity message = messagesDao.getById(id);
		if (message == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		return message;
	}

	@Override
	@Transactional(readOnly = true)
	public MessageEntity getOutboxMessage(Long id) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageEntity message = messagesDao.getByIdAndSenderId(id, currentUserId, false, false);
		if (message == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		return message;
	}

	@Override
	public boolean markMessageRead(Long id, boolean isRead) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageRecipentEntity messageRecipent = messageRecipentsDao.getByIdAndRecipentId(id, currentUserId, false, false);
		if (messageRecipent == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		messageRecipent.setReadDate(isRead ? new Date() : null);
		messageRecipentsDao.update(messageRecipent);
		return false;
	}

	@Override
	public boolean sendMessage(MessageEntity message) throws ServiceException {
		if (StringUtils.isBlank(message.getSubject())) {
			throw new ServiceException("You must provide message subject.", "exceptions.serviceExceptions.messages.noSubject");
		}
		if (StringUtils.isBlank(message.getContent())) {
			throw new ServiceException("You must provide message content.", "exceptions.serviceExceptions.messages.noContent");
		}
		message.setSender(SecurityUtils.getCurrentUser());
		message.setArchivedBySender(false);
		message.setDeletedBySender(false);
		message.setDate(new Date());
		if (CollectionUtils.isEmpty(message.getRecipents())) {
			throw new ServiceException("You must provide at least one message recipent.",
			        "exceptions.serviceExceptions.messages.noRecipents");
		}
		for (MessageRecipentEntity messageRecipent : message.getRecipents()) {
			if (messageRecipent.getRecipent() == null) {
				throw new ServiceException("You must provide ID or username of recipent.",
				        "exceptions.serviceExceptions.messages.noRecipentIdNorUsernameProvided");
			}
			if (messageRecipent.getRecipent().getId() != null) {
				UserEntity user = usersDao.getById(messageRecipent.getRecipent().getId());
				if (user == null) {
					throw new ServiceException("Unknown user pointed as message recipent.",
					        "exceptions.serviceExceptions.messages.unknownRecipent");
				}
				messageRecipent.setRecipent(user);
			} else if (messageRecipent.getRecipent().getUsername() != null) {
				UserEntity user = usersDao.getById(messageRecipent.getRecipent().getUsername());
				if (user == null) {
					throw new ServiceException("Unknown user pointed as message recipent.",
					        "exceptions.serviceExceptions.messages.unknownRecipent");
				}
				messageRecipent.setRecipent(user);
			} else {
				throw new ServiceException("You must provide ID or username of recipent.",
				        "exceptions.serviceExceptions.messages.noRecipentIdNorUsernameProvided");
			}
			messageRecipent.setReadDate(null);
			messageRecipent.setArchivedByRecipent(false);
			messageRecipent.setDeletedByRecipent(false);
		}
		messagesDao.create(message);
		return true;
	}

	@Override
	public long shredMessagesReadBefore(Date date, boolean deletedOnly) throws ServiceException {
		List<MessageEntity> messages = messagesDao.getMessagesReadBefore(date, deletedOnly);
		for (MessageEntity message : messages) {
			messagesDao.delete(message);
		}
		return messages.size();
	}

	@Override
	public boolean updateMessage(MessageEntity message) throws ServiceException {
		MessageEntity retrievedMessage = messagesDao.getById(message.getId());
		if (retrievedMessage == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		retrievedMessage.merge(message);
		messagesDao.update(retrievedMessage);
		return true;
	}

	@Override
	public boolean updateOutboxMessage(MessageEntity message) throws ServiceException {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		MessageEntity retrievedMessage = messagesDao.getByIdAndSenderId(message.getId(), currentUserId, false, false);
		if (retrievedMessage == null) {
			throw new ServiceException("Message with this ID does not exist!", "exceptions.serviceExceptions.messages.notExistId");
		}
		for (MessageRecipentEntity messageRecipent : retrievedMessage.getRecipents()) {
			if (messageRecipent.getReadDate() != null) {
				throw new ServiceException("Can't update message that has already been read by any of the recipents!",
				        "exceptions.serviceExceptions.messages.cantUpdateAlreadyRead");
			}
		}
		retrievedMessage.setSubject(message.getSubject());
		retrievedMessage.setContent(message.getContent());
		retrievedMessage.setRecipents(message.getRecipents());
		messagesDao.update(retrievedMessage);
		return true;
	}

}

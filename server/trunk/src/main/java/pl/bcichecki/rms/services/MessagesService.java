/**
 * Project:   rms-server
 * File:      MessagesService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.MessageEntity;

/**
 * @author Bartosz Cichecki
 */
public interface MessagesService {

	boolean archiveMessage(String id) throws ServiceException;

	boolean createMessage(MessageEntity message) throws ServiceException;

	boolean deleteArchivedInboxMessage(String id) throws ServiceException;

	boolean deleteArchivedOutboxMessage(String id) throws ServiceException;

	boolean deleteInboxMessage(String id) throws ServiceException;

	boolean deleteMessage(String id) throws ServiceException;

	boolean deleteOutboxMessage(String id) throws ServiceException;

	List<MessageEntity> getAllArchivedInboxMessages();

	List<MessageEntity> getAllArchivedOutboxMessages();

	List<MessageEntity> getAllInboxMessages();

	List<MessageEntity> getAllMessages();

	List<MessageEntity> getAllOutboxMessages();

	MessageEntity getArchivedInboxMessage(String id);

	MessageEntity getArchivedOutboxMessage(String id) throws ServiceException;

	MessageEntity getInboxMessage(String id) throws ServiceException;

	MessageEntity getMessage(String id) throws ServiceException;

	MessageEntity getOutboxMessage(String id) throws ServiceException;

	boolean markMessageRead(String id, boolean isRead) throws ServiceException;

	boolean sendMessage(MessageEntity message) throws ServiceException;

	long shredMessagesReadBefore(Date date, boolean deletedOnly) throws ServiceException;

	boolean updateMessage(MessageEntity message) throws ServiceException;

	boolean updateOutboxMessage(MessageEntity message) throws ServiceException;

}

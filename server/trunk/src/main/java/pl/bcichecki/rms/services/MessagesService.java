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

	boolean createMessage(MessageEntity message) throws ServiceException;

	boolean deleteArchivedInboxMessage(Long id) throws ServiceException;

	boolean deleteArchivedOutboxMessage(Long id) throws ServiceException;

	boolean deleteInboxMessage(Long id) throws ServiceException;

	boolean deleteMessage(Long id) throws ServiceException;

	boolean deleteOutboxMessage(Long id) throws ServiceException;

	List<MessageEntity> getAllArchivedInboxMessages();

	List<MessageEntity> getAllArchivedOutboxMessages();

	List<MessageEntity> getAllInboxMessages();

	List<MessageEntity> getAllMessages(boolean idAndVersionOnly);

	List<MessageEntity> getAllOutboxMessages();

	MessageEntity getArchivedInboxMessage(Long id);

	MessageEntity getArchivedOutboxMessage(Long id) throws ServiceException;

	MessageEntity getInboxMessage(Long id) throws ServiceException;

	MessageEntity getMessage(Long id) throws ServiceException;

	MessageEntity getOutboxMessage(Long id) throws ServiceException;

	boolean markMessageRead(Long id, boolean isRead) throws ServiceException;

	boolean sendMessage(MessageEntity message) throws ServiceException;

	long shredMessagesReadBefore(Date date, boolean deletedOnly) throws ServiceException;

	boolean updateMessage(MessageEntity message) throws ServiceException;

	boolean updateOutboxMessage(MessageEntity message) throws ServiceException;

}

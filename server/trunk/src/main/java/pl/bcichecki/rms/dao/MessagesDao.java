/**
 * Project:   rms-server
 * File:      MessagesDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.model.impl.MessageEntity;

/**
 * @author Bartosz Cichecki
 */
public interface MessagesDao extends GenericDao<MessageEntity> {

	MessageEntity getByIdAndRecipentId(Serializable id, Serializable recipentId, boolean archived, boolean deleted);

	MessageEntity getByIdAndSenderId(Serializable id, Serializable senderId, boolean archived, boolean deleted);

	List<MessageEntity> getByRecipentId(Long recipentId, boolean archived, boolean deleted);

	List<MessageEntity> getBySenderId(Long senderId, boolean archived, boolean deleted);

	List<MessageEntity> getMessagesReadBefore(Date date, boolean deletedOnly);

}

/**
 * Project:   rms-server
 * File:      MessageRecipentsDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.io.Serializable;

import pl.bcichecki.rms.model.impl.MessageRecipentEntity;

/**
 * @author Bartosz Cichecki
 */
public interface MessageRecipentsDao extends GenericDao<MessageRecipentEntity> {

	MessageRecipentEntity getByIdAndRecipentId(Serializable id, Serializable recipentId, boolean archived, boolean deleted);

}

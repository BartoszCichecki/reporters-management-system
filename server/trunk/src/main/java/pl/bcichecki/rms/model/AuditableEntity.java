/**
 * Project:   Reporters Management System - Server
 * File:      AuditableEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model;

import java.util.Date;

/**
 * @author Bartosz Cichecki
 */
public interface AuditableEntity<T> {

	Date getCreationDate();

	T getCreationUser();

	T getCurrentUser();

	Date getModificationDate();

	T getModificationUser();

	void setCreationDate(Date creationDate);

	void setCreationUser(T creationUser);

	void setModificationDate(Date modificationDate);

	void setModificationUser(T modificationUser);

}

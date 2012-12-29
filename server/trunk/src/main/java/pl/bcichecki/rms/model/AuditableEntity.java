/**
 * Project:   rms-server
 * File:      AuditableEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model;

/**
 * @author Bartosz Cichecki
 */
public interface AuditableEntity<T, Y, Z> {

	Z getCreationDate();

	Y getCreationUserId();

	T getCurrentUser();

	Y getCurrentUserId();

	Z getModificationDate();

	Y getModificationUserId();

	void setCreationDate(Z creationDate);

	void setCreationUserId(Y creationUserId);

	void setModificationDate(Z modificationDate);

	void setModificationUserId(Y modificationUserId);

}

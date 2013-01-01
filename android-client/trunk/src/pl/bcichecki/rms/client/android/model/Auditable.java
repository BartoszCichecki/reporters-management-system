/**
 * Project:   rms-client-android
 * File:      Auditable.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model;

/**
 * @author Bartosz Cichecki
 */
public interface Auditable<T, Y, Z> {

	Z getCreationDate();

	Y getCreationUserId();

	Y getCurrentUserId();

	Z getModificationDate();

	Y getModificationUserId();

	void setCreationDate(Z creationDate);

	void setCreationUserId(Y creationUserId);

	void setModificationDate(Z modificationDate);

	void setModificationUserId(Y modificationUserId);

}

/**
 * Project:   rms-client-android
 * File:      VersionableEntity.java
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
public interface VersionableEntity<T> {

	T getVersion();

	void setVersion(T version);

}

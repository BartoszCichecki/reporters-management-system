/**
 * Project:   rms-client-android
 * File:      Authenticable.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.https;

/**
 * @author Bartosz Cichecki
 * 
 */
interface Authenticable {

	void authenticate();

	boolean isAuthenticated();

}

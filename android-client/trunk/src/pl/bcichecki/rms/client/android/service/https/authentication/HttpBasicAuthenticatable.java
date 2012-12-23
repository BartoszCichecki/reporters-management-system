/**
 * Project:   rms-client-android
 * File:      HttpBasicAuthenticatable.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.service.https.authentication;

/**
 * @author Bartosz Cichecki
 * 
 */
public interface HttpBasicAuthenticatable extends Authenticable {

	String getHost();

	String getPassword();

	int getPort();

	String getRealm();

	String getUsername();

}

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

package pl.bcichecki.rms.client.android.services.clients.restful.https;

/**
 * @author Bartosz Cichecki
 * 
 */
interface HttpBasicAuthenticatable extends Authenticable {

	String getHost();

	String getPassword();

	int getPort();

	String getRealm();

	String getUsername();

	void setHost(String host);

	void setPassword(String password);

	void setPort(int port);

	void setRealm(String realm);

	void setUsername(String username);

}

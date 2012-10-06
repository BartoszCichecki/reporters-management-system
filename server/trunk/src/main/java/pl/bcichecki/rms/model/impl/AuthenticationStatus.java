/**
 * Project:   rms-server
 * File:      AccessStatus.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      27-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum AuthenticationStatus {

	AUTHENTICATION_OK, AUTHENTICATION_BAD_CREDENTIALS, AUTHENTICATION_ACCOUNT_LOCKED, AUTHENTICATION_ACCOUNT_DELETED, AUTHENTICATION_ERROR;

}

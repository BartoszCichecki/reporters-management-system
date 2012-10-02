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
public enum AccessStatus {

	AUTHENTICATION_OK("authentication_ok"), AUTHORIZATION_OK("authorization_ok"), AUTHENTICATION_BAD_CREDENTIALS(
	        "authentication_badCredentials"), AUTHENTICATION_ACCOUNT_LOCKED("authentication_accountLocked"), AUTHORIZATION_UNSUFFICIENT_PRIVILAGES(
	        "authorization_unsufficientPrivilages");

	private String value;

	private AccessStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

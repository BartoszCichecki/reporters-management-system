/**
 * Project: Reporters Management System - Server
 * File:    PrivilegeType.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    13-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum PrivilegeType {

	LOGIN("login"), GET_ALL_USERS("getAllUsers");

	private String value;

	private PrivilegeType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

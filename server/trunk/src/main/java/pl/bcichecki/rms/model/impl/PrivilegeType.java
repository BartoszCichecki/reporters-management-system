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

	ADD_USER("addUser");

	private String value;

	private PrivilegeType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

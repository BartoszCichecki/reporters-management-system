/**
 * Project: Reporters Management System - Server
 * File:    ContactType.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    13-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum ContactType {

	PHONE("phone"), FAX("fax"), EMAIL("email"), FACEBOOK("facebook"), SKYPE("skype");

	private String value;

	private ContactType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}

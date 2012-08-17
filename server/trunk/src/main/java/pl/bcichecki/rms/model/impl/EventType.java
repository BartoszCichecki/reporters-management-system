/**
 * Project: Reporters Management System - Server
 * File:    EventType.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    07-08-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public enum EventType {

	MEETING("meeting"), INTERVIEW("interview");

	private String value;

	private EventType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

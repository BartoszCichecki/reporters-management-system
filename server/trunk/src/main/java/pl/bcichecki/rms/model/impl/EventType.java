/**
 * Project:   rms-server
 * File:      EventType.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Bartosz Cichecki
 */
public enum EventType {

	MEETING("MEETING"), INTERVIEW("INTERVIEW");

	public static EventType fromString(String value) {
		for (EventType eventType : EventType.values()) {
			if (eventType.toString().equalsIgnoreCase(StringUtils.defaultString(value))) {
				return eventType;
			}
		}
		throw new IllegalArgumentException();
	}

	private String value;

	private EventType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

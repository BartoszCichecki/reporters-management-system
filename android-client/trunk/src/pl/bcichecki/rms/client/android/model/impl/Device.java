/**
 * Project:   rms-client-android
 * File:      Device.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class Device extends AbstractPOJO implements Mergeable<Device> {

	private static final long serialVersionUID = -1108300387924092896L;

	protected String name;

	protected String description;

	protected boolean deleted;

	public Device() {
		super();
	}

	public Device(String name, String description, boolean deleted) {
		super();
		this.name = name;
		this.description = description;
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Device other = (Device) obj;
		if (deleted != other.deleted) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void merge(Device device) {
		setName(StringUtils.defaultString(device.getName()));
		setDescription(StringUtils.defaultIfBlank(device.getDescription(), null));
		setDeleted(device.isDeleted());
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Device [name=" + name + ", description=" + description + ", deleted=" + deleted + ", id=" + id + ", creationUserId="
		        + creationUserId + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

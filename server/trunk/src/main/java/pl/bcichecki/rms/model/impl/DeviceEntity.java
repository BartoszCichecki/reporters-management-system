/**
 * Project:   rms-server
 * File:      DeviceEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "DEVICES")
public class DeviceEntity extends AbstractEntity implements Mergeable<DeviceEntity> {

	@Transient
	private static final long serialVersionUID = -1108300387924092896L;

	@Column(name = "NAME", nullable = false, unique = true, length = 250)
	protected String name;

	@Column(name = "DESCRIPTION", nullable = true, unique = false, length = 1000)
	protected String description;

	@Column(name = "DELETED", nullable = false, unique = false)
	protected boolean deleted;

	public DeviceEntity() {
		super();
	}

	public DeviceEntity(String name, String description, boolean deleted) {
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
		DeviceEntity other = (DeviceEntity) obj;
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
	public void merge(DeviceEntity device) {
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
		return "DeviceEntity [name=" + name + ", description=" + description + ", deleted=" + deleted + ", id=" + id + ", creationUserId="
		        + creationUserId + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

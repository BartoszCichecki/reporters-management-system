/**
 * Project:   rms-server
 * File:      EventEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "EVENTS")
public class EventEntity extends AbstractEntity implements Mergeable<EventEntity> {

	@Transient
	private static final long serialVersionUID = -6149510997653672596L;

	@Column(name = "TITLE", nullable = false, unique = false, length = 250)
	protected String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, unique = false)
	protected EventType type;

	@Column(name = "DESCRIPTION", nullable = true, unique = false, length = 10000)
	protected String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false, unique = false)
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE", nullable = false, unique = false)
	protected Date endDate;

	@OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ADDRESS", referencedColumnName = "ID", nullable = false)
	protected AddressDataEntity address;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "EVENTS_USERS_LINK", joinColumns = { @JoinColumn(name = "EVENT_ID", nullable = false, unique = false) },
	        inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, unique = false) })
	protected Set<UserEntity> participants;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "EVENTS_DEVICES_LINK", joinColumns = { @JoinColumn(name = "EVENT_ID", nullable = false, unique = false) },
	        inverseJoinColumns = { @JoinColumn(name = "DEVICE_ID", nullable = false, unique = false) })
	protected Set<DeviceEntity> devices;

	@Column(name = "LOCKED", nullable = false, unique = false)
	protected boolean locked;

	@Column(name = "ARCHIVED", nullable = false, unique = false)
	protected boolean archived;

	@Column(name = "DELETED", nullable = false, unique = false)
	protected boolean deleted;

	public EventEntity() {
		super();
	}

	public EventEntity(String title, EventType type, String description, Date startDate, Date endDate, AddressDataEntity address,
	        Set<UserEntity> participants, Set<DeviceEntity> devices, boolean locked, boolean archived, boolean deleted) {
		super();
		this.title = title;
		this.type = type;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.participants = participants;
		this.devices = devices;
		this.locked = locked;
		this.archived = archived;
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
		EventEntity other = (EventEntity) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (archived != other.archived) {
			return false;
		}
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
		if (devices == null) {
			if (other.devices != null) {
				return false;
			}
		} else if (!devices.equals(other.devices)) {
			return false;
		}
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (locked != other.locked) {
			return false;
		}
		if (participants == null) {
			if (other.participants != null) {
				return false;
			}
		} else if (!participants.equals(other.participants)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	public AddressDataEntity getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public Set<DeviceEntity> getDevices() {
		return devices;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Set<UserEntity> getParticipants() {
		return participants;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTitle() {
		return title;
	}

	public EventType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (archived ? 1231 : 1237);
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (devices == null ? 0 : devices.hashCode());
		result = prime * result + (endDate == null ? 0 : endDate.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + (participants == null ? 0 : participants.hashCode());
		result = prime * result + (startDate == null ? 0 : startDate.hashCode());
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	public boolean isArchived() {
		return archived;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public void merge(EventEntity event) {
		setTitle(StringUtils.defaultString(event.getTitle()));
		setType(event.getType());
		setDescription(StringUtils.defaultIfBlank(event.getDescription(), null));
		setStartDate(event.getStartDate());
		setEndDate(event.getEndDate());
		setAddress(event.getAddress());
		setParticipants(event.getParticipants());
		setDevices(event.getDevices());
		setLocked(event.isLocked());
		setArchived(event.isArchived());
		setDeleted(event.isDeleted());

	}

	public void setAddress(AddressDataEntity address) {
		this.address = address;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDevices(Set<DeviceEntity> devices) {
		this.devices = devices;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setParticipants(Set<UserEntity> participants) {
		this.participants = participants;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EventEntity [title=" + title + ", type=" + type + ", description=" + description + ", startDate=" + startDate
		        + ", endDate=" + endDate + ", address=" + address + ", participants=" + participants + ", devices=" + devices + ", locked="
		        + locked + ", archived=" + archived + ", deleted=" + deleted + ", id=" + id + ", creationUser=" + creationUser
		        + ", modificationUser=" + modificationUser + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate
		        + ", version=" + version + "]";
	}

}

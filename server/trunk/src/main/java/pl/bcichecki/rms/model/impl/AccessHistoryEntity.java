/**
 * Project:   Reporters Management System - Server
 * File:      AccessHistoryEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ACCESS_HISTORY")
public class AccessHistoryEntity extends AbstractEntity implements Mergeable<AccessHistoryEntity> {

	@Transient
	private static final long serialVersionUID = -8583688459079578896L;

	@Column(name = "USERNAME", nullable = true, unique = false)
	protected String username;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCESS_DATE", nullable = false, unique = false)
	protected Date accessDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCESS_STATUS", nullable = false, unique = false)
	protected AccessStatus accessStatus;
	@Column(name = "USER_IP", nullable = true, unique = false)
	protected String ip;

	public AccessHistoryEntity() {
		super();
	}

	public AccessHistoryEntity(String username, Date accessDate, AccessStatus accessStatus, String ip) {
		super();
		this.username = username;
		this.accessDate = accessDate;
		this.accessStatus = accessStatus;
		this.ip = ip;
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
		AccessHistoryEntity other = (AccessHistoryEntity) obj;
		if (accessDate == null) {
			if (other.accessDate != null) {
				return false;
			}
		} else if (!accessDate.equals(other.accessDate)) {
			return false;
		}
		if (accessStatus != other.accessStatus) {
			return false;
		}
		if (ip == null) {
			if (other.ip != null) {
				return false;
			}
		} else if (!ip.equals(other.ip)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public AccessStatus getAccessStatus() {
		return accessStatus;
	}

	public String getIp() {
		return ip;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (accessDate == null ? 0 : accessDate.hashCode());
		result = prime * result + (accessStatus == null ? 0 : accessStatus.hashCode());
		result = prime * result + (ip == null ? 0 : ip.hashCode());
		result = prime * result + (username == null ? 0 : username.hashCode());
		return result;
	}

	@Override
	public void merge(AccessHistoryEntity accessHistory) {
		setUsername(StringUtils.defaultIfBlank(accessHistory.getUsername(), null));
		setAccessDate(accessHistory.getAccessDate());
		setAccessStatus(accessHistory.getAccessStatus());
		setIp(StringUtils.defaultIfBlank(accessHistory.getIp(), null));
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "AccessHistoryEntity [username=" + username + ", accessDate=" + accessDate + ", accessStatus="
				+ accessStatus + ", ip=" + ip + "]";
	}

}

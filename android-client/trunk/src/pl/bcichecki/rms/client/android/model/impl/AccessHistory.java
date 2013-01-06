/**
 * Project:   rms-client-android
 * File:      AccessHistory.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class AccessHistory extends AbstractPOJO implements Serializable, Mergeable<AccessHistory> {

	private static final long serialVersionUID = -8583688459079578896L;

	protected String username;

	protected Date accessDate;

	protected AuthenticationStatus authenticationStatus;

	protected String ip;

	public AccessHistory() {
		super();
	}

	public AccessHistory(String username, Date accessDate, AuthenticationStatus authenticationStatus, String ip) {
		super();
		this.username = username;
		this.accessDate = accessDate;
		this.authenticationStatus = authenticationStatus;
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
		AccessHistory other = (AccessHistory) obj;
		if (accessDate == null) {
			if (other.accessDate != null) {
				return false;
			}
		} else if (!accessDate.equals(other.accessDate)) {
			return false;
		}
		if (authenticationStatus != other.authenticationStatus) {
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

	public AuthenticationStatus getAuthenticationStatus() {
		return authenticationStatus;
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
		result = prime * result + (authenticationStatus == null ? 0 : authenticationStatus.hashCode());
		result = prime * result + (ip == null ? 0 : ip.hashCode());
		result = prime * result + (username == null ? 0 : username.hashCode());
		return result;
	}

	@Override
	public void merge(AccessHistory accessHistory) {
		setUsername(StringUtils.defaultIfBlank(accessHistory.getUsername(), null));
		setAccessDate(accessHistory.getAccessDate());
		setAuthenticationStatus(accessHistory.getAuthenticationStatus());
		setIp(StringUtils.defaultIfBlank(accessHistory.getIp(), null));
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public void setAuthenticationStatus(AuthenticationStatus authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "AccessHistory [username=" + username + ", accessDate=" + accessDate + ", authenticationStatus=" + authenticationStatus
		        + ", ip=" + ip + ", id=" + id + ", creationUserId=" + creationUserId + ", modificationUserId=" + modificationUserId
		        + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + ", version=" + version + "]";
	}

}

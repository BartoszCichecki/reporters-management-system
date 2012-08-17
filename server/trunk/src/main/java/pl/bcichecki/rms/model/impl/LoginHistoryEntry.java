/**
 * Project: Reporters Management System - Server
 * File:    LoginHistoryEntry.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "LOGIN_HISTORY")
public class LoginHistoryEntry extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = -8583688459079578896L;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER", referencedColumnName = "ID", nullable = false)
	protected User user;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE", nullable = false, unique = false)
	protected Date date;
	@Column(name = "LOGIN_SUCCESSFUL", nullable = false, unique = false)
	protected boolean loginSuccessful;
	@Column(name = "LOGOUT_SUCCESSFUL", nullable = false, unique = false)
	protected boolean logoutSuccessful;

	public LoginHistoryEntry() {
		super();
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
		LoginHistoryEntry other = (LoginHistoryEntry) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (loginSuccessful != other.loginSuccessful) {
			return false;
		}
		if (logoutSuccessful != other.logoutSuccessful) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (loginSuccessful ? 1231 : 1237);
		result = prime * result + (logoutSuccessful ? 1231 : 1237);
		result = prime * result + (user == null ? 0 : user.hashCode());
		return result;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public boolean isLogoutSuccessful() {
		return logoutSuccessful;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	public void setLogoutSuccessful(boolean logoutSuccessful) {
		this.logoutSuccessful = logoutSuccessful;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginHistoryEntry [user=" + user + ", date=" + date + ", loginSuccessful=" + loginSuccessful
				+ ", logoutSuccessful=" + logoutSuccessful + ", id=" + id + ", creationUser=" + creationUser
				+ ", modificationUser=" + modificationUser + ", creationDate=" + creationDate + ", modificationDate="
				+ modificationDate + ", version=" + version + "]";
	}

}

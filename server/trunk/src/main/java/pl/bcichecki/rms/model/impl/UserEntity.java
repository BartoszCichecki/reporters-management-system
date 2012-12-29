/**
 * Project:   rms-server
 * File:      UserEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "USERS")
public class UserEntity extends AbstractEntity implements Mergeable<UserEntity> {

	@Transient
	private static final long serialVersionUID = -3895708148603521817L;

	@Column(name = "USERNAME", nullable = false, unique = true, length = 32)
	protected String username;

	@Column(name = "PASSWORD", nullable = false, unique = false)
	protected String password;

	@Column(name = "EMAIL", nullable = false, unique = true)
	protected String email;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROLE", referencedColumnName = "ID", nullable = true)
	protected RoleEntity role;

	@OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ADDRESS", referencedColumnName = "ID", nullable = true, unique = true)
	protected AddressDataEntity address;

	@Column(name = "LOCKED", nullable = false, unique = false)
	protected boolean locked;

	@Column(name = "COMMENT", nullable = true, unique = false, length = 1000)
	protected String comment;

	@Column(name = "DELETED", nullable = false, unique = false)
	protected boolean deleted;

	public UserEntity() {
		super();
	}

	public UserEntity(String username, String password, String email, RoleEntity role, AddressDataEntity address, boolean locked,
	        String comment, boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.address = address;
		this.locked = locked;
		this.comment = comment;
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
		UserEntity other = (UserEntity) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (comment == null) {
			if (other.comment != null) {
				return false;
			}
		} else if (!comment.equals(other.comment)) {
			return false;
		}
		if (deleted != other.deleted) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (locked != other.locked) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
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

	public AddressDataEntity getAddress() {
		return address;
	}

	public String getComment() {
		return comment;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public RoleEntity getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (comment == null ? 0 : comment.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result + (password == null ? 0 : password.hashCode());
		result = prime * result + (role == null ? 0 : role.hashCode());
		result = prime * result + (username == null ? 0 : username.hashCode());
		return result;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public void merge(UserEntity user) {
		setUsername(StringUtils.defaultString(user.getUsername()));
		setPassword(StringUtils.defaultString(user.getPassword()));
		setEmail(StringUtils.defaultString(user.getEmail()));
		setRole(user.getRole());
		setAddress(user.getAddress());
		setComment(StringUtils.defaultIfBlank(user.getComment(), null));
		setLocked(user.isLocked());
		setDeleted(user.isDeleted());
	}

	public void setAddress(AddressDataEntity address) {
		this.address = address;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserEntity [username=" + username + ", password=" + password + ", email=" + email + ", role=" + role + ", address="
		        + address + ", locked=" + locked + ", comment=" + comment + ", deleted=" + deleted + ", id=" + id + ", creationUser="
		        + creationUser + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

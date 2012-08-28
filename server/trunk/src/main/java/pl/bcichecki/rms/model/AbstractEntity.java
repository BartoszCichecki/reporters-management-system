/**
 * Project:   Reporters Management System - Server
 * File:      AbstractEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import pl.bcichecki.rms.model.impl.UserEntity;

/**
 * @author Bartosz Cichecki
 */
@MappedSuperclass
public abstract class AbstractEntity implements AuditableEntity<UserEntity>, Serializable, VersionableEntity<Long> {

	@Transient
	private static final long serialVersionUID = -4067696286632039806L;

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, unique = true)
	protected Long id;
	@ManyToOne
	@JoinColumn(name = "CREATED_BY", nullable = true, unique = false)
	protected UserEntity creationUser;
	@ManyToOne
	@JoinColumn(name = "MODIFIED_BY", nullable = true, unique = false)
	protected UserEntity modificationUser;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", nullable = true, unique = false)
	protected Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICATION_DATE", nullable = true, unique = false)
	protected Date modificationDate;
	@Version
	@Column(name = "VERSION", nullable = false, unique = false)
	protected Long version;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (creationUser == null) {
			if (other.creationUser != null) {
				return false;
			}
		} else if (!creationUser.equals(other.creationUser)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (modificationDate == null) {
			if (other.modificationDate != null) {
				return false;
			}
		} else if (!modificationDate.equals(other.modificationDate)) {
			return false;
		}
		if (modificationUser == null) {
			if (other.modificationUser != null) {
				return false;
			}
		} else if (!modificationUser.equals(other.modificationUser)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public UserEntity getCreationUser() {
		return creationUser;
	}

	@Override
	public UserEntity getCurrentUser() {
		// TODO Get current user from somewhere. Maybe session? Remember about
		// MA!
		return null;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Date getModificationDate() {
		return modificationDate;
	}

	@Override
	public UserEntity getModificationUser() {
		return modificationUser;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
		result = prime * result + (creationUser == null ? 0 : creationUser.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (modificationDate == null ? 0 : modificationDate.hashCode());
		result = prime * result + (modificationUser == null ? 0 : modificationUser.hashCode());
		result = prime * result + (version == null ? 0 : version.hashCode());
		return result;
	}

	@PrePersist
	public void prePersist() {
		setCreationDate(new Date());
		setCreationUser(getCurrentUser());
		preUpdate();
	}

	@PreUpdate
	public void preUpdate() {
		setModificationDate(new Date());
		setModificationUser(getCurrentUser());
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setCreationUser(UserEntity creationUser) {
		this.creationUser = creationUser;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public void setModificationUser(UserEntity modificationUser) {
		this.modificationUser = modificationUser;
	}

}

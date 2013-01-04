/**
 * Project:   rms-server
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
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
@MappedSuperclass
public abstract class AbstractEntity implements AuditableEntity<UserEntity, String, Date>, Serializable, VersionableEntity<Long> {

	@Transient
	private static final long serialVersionUID = -4067696286632039806L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", nullable = false, unique = true)
	protected String id;

	@Column(name = "CREATED_BY_ID", nullable = true, unique = false)
	protected String creationUserId;

	@Column(name = "MODIFIED_BY_ID", nullable = true, unique = false)
	protected String modificationUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", nullable = true, unique = false)
	protected Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICATION_DATE", nullable = true, unique = false)
	protected Date modificationDate;

	@Version
	@Column(name = "VERSION", nullable = false, unique = false)
	protected Long version;

	public AbstractEntity() {
		super();
	}

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
		if (creationUserId == null) {
			if (other.creationUserId != null) {
				return false;
			}
		} else if (!creationUserId.equals(other.creationUserId)) {
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
		if (modificationUserId == null) {
			if (other.modificationUserId != null) {
				return false;
			}
		} else if (!modificationUserId.equals(other.modificationUserId)) {
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
	public String getCreationUserId() {
		return creationUserId;
	}

	@Override
	public UserEntity getCurrentUser() {
		return SecurityUtils.getCurrentUser();
	}

	@Override
	public String getCurrentUserId() {
		return SecurityUtils.getCurrentUserId();
	}

	public String getId() {
		return id;
	}

	@Override
	public Date getModificationDate() {
		return modificationDate;
	}

	@Override
	public String getModificationUserId() {
		return modificationUserId;
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
		result = prime * result + (creationUserId == null ? 0 : creationUserId.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (modificationDate == null ? 0 : modificationDate.hashCode());
		result = prime * result + (modificationUserId == null ? 0 : modificationUserId.hashCode());
		result = prime * result + (version == null ? 0 : version.hashCode());
		return result;
	}

	@PrePersist
	public void prePersist() {
		setCreationDate(new Date());
		setCreationUserId(getCurrentUserId());
		setVersion(null);
		preUpdate();
	}

	@PreUpdate
	public void preUpdate() {
		setModificationDate(new Date());
		setModificationUserId(getCurrentUserId());
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public void setCreationUserId(String creationUser) {
		creationUserId = creationUser;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public void setModificationUserId(String modificationUser) {
		modificationUserId = modificationUser;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}

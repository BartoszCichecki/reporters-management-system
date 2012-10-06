/**
 * Project:   rms-server
 * File:      MessageRecipentEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      16-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "MESSAGE_RECIPENTS")
public class MessageRecipentEntity extends AbstractEntity implements Mergeable<MessageRecipentEntity> {

	@Transient
	private static final long serialVersionUID = 8831025727208405070L;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "MESSAGE", nullable = false, unique = false)
	protected MessageEntity message;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPENT", nullable = false, unique = false)
	protected UserEntity recipent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READ_DATE", nullable = true, unique = false)
	protected Date readDate;

	@Column(name = "ARCHIVED_BY_RECIPENT", nullable = false, unique = false)
	protected boolean archivedByRecipent;

	@Column(name = "DELETED_BY_RECIPENT", nullable = false, unique = false)
	protected boolean deletedByRecipent;

	public MessageRecipentEntity() {
		super();
	}

	public MessageRecipentEntity(MessageEntity message, UserEntity recipent, Date readDate, boolean archivedByRecipent,
	        boolean deletedByRecipent) {
		super();
		this.message = message;
		this.recipent = recipent;
		this.readDate = readDate;
		this.archivedByRecipent = archivedByRecipent;
		this.deletedByRecipent = deletedByRecipent;
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
		MessageRecipentEntity other = (MessageRecipentEntity) obj;
		if (archivedByRecipent != other.archivedByRecipent) {
			return false;
		}
		if (deletedByRecipent != other.deletedByRecipent) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (readDate == null) {
			if (other.readDate != null) {
				return false;
			}
		} else if (!readDate.equals(other.readDate)) {
			return false;
		}
		if (recipent == null) {
			if (other.recipent != null) {
				return false;
			}
		} else if (!recipent.equals(other.recipent)) {
			return false;
		}
		return true;
	}

	public MessageEntity getMessage() {
		return message;
	}

	public Date getReadDate() {
		return readDate;
	}

	public UserEntity getRecipent() {
		return recipent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (archivedByRecipent ? 1231 : 1237);
		result = prime * result + (deletedByRecipent ? 1231 : 1237);
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (readDate == null ? 0 : readDate.hashCode());
		result = prime * result + (recipent == null ? 0 : recipent.hashCode());
		return result;
	}

	public boolean isArchivedByRecipent() {
		return archivedByRecipent;
	}

	public boolean isDeletedByRecipent() {
		return deletedByRecipent;
	}

	@Override
	public void merge(MessageRecipentEntity messageRecipent) {
		setMessage(messageRecipent.getMessage());
		setRecipent(messageRecipent.getRecipent());
		setReadDate(messageRecipent.getReadDate());
		setArchivedByRecipent(messageRecipent.isArchivedByRecipent());
		setDeletedByRecipent(messageRecipent.isDeletedByRecipent());
	}

	public void setArchivedByRecipent(boolean archivedByRecipent) {
		this.archivedByRecipent = archivedByRecipent;
	}

	public void setDeletedByRecipent(boolean deletedByRecipent) {
		this.deletedByRecipent = deletedByRecipent;
	}

	public void setMessage(MessageEntity message) {
		this.message = message;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public void setRecipent(UserEntity recipent) {
		this.recipent = recipent;
	}

	@Override
	public String toString() {
		return "MessageRecipentEntity [message=" + message + ", recipent=" + recipent + ", readDate=" + readDate + ", archivedByRecipent="
		        + archivedByRecipent + ", deletedByRecipent=" + deletedByRecipent + ", id=" + id + ", creationUser=" + creationUser
		        + ", modificationUser=" + modificationUser + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate
		        + ", version=" + version + "]";
	}

}

/**
 * Project:   rms-server
 * File:      MessageEntity.java
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "MESSAGES")
public class MessageEntity extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = 3946742167156181439L;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "SENDER", nullable = false, unique = false)
	protected UserEntity sender;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MESSAGE", referencedColumnName = "ID", nullable = false)
	protected Set<MessageRecipentEntity> recipents;

	@Column(name = "SUBJECT", nullable = false, unique = false, length = 250)
	protected String subject;

	@Column(name = "CONTENT", nullable = false, unique = false, length = 10000)
	protected String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_SENT", nullable = false, unique = false)
	protected Date date;

	@Column(name = "ARCHIVED_BY_SENDER", nullable = false, unique = false)
	protected boolean archivedBySender;

	@Column(name = "DELETED_BY_SENDER", nullable = false, unique = false)
	protected boolean deletedBySender;

	public MessageEntity() {
		super();
	}

	public MessageEntity(UserEntity sender, Set<MessageRecipentEntity> recipents, String subject, String content, Date date,
	        boolean archivedBySender, boolean deletedBySender) {
		super();
		this.sender = sender;
		this.recipents = recipents;
		this.subject = subject;
		this.content = content;
		this.date = date;
		this.archivedBySender = archivedBySender;
		this.deletedBySender = deletedBySender;
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
		MessageEntity other = (MessageEntity) obj;
		if (archivedBySender != other.archivedBySender) {
			return false;
		}
		if (content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!content.equals(other.content)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (deletedBySender != other.deletedBySender) {
			return false;
		}
		if (recipents == null) {
			if (other.recipents != null) {
				return false;
			}
		} else if (!recipents.equals(other.recipents)) {
			return false;
		}
		if (sender == null) {
			if (other.sender != null) {
				return false;
			}
		} else if (!sender.equals(other.sender)) {
			return false;
		}
		if (subject == null) {
			if (other.subject != null) {
				return false;
			}
		} else if (!subject.equals(other.subject)) {
			return false;
		}
		return true;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public Set<MessageRecipentEntity> getRecipents() {
		return recipents;
	}

	public UserEntity getSender() {
		return sender;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (archivedBySender ? 1231 : 1237);
		result = prime * result + (content == null ? 0 : content.hashCode());
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (deletedBySender ? 1231 : 1237);
		result = prime * result + (recipents == null ? 0 : recipents.hashCode());
		result = prime * result + (sender == null ? 0 : sender.hashCode());
		result = prime * result + (subject == null ? 0 : subject.hashCode());
		return result;
	}

	public boolean isArchivedBySender() {
		return archivedBySender;
	}

	public boolean isDeletedBySender() {
		return deletedBySender;
	}

	public void merge(MessageEntity message) {
		setSender(message.getSender());
		setRecipents(message.getRecipents());
		setSubject(StringUtils.defaultString(message.getSubject()));
		setContent(StringUtils.defaultString(message.getContent()));
		setDate(message.getDate());
		setArchivedBySender(message.isArchivedBySender());
		setDeletedBySender(message.isDeletedBySender());
	}

	public void setArchivedBySender(boolean archivedBySender) {
		this.archivedBySender = archivedBySender;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDeletedBySender(boolean deletedBySender) {
		this.deletedBySender = deletedBySender;
	}

	public void setRecipents(Set<MessageRecipentEntity> recipents) {
		this.recipents = recipents;
	}

	public void setSender(UserEntity sender) {
		this.sender = sender;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}

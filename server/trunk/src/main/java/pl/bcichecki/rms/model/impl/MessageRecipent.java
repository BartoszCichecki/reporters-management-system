/**
 * Project: Reporters Management System - Server
 * File:    MessageRecipent.java
 *
 * Author:  Bartosz Cichecki
 * Date:    16-08-2012
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


/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "MESSAGE_RECIPENTS")
public class MessageRecipent extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = 8831025727208405070L;
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "MESSAGE", nullable = false, unique = false)
	protected Message message;
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPENT_USER", nullable = false, unique = false)
	protected User recipent;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "READ_DATE", nullable = true, unique = false)
	protected Date readDate;

	public MessageRecipent() {
		super();
	}

	public MessageRecipent(Message message, User recipent, Date readDate) {
		super();
		this.message = message;
		this.recipent = recipent;
		this.readDate = readDate;
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
		MessageRecipent other = (MessageRecipent) obj;
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

	public Message getMessage() {
		return message;
	}

	public Date getReadDate() {
		return readDate;
	}

	public User getRecipent() {
		return recipent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (readDate == null ? 0 : readDate.hashCode());
		result = prime * result + (recipent == null ? 0 : recipent.hashCode());
		return result;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public void setRecipent(User recipent) {
		this.recipent = recipent;
	}

	@Override
	public String toString() {
		return "MessageRecipent [message=" + message + ", recipent=" + recipent + ", readDate=" + readDate + ", id="
				+ id + ", creationUser=" + creationUser + ", modificationUser=" + modificationUser + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + ", version=" + version + "]";
	}

}

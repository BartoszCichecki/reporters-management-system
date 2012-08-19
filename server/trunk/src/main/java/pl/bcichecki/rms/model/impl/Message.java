/**
 * Project:   Reporters Management System - Server
 * File:      Message.java
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

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "MESSAGES")
public class Message extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = 3946742167156181439L;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "SENDER", nullable = false, unique = false)
	protected User sender;
	@OneToMany(mappedBy = "message")
	protected Set<MessageRecipent> recipents;
	@Column(name = "TOPIC", nullable = false, unique = false, length = 250)
	protected String topic;
	@Column(name = "CONTENT", nullable = false, unique = false, length = 10000)
	protected String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_SENT", nullable = false, unique = false)
	protected Date date;

	public Message() {
		super();
	}

	public Message(User sender, Set<MessageRecipent> recipents, String topic, String content, Date date) {
		super();
		this.sender = sender;
		this.recipents = recipents;
		this.topic = topic;
		this.content = content;
		this.date = date;
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
		Message other = (Message) obj;
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
		if (topic == null) {
			if (other.topic != null) {
				return false;
			}
		} else if (!topic.equals(other.topic)) {
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

	public Set<MessageRecipent> getRecipents() {
		return recipents;
	}

	public User getSender() {
		return sender;
	}

	public String getTopic() {
		return topic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (content == null ? 0 : content.hashCode());
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (recipents == null ? 0 : recipents.hashCode());
		result = prime * result + (sender == null ? 0 : sender.hashCode());
		result = prime * result + (topic == null ? 0 : topic.hashCode());
		return result;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setRecipents(Set<MessageRecipent> recipents) {
		this.recipents = recipents;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", recipents=" + recipents + ", topic=" + topic + ", content=" + content
				+ ", date=" + date + ", id=" + id + ", creationUser=" + creationUser + ", modificationUser="
				+ modificationUser + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate
				+ ", version=" + version + "]";
	}

}

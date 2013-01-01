/**
 * Project:   rms-client-android
 * File:      MessageRecipent.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import java.util.Date;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class MessageRecipent extends AbstractPOJO implements Mergeable<MessageRecipent> {

	private static final long serialVersionUID = 8831025727208405070L;

	protected User recipent;

	protected Date readDate;

	protected boolean archivedByRecipent;

	protected boolean deletedByRecipent;

	public MessageRecipent() {
		super();
	}

	public MessageRecipent(User recipent, Date readDate, boolean archivedByRecipent, boolean deletedByRecipent) {
		super();
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
		MessageRecipent other = (MessageRecipent) obj;
		if (archivedByRecipent != other.archivedByRecipent) {
			return false;
		}
		if (deletedByRecipent != other.deletedByRecipent) {
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
		result = prime * result + (archivedByRecipent ? 1231 : 1237);
		result = prime * result + (deletedByRecipent ? 1231 : 1237);
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
	public void merge(MessageRecipent messageRecipent) {
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

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public void setRecipent(User recipent) {
		this.recipent = recipent;
	}

	@Override
	public String toString() {
		return "MessageRecipent [recipent=" + recipent + ", readDate=" + readDate + ", archivedByRecipent=" + archivedByRecipent
		        + ", deletedByRecipent=" + deletedByRecipent + ", id=" + id + ", creationUserId=" + creationUserId + ", modificationUserId="
		        + modificationUserId + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + ", version=" + version
		        + "]";
	}

}

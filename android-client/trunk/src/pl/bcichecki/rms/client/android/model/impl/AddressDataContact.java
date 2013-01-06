/**
 * Project:   rms-client-android
 * File:      AddressDataContact.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class AddressDataContact extends AbstractPOJO implements Serializable, Mergeable<AddressDataContact> {

	private static final long serialVersionUID = 7766408310811369512L;

	protected ContactType type;

	protected String value;

	public AddressDataContact() {
		super();
	}

	public AddressDataContact(ContactType type, String value) {
		super();
		this.type = type;
		this.value = value;
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
		AddressDataContact other = (AddressDataContact) obj;
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public ContactType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public void merge(AddressDataContact addressDataContact) {
		setType(addressDataContact.getType());
		setValue(StringUtils.defaultString(addressDataContact.getValue()));
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AddressDataContact [type=" + type + ", value=" + value + ", id=" + id + ", creationUserId=" + creationUserId
		        + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

/**
 * Project:   rms-server
 * File:      AddressDataContactEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      13-08-2012
 */

package pl.bcichecki.rms.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "ADDRESS_DATA_CONTACTS")
public class AddressDataContactEntity extends AbstractEntity implements Mergeable<AddressDataContactEntity> {

	@Transient
	private static final long serialVersionUID = 7766408310811369512L;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false, unique = false)
	protected ContactType type;

	@Column(name = "VALUE", nullable = false, unique = false, length = 250)
	protected String value;

	public AddressDataContactEntity() {
		super();
	}

	public AddressDataContactEntity(ContactType type, String value) {
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
		AddressDataContactEntity other = (AddressDataContactEntity) obj;
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
	public void merge(AddressDataContactEntity addressDataContact) {
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
		return "AddressDataContactEntity [type=" + type + ", value=" + value + ", id=" + id + ", creationUserId=" + creationUserId
		        + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

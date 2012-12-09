/**
 * Project:   rms-server
 * File:      AddressDataEntity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.model.AbstractEntity;
import pl.bcichecki.rms.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "ADDRESS_DATA")
public class AddressDataEntity extends AbstractEntity implements Mergeable<AddressDataEntity> {

	@Transient
	private static final long serialVersionUID = 7142910058149199699L;

	@Column(name = "TITLE", nullable = true, unique = false, length = 250)
	protected String title;

	@Column(name = "FIRST_NAME", nullable = true, unique = false, length = 250)
	protected String firstName;

	@Column(name = "MIDDLE_NAME", nullable = true, unique = false, length = 250)
	protected String middleName;

	@Column(name = "LAST_NAME", nullable = true, unique = false, length = 250)
	protected String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", nullable = true, unique = false)
	protected Calendar birthday;

	@Column(name = "STREET", nullable = true, unique = false, length = 250)
	protected String street;

	@Column(name = "STREET_NUMBER", nullable = true, unique = false, length = 10)
	protected String streetNumber;

	@Column(name = "HOUSE_NUMBER", nullable = true, unique = false, length = 10)
	protected String houseNumber;

	@Column(name = "ZIP_CODE", nullable = true, unique = false, length = 10)
	protected String zipCode;

	@Column(name = "CITY", nullable = true, unique = false, length = 50)
	protected String city;

	@Column(name = "COUNTRY", nullable = true, unique = false, length = 50)
	protected String country;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ADDRESS_DATA", referencedColumnName = "ID", nullable = false)
	protected List<AddressDataContactEntity> contacts;

	public AddressDataEntity() {
		super();
	}

	public AddressDataEntity(String title, String firstName, String middleName, String lastName, Calendar birthday, String street,
	        String streetNumber, String houseNumber, String zipCode, String city, String country, List<AddressDataContactEntity> contacts) {
		super();
		this.title = title;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.street = street;
		this.streetNumber = streetNumber;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		this.contacts = contacts;
	}

	public void addContacts(ContactType contactType, String value) {
		if (contacts == null) {
			contacts = new ArrayList<AddressDataContactEntity>();
		}
		contacts.add(new AddressDataContactEntity(contactType, value));
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
		AddressDataEntity other = (AddressDataEntity) obj;
		if (birthday == null) {
			if (other.birthday != null) {
				return false;
			}
		} else if (!birthday.equals(other.birthday)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (contacts == null) {
			if (other.contacts != null) {
				return false;
			}
		} else if (!contacts.equals(other.contacts)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (houseNumber == null) {
			if (other.houseNumber != null) {
				return false;
			}
		} else if (!houseNumber.equals(other.houseNumber)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (middleName == null) {
			if (other.middleName != null) {
				return false;
			}
		} else if (!middleName.equals(other.middleName)) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equals(other.street)) {
			return false;
		}
		if (streetNumber == null) {
			if (other.streetNumber != null) {
				return false;
			}
		} else if (!streetNumber.equals(other.streetNumber)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (zipCode == null) {
			if (other.zipCode != null) {
				return false;
			}
		} else if (!zipCode.equals(other.zipCode)) {
			return false;
		}
		return true;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public String getCity() {
		return city;
	}

	public List<AddressDataContactEntity> getContacts() {
		return contacts;
	}

	@SuppressWarnings("unchecked")
	public List<String> getContacts(ContactType contactType) {
		if (contacts == null) {
			return ListUtils.EMPTY_LIST;
		}
		List<String> values = new ArrayList<String>();
		for (AddressDataContactEntity addressDataContact : contacts) {
			if (addressDataContact.getType().equals(contactType)) {
				values.add(addressDataContact.getValue());
			}
		}
		return values;
	}

	public String getCountry() {
		return country;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getZipCode() {
		return zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (birthday == null ? 0 : birthday.hashCode());
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (contacts == null ? 0 : contacts.hashCode());
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (firstName == null ? 0 : firstName.hashCode());
		result = prime * result + (houseNumber == null ? 0 : houseNumber.hashCode());
		result = prime * result + (lastName == null ? 0 : lastName.hashCode());
		result = prime * result + (middleName == null ? 0 : middleName.hashCode());
		result = prime * result + (street == null ? 0 : street.hashCode());
		result = prime * result + (streetNumber == null ? 0 : streetNumber.hashCode());
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (zipCode == null ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public void merge(AddressDataEntity addressData) {
		setTitle(StringUtils.defaultIfBlank(addressData.getTitle(), null));
		setFirstName(StringUtils.defaultIfBlank(addressData.getFirstName(), null));
		setMiddleName(StringUtils.defaultIfBlank(addressData.getMiddleName(), null));
		setLastName(StringUtils.defaultIfBlank(addressData.getLastName(), null));
		setBirthday(addressData.getBirthday());
		setStreet(StringUtils.defaultIfBlank(addressData.getStreet(), null));
		setStreetNumber(StringUtils.defaultIfBlank(addressData.getStreetNumber(), null));
		setHouseNumber(StringUtils.defaultIfBlank(addressData.getHouseNumber(), null));
		setZipCode(StringUtils.defaultIfBlank(addressData.getZipCode(), null));
		setCity(StringUtils.defaultIfBlank(addressData.getCity(), null));
		setCountry(StringUtils.defaultIfBlank(addressData.getCountry(), null));
		setContacts(addressData.getContacts());
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContacts(List<AddressDataContactEntity> contacts) {
		this.contacts = contacts;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "AddressDataEntity [title=" + title + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
		        + ", birthday=" + birthday + ", street=" + street + ", streetNumber=" + streetNumber + ", houseNumber=" + houseNumber
		        + ", zipCode=" + zipCode + ", city=" + city + ", country=" + country + ", contacts=" + contacts + ", id=" + id
		        + ", creationUser=" + creationUser + ", modificationUser=" + modificationUser + ", creationDate=" + creationDate
		        + ", modificationDate=" + modificationDate + ", version=" + version + "]";
	}

}

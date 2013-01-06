/**
 * Project:   rms-client-android
 * File:      AddressData.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class AddressData extends AbstractPOJO implements Serializable, Mergeable<AddressData> {

	private static final long serialVersionUID = 7142910058149199699L;

	protected String title;

	protected String firstName;

	protected String middleName;

	protected String lastName;

	protected Calendar birthday;

	protected String street;

	protected String streetNumber;

	protected String houseNumber;

	protected String zipCode;

	protected String city;

	protected String country;

	protected List<AddressDataContact> contacts;

	public AddressData() {
		super();
	}

	public AddressData(String title, String firstName, String middleName, String lastName, Calendar birthday, String street,
	        String streetNumber, String houseNumber, String zipCode, String city, String country, List<AddressDataContact> contacts) {
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
			contacts = new ArrayList<AddressDataContact>();
		}
		contacts.add(new AddressDataContact(contactType, value));
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
		AddressData other = (AddressData) obj;
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

	public List<AddressDataContact> getContacts() {
		return contacts;
	}

	public List<String> getContacts(ContactType contactType) {
		if (contacts == null) {
			return new ArrayList<String>();
		}
		List<String> values = new ArrayList<String>();
		for (AddressDataContact addressDataContact : contacts) {
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
	public void merge(AddressData addressData) {
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

	public void setContacts(List<AddressDataContact> contacts) {
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
		return "AddressData [title=" + title + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
		        + ", birthday=" + birthday + ", street=" + street + ", streetNumber=" + streetNumber + ", houseNumber=" + houseNumber
		        + ", zipCode=" + zipCode + ", city=" + city + ", country=" + country + ", contacts=" + contacts + ", id=" + id
		        + ", creationUserId=" + creationUserId + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate
		        + ", modificationDate=" + modificationDate + ", version=" + version + "]";
	}

}

/**
 * Project:   Reporters Management System - Server
 * File:      AddressData.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "ADDRESS_DATA")
public class AddressData extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = 7142910058149199699L;

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
	protected Set<AddressDataContact> contacts;

	public AddressData() {
		super();
	}

	public AddressData(String street, String streetNumber, String houseNumber, String zipCode, String city,
			String country, Set<AddressDataContact> contacts) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
		this.contacts = contacts;
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
		AddressData other = (AddressData) obj;
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
		if (houseNumber == null) {
			if (other.houseNumber != null) {
				return false;
			}
		} else if (!houseNumber.equals(other.houseNumber)) {
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
		if (zipCode == null) {
			if (other.zipCode != null) {
				return false;
			}
		} else if (!zipCode.equals(other.zipCode)) {
			return false;
		}
		return true;
	}

	public String getCity() {
		return city;
	}

	public Set<AddressDataContact> getContacts() {
		return contacts;
	}

	public String getCountry() {
		return country;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (contacts == null ? 0 : contacts.hashCode());
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (houseNumber == null ? 0 : houseNumber.hashCode());
		result = prime * result + (street == null ? 0 : street.hashCode());
		result = prime * result + (streetNumber == null ? 0 : streetNumber.hashCode());
		result = prime * result + (zipCode == null ? 0 : zipCode.hashCode());
		return result;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setContacts(Set<AddressDataContact> contacts) {
		this.contacts = contacts;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "AddressData [street=" + street + ", streetNumber=" + streetNumber + ", houseNumber=" + houseNumber
				+ ", zipCode=" + zipCode + ", city=" + city + ", country=" + country + ", contacts=" + contacts
				+ ", id=" + id + ", creationUser=" + creationUser + ", modificationUser=" + modificationUser
				+ ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + ", version=" + version
				+ "]";
	}

}

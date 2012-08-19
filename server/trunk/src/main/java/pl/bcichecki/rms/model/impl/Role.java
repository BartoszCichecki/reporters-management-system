/**
 * Project:   Reporters Management System - Server
 * File:      Role.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-08-2012
 */

package pl.bcichecki.rms.model.impl;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
@Entity
@Table(name = "ROLES")
public class Role extends AbstractEntity {

	@Transient
	private static final long serialVersionUID = -113983953800149169L;

	@Column(name = "NAME", nullable = false, unique = true, length = 50)
	protected String name;
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@JoinTable(name = "PRIVILEGES", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, unique = false) },
			uniqueConstraints = { @UniqueConstraint(columnNames = { "ROLE_ID", "PRIVILEGE" }) })
	@Column(name = "PRIVILEGE", nullable = false, unique = false)
	protected Set<PrivilegeType> privileges;

	public Role() {
		super();
	}

	public Role(String name, Set<PrivilegeType> privileges) {
		super();
		this.name = name;
		this.privileges = privileges;
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
		Role other = (Role) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (privileges == null) {
			if (other.privileges != null) {
				return false;
			}
		} else if (!privileges.equals(other.privileges)) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public Set<PrivilegeType> getPrivileges() {
		return privileges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (privileges == null ? 0 : privileges.hashCode());
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrivileges(Set<PrivilegeType> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", privileges=" + privileges + ", id=" + id + ", creationUser=" + creationUser
				+ ", modificationUser=" + modificationUser + ", creationDate=" + creationDate + ", modificationDate="
				+ modificationDate + ", version=" + version + "]";
	}

}

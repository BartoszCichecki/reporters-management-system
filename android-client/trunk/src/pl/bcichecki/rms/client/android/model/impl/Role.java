/**
 * Project:   rms-client-android
 * File:      Role.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.model.impl;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import pl.bcichecki.rms.client.android.model.AbstractPOJO;
import pl.bcichecki.rms.client.android.model.Mergeable;

/**
 * @author Bartosz Cichecki
 */
public class Role extends AbstractPOJO implements Serializable, Mergeable<Role> {

	private static final long serialVersionUID = -113983953800149169L;

	protected String name;

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

	@Override
	public void merge(Role role) {
		setName(StringUtils.defaultString(role.getName()));
		setPrivileges(role.getPrivileges());
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrivileges(Set<PrivilegeType> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", privileges=" + privileges + ", id=" + id + ", creationUserId=" + creationUserId
		        + ", modificationUserId=" + modificationUserId + ", creationDate=" + creationDate + ", modificationDate="
		        + modificationDate + ", version=" + version + "]";
	}

}

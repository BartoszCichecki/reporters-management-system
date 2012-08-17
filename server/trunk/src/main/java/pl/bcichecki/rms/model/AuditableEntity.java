/**
 * Project: Reporters Management System - Server
 * File:    AuditableEntity.java
 *
 * Author:  Bartosz Cichecki
 * Date:    16-08-2012
 */
package pl.bcichecki.rms.model;

import java.util.Date;

/**
 * @author Bartosz Cichecki
 */
public interface AuditableEntity<T> {

	Date getCreationDate();

	T getCreationUser();

	T getCurrentUser();

	Date getModificationDate();

	T getModificationUser();

	void setCreationDate(Date creationDate);

	void setCreationUser(T creationUser);

	void setModificationDate(Date modificationDate);

	void setModificationUser(T modificationUser);

}

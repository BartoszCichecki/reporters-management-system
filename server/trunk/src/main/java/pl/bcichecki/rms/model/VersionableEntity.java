/**
 * Project: Reporters Management System - Server
 * File:    VersionableEntity.java
 *
 * Author:  Bartosz Cichecki
 * Date:    16-08-2012
 */
package pl.bcichecki.rms.model;

/**
 * @author Bartosz Cichecki
 */
public interface VersionableEntity<T> {

	T getVersion();

}

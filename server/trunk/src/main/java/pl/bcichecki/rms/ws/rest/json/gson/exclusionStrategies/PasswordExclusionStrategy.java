/**
 * Project:   rms-server
 * File:      PasswordExclusionStrategy.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-12-2012
 */

package pl.bcichecki.rms.ws.rest.json.gson.exclusionStrategies;

import java.lang.reflect.Field;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * @author Bartosz Cichecki
 */
public class PasswordExclusionStrategy implements ExclusionStrategy {

	private Class<?> clazz;

	private Field field;

	public PasswordExclusionStrategy(Class<?> clazz, Field field) {
		this.clazz = clazz;
		this.field = field;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getDeclaredClass().equals(clazz) && f.getName().equals(field.getName());
	}

}

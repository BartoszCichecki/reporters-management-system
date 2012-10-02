/**
 * Project:   rms-server
 * File:      ResourceBundleUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      27-09-2012
 */

package pl.bcichecki.rms.utils;

import java.util.Locale;

import pl.bcichecki.rms.holders.ApplicationContextHolder;

/**
 * @author Bartosz Cichecki
 */
public class ResourceBundleUtils {

	public static String getValue(String key, Locale locale) {
		return ApplicationContextHolder.getContext().getMessage(key, null, locale);
	}

	public static String getValue(String key, Locale locale, String... variables) {
		return ApplicationContextHolder.getContext().getMessage(key, variables, locale);
	}
}

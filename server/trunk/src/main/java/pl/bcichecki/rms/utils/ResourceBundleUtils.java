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
import java.util.ResourceBundle;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Bartosz Cichecki
 */
public class ResourceBundleUtils implements InitializingBean {

	private static String bundlePath;

	public static String getValue(String key, Locale locale) {
		return ResourceBundle.getBundle(bundlePath, locale).getString(key);
	}

	public static void setBundlePath(String bundlePath) {
		ResourceBundleUtils.bundlePath = bundlePath;
	}

	@Override
	public void afterPropertiesSet() {
		ResourceBundle.getBundle(bundlePath, Locale.getDefault());
	}

}

/**
 * Project: Reporters Management System - Server
 * File:    PropertiesUtil.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    09-08-2012
 */

package pl.bcichecki.rms.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Bartosz Cichecki
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer {

	private static Map<String, String> PROPERTIES;

	public static String getProperty(String name) {
		return PROPERTIES.get(name);
	}

	private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);

		PROPERTIES = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props, springSystemPropertiesMode);
			PROPERTIES.put(keyStr, valueStr);
		}
	}

	@Override
	public void setSystemPropertiesMode(int systemPropertiesMode) {
		super.setSystemPropertiesMode(systemPropertiesMode);
		springSystemPropertiesMode = systemPropertiesMode;
	}
}

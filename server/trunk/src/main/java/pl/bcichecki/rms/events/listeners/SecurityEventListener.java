/**
 * Project:   rms-server
 * File:      SecurityEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-08-2012
 */

package pl.bcichecki.rms.events.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Bartosz Cichecki
 */
public interface SecurityEventListener<E extends ApplicationEvent> extends ApplicationListener<E> {

	static final String ipUnknown = "UNKNOWN";

}

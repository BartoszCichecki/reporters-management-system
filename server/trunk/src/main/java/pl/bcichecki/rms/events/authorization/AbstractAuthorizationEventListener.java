/**
 * Project:   rms-server
 * File:      AbstractAuthorizationEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-08-2012
 */

package pl.bcichecki.rms.events.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.event.AbstractAuthorizationEvent;

import pl.bcichecki.rms.events.SecurityEventListener;
import pl.bcichecki.rms.model.impl.AuthenticationStatus;
import pl.bcichecki.rms.model.impl.AuthorizationStatus;
import pl.bcichecki.rms.services.AccessHistoryService;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractAuthorizationEventListener<E extends AbstractAuthorizationEvent> implements SecurityEventListener<E> {

	@Autowired
	protected AccessHistoryService accessHistoryService;

	protected abstract void log(E event, AuthenticationStatus authenticationStatus, AuthorizationStatus authorizationStatus);

	@Override
	public abstract void onApplicationEvent(E event);

}

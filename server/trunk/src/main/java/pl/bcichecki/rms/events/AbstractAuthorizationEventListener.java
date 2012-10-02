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

package pl.bcichecki.rms.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import pl.bcichecki.rms.model.impl.AccessStatus;
import pl.bcichecki.rms.services.AccessHistoryService;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractAuthorizationEventListener<E extends AbstractAuthorizationEvent> implements SecurityEventListener<E> {

	@Autowired
	protected AccessHistoryService accessHistoryService;

	protected void log(E event, AccessStatus accessStatus) {
		if (event instanceof AuthorizationFailureEvent) {
			String username = ((AuthorizationFailureEvent) event).getAuthentication().getName();
			WebAuthenticationDetails authDetails = (WebAuthenticationDetails) ((AuthorizationFailureEvent) event).getAuthentication()
			        .getDetails();
			String userIp = ipUnknown;
			if (authDetails != null) {
				userIp = ((WebAuthenticationDetails) ((AuthorizationFailureEvent) event).getAuthentication().getDetails())
				        .getRemoteAddress();
			}
			accessHistoryService.logAccess(username, userIp, accessStatus);
		} else if (event instanceof AuthorizedEvent) {
			String username = ((AuthorizedEvent) event).getAuthentication().getName();
			WebAuthenticationDetails authDetails = (WebAuthenticationDetails) ((AuthorizedEvent) event).getAuthentication().getDetails();
			String userIp = ipUnknown;
			if (authDetails != null) {
				userIp = ((WebAuthenticationDetails) ((AuthorizedEvent) event).getAuthentication().getDetails()).getRemoteAddress();
			}
			accessHistoryService.logAccess(username, userIp, accessStatus);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public abstract void onApplicationEvent(E event);

}

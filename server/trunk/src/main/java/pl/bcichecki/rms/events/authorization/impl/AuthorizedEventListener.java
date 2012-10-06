/**
 * Project:   rms-server
 * File:      AuthorizedEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      06-10-2012
 */

package pl.bcichecki.rms.events.authorization.impl;

import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import pl.bcichecki.rms.events.authorization.AbstractAuthorizationEventListener;
import pl.bcichecki.rms.model.impl.AuthenticationStatus;
import pl.bcichecki.rms.model.impl.AuthorizationStatus;

/**
 * @author Bartosz Cichecki
 */
public class AuthorizedEventListener extends AbstractAuthorizationEventListener<AuthorizedEvent> {

	@Override
	protected void log(AuthorizedEvent event, AuthenticationStatus authenticationStatus, AuthorizationStatus authorizationStatus) {
		String username = event.getAuthentication().getName();
		WebAuthenticationDetails authDetails = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		String userIp = ipUnknown;
		if (authDetails != null) {
			userIp = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();
		}
		accessHistoryService.logAccess(username, userIp, authenticationStatus, authorizationStatus);
	}

	@Override
	public void onApplicationEvent(AuthorizedEvent event) {
		log(event, AuthenticationStatus.AUTHENTICATION_OK, AuthorizationStatus.AUTHORIZATION_OK);
	}

}

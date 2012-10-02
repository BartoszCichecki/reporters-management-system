/**
 * Project:   rms-server
 * File:      AbstractAuthenticationEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      27-08-2012
 */

package pl.bcichecki.rms.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import pl.bcichecki.rms.model.impl.AccessStatus;
import pl.bcichecki.rms.services.AccessHistoryService;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractAuthenticationEventListener<E extends AbstractAuthenticationEvent> implements SecurityEventListener<E> {

	@Autowired
	protected AccessHistoryService accessHistoryService;

	protected void log(E event, AccessStatus accessStatus) {
		String username = event.getAuthentication().getName();
		WebAuthenticationDetails authDetails = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		String userIp = ipUnknown;
		if (authDetails != null) {
			userIp = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();
		}
		accessHistoryService.logAccess(username, userIp, accessStatus);
	}

	@Override
	public abstract void onApplicationEvent(E event);

}

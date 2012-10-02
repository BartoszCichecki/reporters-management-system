/**
 * Project:   rms-server
 * File:      BadCredentialsAuthenticationEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-08-2012
 */

package pl.bcichecki.rms.events.impl;

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;

import pl.bcichecki.rms.events.AbstractAuthenticationEventListener;
import pl.bcichecki.rms.model.impl.AccessStatus;

/**
 * @author Bartosz Cichecki
 */
public class BadCredentialsAuthenticationEventListener extends
        AbstractAuthenticationEventListener<AuthenticationFailureBadCredentialsEvent> {

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		log(event, AccessStatus.AUTHENTICATION_BAD_CREDENTIALS);
	}

}

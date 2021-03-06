/**
 * Project:   rms-server
 * File:      SuccessfulAuthenticationEventListener.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-08-2012
 */

package pl.bcichecki.rms.events.authentication.impl;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import pl.bcichecki.rms.events.authentication.AbstractAuthenticationEventListener;
import pl.bcichecki.rms.model.impl.AuthenticationStatus;

/**
 * @author Bartosz Cichecki
 */
public class SuccessfulAuthenticationEventListener extends AbstractAuthenticationEventListener<AuthenticationSuccessEvent> {

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		log(event, AuthenticationStatus.AUTHENTICATION_OK);
	}
}

/**
 * Project:   rms-client-android
 * File:      ServiceException.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.exceptions.impl;

import pl.bcichecki.rms.client.android.exceptions.AbstractWithExceptionCodeException;

/**
 * @author Bartosz Cichecki
 */
public class ServiceException extends AbstractWithExceptionCodeException {

	private static final long serialVersionUID = 5976482553159149194L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, String exceptionCode) {
		super(message, exceptionCode);
	}

	public ServiceException(String message, String exceptionCode, Throwable cause) {
		super(message, exceptionCode, cause);
	}

	public ServiceException(String exceptionCode, Throwable cause) {
		super(exceptionCode, cause);
	}

}

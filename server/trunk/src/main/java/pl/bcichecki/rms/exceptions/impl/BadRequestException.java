/**
 * Project:   rms-server
 * File:      BadRequestException.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-09-2012
 */

package pl.bcichecki.rms.exceptions.impl;

import pl.bcichecki.rms.exceptions.AbstractWithExceptionCodeException;

/**
 * @author Bartosz Cichecki
 */
public class BadRequestException extends AbstractWithExceptionCodeException {

	private static final long serialVersionUID = 5976482553159149194L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message, String exceptionCode) {
		super(message, exceptionCode);
	}

	public BadRequestException(String message, String exceptionCode, Throwable cause) {
		super(message, exceptionCode, cause);
	}

	public BadRequestException(String message, String exceptionCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, exceptionCode, cause, enableSuppression, writableStackTrace);
	}

	public BadRequestException(String exceptionCode, Throwable cause) {
		super(exceptionCode, cause);
	}

}

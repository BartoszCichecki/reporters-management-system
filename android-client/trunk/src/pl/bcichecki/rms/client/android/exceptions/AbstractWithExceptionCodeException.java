/**
 * Project:   rms-client-android
 * File:      AbstractWithExceptionCodeException.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-12-2012
 */

package pl.bcichecki.rms.client.android.exceptions;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractWithExceptionCodeException extends Exception {

	private static final long serialVersionUID = 4359967406680811977L;

	private String exceptionCode;

	public AbstractWithExceptionCodeException() {
		super();
	}

	public AbstractWithExceptionCodeException(String message, String exceptionCode) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public AbstractWithExceptionCodeException(String message, String exceptionCode, Throwable cause) {
		super(message, cause);
		this.exceptionCode = exceptionCode;
	}

	public AbstractWithExceptionCodeException(String exceptionCode, Throwable cause) {
		super(cause);
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}
}

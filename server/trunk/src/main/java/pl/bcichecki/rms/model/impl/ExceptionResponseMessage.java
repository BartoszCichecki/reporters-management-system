/**
 * Project:   rms-server
 * File:      ExceptionResponseMessage.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.model.impl;

/**
 * @author Bartosz Cichecki
 */
public class ExceptionResponseMessage {

	private Class<?> exceptionClass;

	private String exceptionMessage;

	private String message;

	private String code;

	public ExceptionResponseMessage() {
		super();
	}

	public ExceptionResponseMessage(Class<?> exceptionClass, String exceptionMessage, String message, String code) {
		super();
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
		this.message = message;
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExceptionResponseMessage other = (ExceptionResponseMessage) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (exceptionClass == null) {
			if (other.exceptionClass != null) {
				return false;
			}
		} else if (!exceptionClass.equals(other.exceptionClass)) {
			return false;
		}
		if (exceptionMessage == null) {
			if (other.exceptionMessage != null) {
				return false;
			}
		} else if (!exceptionMessage.equals(other.exceptionMessage)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		return true;
	}

	public String getCode() {
		return code;
	}

	public Class<?> getExceptionClass() {
		return exceptionClass;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (code == null ? 0 : code.hashCode());
		result = prime * result + exceptionClass.toString().hashCode();
		result = prime * result + (exceptionMessage == null ? 0 : exceptionMessage.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		return result;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setExceptionClass(Class<?> exceptionClazz) {
		exceptionClass = exceptionClazz;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public void setMessage(String localizedMessage) {
		message = localizedMessage;
	}

	@Override
	public String toString() {
		return "ExceptionResponseMessage [exceptionClass=" + exceptionClass + ", exceptionMessage=" + exceptionMessage + ", message="
		        + message + ", code=" + code + "]";
	}

}

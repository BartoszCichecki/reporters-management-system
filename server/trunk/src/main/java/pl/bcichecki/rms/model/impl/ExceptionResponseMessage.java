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

	private String customMessage;

	private String customCode;

	public ExceptionResponseMessage() {
		super();
	}

	public ExceptionResponseMessage(Class<?> exceptionClass, String exceptionMessage, String customMessage, String code) {
		super();
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
		this.customMessage = customMessage;
		this.customCode = code;
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
		if (customCode == null) {
			if (other.customCode != null) {
				return false;
			}
		} else if (!customCode.equals(other.customCode)) {
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
		if (customMessage == null) {
			if (other.customMessage != null) {
				return false;
			}
		} else if (!customMessage.equals(other.customMessage)) {
			return false;
		}
		return true;
	}

	public String getCustomCode() {
		return customCode;
	}

	public String getCustomMessage() {
		return customMessage;
	}

	public Class<?> getExceptionClass() {
		return exceptionClass;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (customCode == null ? 0 : customCode.hashCode());
		result = prime * result + exceptionClass.toString().hashCode();
		result = prime * result + (exceptionMessage == null ? 0 : exceptionMessage.hashCode());
		result = prime * result + (customMessage == null ? 0 : customMessage.hashCode());
		return result;
	}

	public void setCustomCode(String code) {
		this.customCode = code;
	}

	public void setCustomMessage(String localizedMessage) {
		customMessage = localizedMessage;
	}

	public void setExceptionClass(Class<?> exceptionClazz) {
		exceptionClass = exceptionClazz;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ExceptionResponseMessage [exceptionClass=" + exceptionClass + ", exceptionMessage=" + exceptionMessage + ", customMessage="
		        + customMessage + ", customCode=" + customCode + "]";
	}

}

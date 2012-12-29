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

import java.util.Locale;

/**
 * @author Bartosz Cichecki
 */
public class ExceptionResponseMessage {

	private String exceptionClassName;

	private String exceptionMessage;

	private String customMessage;

	private String customCode;

	private Locale customMessageLocale;

	public ExceptionResponseMessage() {
		super();
	}

	public ExceptionResponseMessage(String exceptionClassName, String exceptionMessage, String customMessage, Locale customMessageLocale,
	        String customCode) {
		super();
		this.exceptionClassName = exceptionClassName;
		this.exceptionMessage = exceptionMessage;
		this.customMessage = customMessage;
		this.customMessageLocale = customMessageLocale;
		this.customCode = customCode;
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
		if (customMessage == null) {
			if (other.customMessage != null) {
				return false;
			}
		} else if (!customMessage.equals(other.customMessage)) {
			return false;
		}
		if (customMessageLocale == null) {
			if (other.customMessageLocale != null) {
				return false;
			}
		} else if (!customMessageLocale.equals(other.customMessageLocale)) {
			return false;
		}
		if (exceptionClassName == null) {
			if (other.exceptionClassName != null) {
				return false;
			}
		} else if (!exceptionClassName.equals(other.exceptionClassName)) {
			return false;
		}
		if (exceptionMessage == null) {
			if (other.exceptionMessage != null) {
				return false;
			}
		} else if (!exceptionMessage.equals(other.exceptionMessage)) {
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

	public Locale getCustomMessageLocale() {
		return customMessageLocale;
	}

	public String getExceptionClassName() {
		return exceptionClassName;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (customCode == null ? 0 : customCode.hashCode());
		result = prime * result + (customMessage == null ? 0 : customMessage.hashCode());
		result = prime * result + (customMessageLocale == null ? 0 : customMessageLocale.hashCode());
		result = prime * result + (exceptionClassName == null ? 0 : exceptionClassName.hashCode());
		result = prime * result + (exceptionMessage == null ? 0 : exceptionMessage.hashCode());
		return result;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public void setCustomMessageLocale(Locale customMessageLocale) {
		this.customMessageLocale = customMessageLocale;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ExceptionResponseMessage [exceptionClassName=" + exceptionClassName + ", exceptionMessage=" + exceptionMessage
		        + ", customMessage=" + customMessage + ", customCode=" + customCode + ", customMessageLocale=" + customMessageLocale + "]";
	}

}

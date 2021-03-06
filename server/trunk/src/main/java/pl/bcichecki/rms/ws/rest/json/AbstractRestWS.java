/**
 * Project:   rms-server
 * File:      AbstractRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      26-09-2012
 */

package pl.bcichecki.rms.ws.rest.json;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.exceptions.AbstractWithExceptionCodeException;
import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.ExceptionResponseMessage;
import pl.bcichecki.rms.utils.ResourceBundleUtils;
import pl.bcichecki.rms.ws.rest.json.gson.GsonHolder;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractRestWS {

	private static final Logger log = LoggerFactory.getLogger(AbstractRestWS.class);

	protected String getMessage(Exception ex, Locale locale) {
		ExceptionResponseMessage message = new ExceptionResponseMessage();
		message.setExceptionClassName(ex.getClass().getSimpleName());
		message.setExceptionMessage(ex.getMessage());
		if (ex instanceof AbstractWithExceptionCodeException
		        && !StringUtils.isBlank(((AbstractWithExceptionCodeException) ex).getExceptionCode())) {
			message.setCustomCode(((AbstractWithExceptionCodeException) ex).getExceptionCode());
			message.setCustomMessage(ResourceBundleUtils.getValue(((AbstractWithExceptionCodeException) ex).getExceptionCode(), locale));
			message.setCustomMessageLocale(locale);
		}
		return GsonHolder.getGson().toJson(message);
	}

	protected String getMessage(Exception ex, String customCode, Locale locale) {
		return getMessage(ex, customCode, ResourceBundleUtils.getValue(customCode, locale), locale);
	}

	protected String getMessage(Exception ex, String customCode, String customMessage, Locale locale) {
		ExceptionResponseMessage message = new ExceptionResponseMessage();
		message.setExceptionClassName(ex.getClass().getSimpleName());
		message.setExceptionMessage(ex.getMessage());
		message.setCustomCode(customCode);
		message.setCustomMessage(customMessage);
		message.setCustomMessageLocale(locale);
		return GsonHolder.getGson().toJson(message);
	}

	@ExceptionHandler({ BadRequestException.class, MissingServletRequestParameterException.class })
	@ResponseBody
	String handleBadRequestExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.info(ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		String responseBody = getMessage(ex, request.getLocale());
		RestUtils.decorateResponseHeaderWithMD5(response, responseBody);
		RestUtils.decorateResponseHeaderForJsonContentType(response);
		return responseBody;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	String handleOtherExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.error("Internal server error!", ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		String responseBody = getMessage(ex, "exceptions.internalServerError", request.getLocale());
		RestUtils.decorateResponseHeaderWithMD5(response, responseBody);
		RestUtils.decorateResponseHeaderForJsonContentType(response);
		return responseBody;
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	String handleServiceExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.info(ex.getMessage(), ex);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String responseBody = getMessage(ex, request.getLocale());
		RestUtils.decorateResponseHeaderWithMD5(response, responseBody);
		RestUtils.decorateResponseHeaderForJsonContentType(response);
		return responseBody;
	}

}

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.exceptions.AbstractWithExceptionCodeException;
import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.utils.ResourceBundleUtils;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

import com.google.gson.Gson;

/**
 * @author Bartosz Cichecki
 */
public abstract class AbstractRestWS {

	private static final Logger log = LoggerFactory.getLogger(AbstractRestWS.class);

	private final Gson gson = new Gson();

	protected final Gson getGson() {
		return gson;
	}

	private String getMessage(Exception ex, Locale locale) {
		if (ex instanceof AbstractWithExceptionCodeException && ((AbstractWithExceptionCodeException) ex).getExceptionCode() != null
		        && !StringUtils.isBlank(((AbstractWithExceptionCodeException) ex).getExceptionCode())) {
			return ResourceBundleUtils.getValue(((AbstractWithExceptionCodeException) ex).getExceptionCode(), locale);
		}
		return ex.getMessage();
	}

	@ExceptionHandler({ BadRequestException.class })
	@ResponseBody
	String handleBadRequestExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.info(ex.getMessage(), ex);

		RestUtils.decorateResponseHeaderForText(response);
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return getMessage(ex, request.getLocale());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	String handleOtherExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.error("Internal server error!", ex);

		RestUtils.decorateResponseHeaderForText(response);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return "Internal server error!";
	}

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	String handleServiceExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		log.info(ex.getMessage(), ex);

		RestUtils.decorateResponseHeaderForText(response);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return getMessage(ex, request.getLocale());
	}

}

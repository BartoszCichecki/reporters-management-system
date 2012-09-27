/**
 * Project:   rms-server
 * File:      RestUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.ws.rest.json.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
public class RestUtils {

	private static final String CONTENT_MD5 = "Content-MD5";
	private static final String ENCODING_UTF8 = "UTF-8";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_APPLICATION_JSON = "application/json";
	private static final String CONTENT_TEXT_PLAIN = "text/plain";

	private static boolean hashResponseBody;
	private static boolean checkRequestBodyHash;

	private static boolean checkRequestBodyMD5(HttpServletRequest request, String requestBody) {
		if (!checkRequestBodyHash) {
			return true;
		}
		String headerMd5 = request.getHeader(CONTENT_MD5);
		if (headerMd5 == null) {
			return true;
		}
		return headerMd5.equals(SecurityUtils.hashMD5(requestBody));
	}

	public static void decorateResponseHeaderForJson(HttpServletResponse response) {
		response.setCharacterEncoding(ENCODING_UTF8);
		response.setHeader(CONTENT_TYPE, CONTENT_APPLICATION_JSON);
	}

	public static void decorateResponseHeaderForText(HttpServletResponse response) {
		response.setCharacterEncoding(ENCODING_UTF8);
		response.setHeader(CONTENT_TYPE, CONTENT_TEXT_PLAIN);
	}

	public static void decorateResponseHeaderWithMD5(HttpServletResponse response, String responseBody) {
		if (hashResponseBody) {
			response.setHeader(CONTENT_MD5, SecurityUtils.hashMD5(responseBody));
		}
	}

	public static String getRequestBody(HttpServletRequest request) throws BadRequestException {
		try {
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			String requestBody = sb.toString();
			if (!checkRequestBodyMD5(request, requestBody)) {
				throw new BadRequestException("Request body hash check failed!",
						"exceptions.badRequestExceptions.requestBodyHashFail");
			}

			return requestBody;
		} catch (IOException ex) {
			throw new BadRequestException("Can't get request body!",
					"exceptions.badRequestExceptions.cantGetRequestBody", ex);
		}
	}

	public static void setCheckRequestBodyHash(boolean checkRequestBodyHash) {
		RestUtils.checkRequestBodyHash = checkRequestBodyHash;
	}

	public static void setHashResponseBody(boolean hashResponseBody) {
		RestUtils.hashResponseBody = hashResponseBody;
	}
}

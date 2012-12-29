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
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.utils.SecurityUtils;

/**
 * @author Bartosz Cichecki
 */
public class RestUtils {

	private static final String CONTENT_TYPE = "content-type";

	private static final String CONTENT_MD5 = "Content-MD5";

	public static final String CONTENT_APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

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
		try {
			return headerMd5.equals(SecurityUtils.hashMD5Base64(requestBody));
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException("This system is unable to hash message body properly.", ex);
		}
	}

	public static void decorateResponseHeaderForJsonContentType(HttpServletResponse response) {
		response.setHeader(CONTENT_TYPE, CONTENT_APPLICATION_JSON_UTF8);
	}

	public static void decorateResponseHeaderWithMD5(HttpServletResponse response, String responseBody) {
		if (hashResponseBody) {
			try {
				response.setHeader(CONTENT_MD5, SecurityUtils.hashMD5Base64(responseBody));
			} catch (UnsupportedEncodingException ex) {
				throw new IllegalStateException("This system is unable to hash message body properly.", ex);
			}
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
				throw new BadRequestException("Request body hash check failed!", "exceptions.badRequestExceptions.requestBodyHashFail");
			}

			return requestBody;
		} catch (IOException ex) {
			throw new BadRequestException("Can't get request body!", "exceptions.badRequestExceptions.cantGetRequestBody", ex);
		}
	}

	public static void setCheckRequestBodyHash(boolean checkRequestBodyHash) {
		RestUtils.checkRequestBodyHash = checkRequestBodyHash;
	}

	public static void setHashResponseBody(boolean hashResponseBody) {
		RestUtils.hashResponseBody = hashResponseBody;
	}
}

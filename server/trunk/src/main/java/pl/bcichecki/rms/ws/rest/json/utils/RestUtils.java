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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;

/**
 * @author Bartosz Cichecki
 */
public class RestUtils {

	public static void decorateHeaderForJson(HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
	}

	public static void decorateHeaderForText(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/plain");
	}

	public static String getRequestBody(ServletRequest request) throws BadRequestException {
		try {
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (IOException ex) {
			throw new BadRequestException("Can't get request body!",
					"exceptions.badRequestExceptions.cantGetRequestBody", ex);
		}
	}

}

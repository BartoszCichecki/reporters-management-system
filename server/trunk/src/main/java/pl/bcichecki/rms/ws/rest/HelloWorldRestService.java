/**
 * Project:   Reporters Management System - Server
 * File:      HelloWorldService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      09-08-2012
 */

package pl.bcichecki.rms.ws.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Bartosz Cichecki
 */
@Controller
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class HelloWorldRestService {

	private static Logger log = LoggerFactory.getLogger(HelloWorldRestService.class);

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	String handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return ex.getMessage();
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody
	String sayHello() {
		log.info("hello");
		return "hello";
	}

	@RequestMapping(value = "/err", method = RequestMethod.GET)
	public @ResponseBody
	String sendError() throws Exception {
		throw new Exception("hurray!");
	}
}

/**
 * Project: Reporters Management System - Server
 * File:    HelloWorldService.java
 * 
 * Author:  Bartosz Cichecki
 * Date:    09-08-2012
 */

package pl.bcichecki.rms.ws.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Bartosz Cichecki
 */
@Controller
public class HelloWorldService {

	private static Logger log = LoggerFactory.getLogger(HelloWorldService.class);

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	String handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return ex.getMessage();
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody
	String sayHello() {
		log.debug("hello");
		log.info("hello");
		return "hello";
	}

	@RequestMapping(value = "/err", method = RequestMethod.GET)
	public @ResponseBody
	String sendError() throws Exception {
		throw new Exception("hurray!");
	}
}

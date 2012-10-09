/**
 * Project:   rms-server
 * File:      AccessHistoryRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      07-10-2012
 */

package pl.bcichecki.rms.ws.rest.json.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.services.AccessHistoryService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/accessHistory")
public class AccessHistoryRestWS extends AbstractRestWS {

	@Autowired
	private AccessHistoryService accessHistoryService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_ACCESS_HISTORY + "')")
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	void deleteAccessHistory(HttpServletRequest request, HttpServletResponse response,
	        @RequestParam(value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		accessHistoryService.deleteAll(from, till);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_ACCESS_HISTORY + "')")
	@RequestMapping(value = "/ip/{ip}", method = RequestMethod.DELETE)
	void deleteAccessHistoryByIp(HttpServletRequest request, HttpServletResponse response, @PathVariable String ip, @RequestParam(
	        value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		accessHistoryService.deleteAllByIp(ip, from, till);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_ACCESS_HISTORY + "')")
	@RequestMapping(value = "/username/{username}", method = RequestMethod.DELETE)
	void deleteAccessHistoryByUsername(HttpServletRequest request, HttpServletResponse response, @PathVariable String username,
	        @RequestParam(value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		accessHistoryService.deleteAllByUsername(username, from, till);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ACCESS_HISTORY + "')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	String getAccessHistory(HttpServletRequest request, HttpServletResponse response,
	        @RequestParam(value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		String json = getGson().toJson(accessHistoryService.getAll(from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ACCESS_HISTORY + "')")
	@RequestMapping(value = "/ip/{ip}", method = RequestMethod.GET)
	@ResponseBody
	String getAccessHistoryByIp(HttpServletRequest request, HttpServletResponse response, @PathVariable String ip, @RequestParam(
	        value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		String json = getGson().toJson(accessHistoryService.getAllByIp(ip, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ACCESS_HISTORY + "')")
	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
	@ResponseBody
	String getAccessHistoryByUsername(HttpServletRequest request, HttpServletResponse response, @PathVariable String username,
	        @RequestParam(value = "from", required = true) Date from, @RequestParam(value = "till", required = true) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		String json = getGson().toJson(accessHistoryService.getAllByUsername(username, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}
}

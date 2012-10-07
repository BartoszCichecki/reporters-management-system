/**
 * Project:   rms-server
 * File:      EventsRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      25-09-2012
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
import pl.bcichecki.rms.services.EventsService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/events")
public class EventsRestWS extends AbstractRestWS {

	@Autowired
	protected EventsService eventsService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_DEVICES + "')")
	@RequestMapping(value = "/devices/{deviceId}", method = RequestMethod.GET)
	@ResponseBody
	String getDevicesEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long deviceId, @RequestParam(
	        value = "idAndVersionOnly", required = false, defaultValue = "false") boolean idAndVersionOnly, @RequestParam(
	        value = "eventsFrom", required = false) Date eventsFrom, @RequestParam(value = "eventsTill", required = false) Date eventsTill)
	        throws BadRequestException, ServiceException {
		String json = getGson().toJson(eventsService.getDevicesEvents(deviceId, eventsFrom, eventsTill));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

}

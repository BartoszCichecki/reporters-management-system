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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonParseException;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.EventEntity;
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

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.POST_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	void createEvent(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			EventEntity event = getGson().fromJson(requestBody, EventEntity.class);
			eventsService.createEvent(event);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.POST_EVENTS_TO_WAITING_ROOM + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/waitingRoom", method = RequestMethod.PUT)
	void createEventInWaitingRoom(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			EventEntity event = getGson().fromJson(requestBody, EventEntity.class);
			eventsService.createLockedEvent(event);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.DELETE_MY_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void deleteEvent(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		eventsService.deleteEvent(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/archived/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllArchivedEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllEvents(true, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestParam(value = "from",
	        required = false) Date from, @RequestParam(value = "till", required = false) Date till) throws BadRequestException,
	        ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllEvents(false, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = { "/archived", "/archived/my" }, method = RequestMethod.GET)
	@ResponseBody
	String getCurrentUserArchivedEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllCurrentUserEvent(true, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = { "", "/my" }, method = RequestMethod.GET)
	@ResponseBody
	String getCurrentUserEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllCurrentUserEvent(false, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_DEVICE_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/archived/devices/{deviceId}", method = RequestMethod.GET)
	@ResponseBody
	String getDevicesArchivedEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long deviceId, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		String json = getGson().toJson(eventsService.getDevicesEvents(deviceId, true, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_DEVICE_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/devices/{deviceId}", method = RequestMethod.GET)
	@ResponseBody
	String getDevicesEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long deviceId, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		String json = getGson().toJson(eventsService.getDevicesEvents(deviceId, false, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getEvent(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws BadRequestException,
	        ServiceException {
		String json = getGson().toJson(eventsService.getEvent(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_DEVICE_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/archived/users/{userId}", method = RequestMethod.GET)
	@ResponseBody
	String getUsersArchivedEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllUserEvent(userId, true, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_DEVICE_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	@ResponseBody
	String getUsersEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId, @RequestParam(
	        value = "from", required = false) Date from, @RequestParam(value = "till", required = false) Date till)
	        throws BadRequestException, ServiceException {
		if (from == null || till == null) {
			throw new BadRequestException("You must provide from date and till date!",
			        "exceptions.badRequestExceptions.fromAndTillDateMissing");
		}
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(eventsService.getAllUserEvent(userId, false, from, till));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.UPDATE_MY_EVENTS + "','" + PrivilegeUtils.Values.MANAGE_EVENTS + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	void updateEvent(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			eventsService.updateEvent(getGson().fromJson(requestBody, EventEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

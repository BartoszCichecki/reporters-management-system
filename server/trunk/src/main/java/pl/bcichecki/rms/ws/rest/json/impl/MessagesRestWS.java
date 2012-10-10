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

import java.sql.Date;

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
import pl.bcichecki.rms.model.impl.MessageEntity;
import pl.bcichecki.rms.services.MessagesService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/messages")
public class MessagesRestWS extends AbstractRestWS {

	@Autowired
	protected MessagesService messagesService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	void createMessage(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			messagesService.createMessage(getGson().fromJson(requestBody, MessageEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/inbox/{id}", method = RequestMethod.DELETE)
	void deleteArchivedInboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
	        throws ServiceException {
		messagesService.deleteArchivedInboxMessage(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/outbox/{id}", method = RequestMethod.DELETE)
	void deleteArchivedOutboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
	        throws ServiceException {
		messagesService.deleteArchivedOutboxMessage(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/inbox/{id}", method = RequestMethod.DELETE)
	void deleteInboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		messagesService.deleteInboxMessage(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.DELETE_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void deleteMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		messagesService.deleteMessage(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.CLEAN_UP_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/readBefore/{timestamp}", method = RequestMethod.DELETE)
	@ResponseBody
	String deleteMessageReadBefore(HttpServletRequest request, HttpServletResponse response, @PathVariable Long timestamp, @RequestParam(
	        value = "deletedOnly", required = false, defaultValue = "true") boolean deletedOnly) throws ServiceException {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.shredMessagesReadBefore(new Date(timestamp), deletedOnly));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/outbox/{id}", method = RequestMethod.DELETE)
	void deleteOutboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		messagesService.deleteOutboxMessage(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/inbox/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllArchivedInboxMessages(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.getAllArchivedInboxMessages());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/outbox/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllArchivedOutboxMessages(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.getAllArchivedOutboxMessages());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/inbox/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllInboxMessages(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.getAllInboxMessages());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllMessages(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.getAllMessages());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/outbox/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllOutboxMessages(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(messagesService.getAllOutboxMessages());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/inbox/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getArchivedInboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		String json = getGson().toJson(messagesService.getArchivedInboxMessage(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/archived/outbox/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getArchivedOutboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
	        throws ServiceException {
		String json = getGson().toJson(messagesService.getArchivedOutboxMessage(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/inbox/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getInboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		String json = getGson().toJson(messagesService.getInboxMessage(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		String json = getGson().toJson(messagesService.getMessage(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/outbox/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getOutboxMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException {
		String json = getGson().toJson(messagesService.getOutboxMessage(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/inbox/markRead/{id}", method = RequestMethod.POST)
	void markMessageRead(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws ServiceException,
	        BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			messagesService.markMessageRead(id, true);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.SEND_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/outbox", method = RequestMethod.PUT)
	void sendMessage(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			messagesService.sendMessage(getGson().fromJson(requestBody, MessageEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	void updateMessage(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			messagesService.updateMessage(getGson().fromJson(requestBody, MessageEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.UPDATE_OUTGOING_MESSAGES + "','" + PrivilegeUtils.Values.MANAGE_MESSAGES + "')")
	@RequestMapping(value = "/outbox", method = RequestMethod.POST)
	void updateOutboxMessage(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			messagesService.updateOutboxMessage(getGson().fromJson(requestBody, MessageEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

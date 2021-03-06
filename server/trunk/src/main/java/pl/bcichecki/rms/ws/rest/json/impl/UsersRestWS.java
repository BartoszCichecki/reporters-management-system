/**
 * Project:   rms-server
 * File:      UsersRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-09-2012
 */

package pl.bcichecki.rms.ws.rest.json.impl;

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
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.gson.GsonHolder;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/users")
public class UsersRestWS extends AbstractRestWS {

	@Autowired
	protected UsersService usersService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.CREATE_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	void createUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			UserEntity user = GsonHolder.getGson().fromJson(requestBody, UserEntity.class);
			usersService.createUser(user);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.DELETE_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void deleteUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestParam(value = "markDeleted",
	        required = false, defaultValue = "true") boolean markDeleted) throws ServiceException {
		usersService.deleteUser(id, markDeleted);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_USERS + "','" + PrivilegeUtils.Values.SEND_MESSAGES + "','"
	        + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "/all/active", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getAllActiveUsers(HttpServletRequest request, HttpServletResponse response) {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(usersService.getAllActiveUsers());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getAllUsers(HttpServletRequest request, HttpServletResponse response) {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(usersService.getAllUsers());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws ServiceException {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(usersService.getUserById(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_ROLE_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getUsersWithRole(HttpServletRequest request, HttpServletResponse response, @PathVariable String roleId) throws ServiceException {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(usersService.getUsersWithRole(roleId));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.UPDATE_USERS + "','" + PrivilegeUtils.Values.MANAGE_USERS + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			usersService.updateUser(GsonHolder.getGson().fromJson(requestBody, UserEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonParseException;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.RoleEntity;
import pl.bcichecki.rms.services.RolesService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.gson.GsonHolder;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/roles")
public class RolesRestWS extends AbstractRestWS {

	@Autowired
	protected RolesService rolesService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.CREATE_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	void createRole(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".", "exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			RoleEntity roleEntity = GsonHolder.getGson().fromJson(requestBody, RoleEntity.class);
			rolesService.createRole(roleEntity);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.DELETE_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void deleteRole(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws ServiceException {
		rolesService.deleteRole(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = { "", "/all" }, method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getAllRoles(HttpServletRequest request, HttpServletResponse response) {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(rolesService.getAllRoles());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_PROFILE + "','" + PrivilegeUtils.Values.MANAGE_PROFILE + "')")
	@RequestMapping(value = { "/my" }, method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getCurrentUsersRoles(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(rolesService.getUsersRolesByUsername(username));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getRole(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws ServiceException {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(rolesService.getRoleById(id));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.LOOK_UP_USER_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = RestUtils.CONTENT_APPLICATION_JSON_UTF8)
	@ResponseBody
	String getUsersRoles(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId) throws ServiceException {
		String json = GsonHolder.getGson(GsonHolder.RESTRICTED).toJson(rolesService.getUsersRoles(userId));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.UPDATE_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	void updateRole(HttpServletRequest request, HttpServletResponse response) throws ServiceException, BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			rolesService.updateRole(GsonHolder.getGson().fromJson(requestBody, RoleEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}
}

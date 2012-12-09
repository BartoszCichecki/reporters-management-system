/**
 * Project:   rms-server
 * File:      PrivilegesRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      24-09-2012
 */

package pl.bcichecki.rms.ws.rest.json.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.services.PrivilegesService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/privileges")
public class PrivilegesRestWS extends AbstractRestWS {

	@Autowired
	protected PrivilegesService privilegesService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = { "", "/all" }, method = RequestMethod.GET)
	public @ResponseBody
	String getAllPrivileges(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(privilegesService.getAllPrivileges());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_PROFILE + "','" + PrivilegeUtils.Values.MANAGE_PROFILE + "')")
	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public @ResponseBody
	String getMyPrivileges(HttpServletRequest request, HttpServletResponse response) {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(privilegesService.getAuthenticatedUsersPrivileges());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_ROLES + "','" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public @ResponseBody
	String getUsersPrivileges(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId) throws ServiceException {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(privilegesService.getUsersPrivileges(userId));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

}

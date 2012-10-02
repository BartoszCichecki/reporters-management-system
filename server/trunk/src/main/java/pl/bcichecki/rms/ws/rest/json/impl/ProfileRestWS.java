/**
 * Project:   rms-server
 * File:      ProfileRestWS.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.ws.rest.json.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonParseException;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.utils.SecurityUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileRestWS extends AbstractRestWS {

	@Autowired
	private UsersService usersService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_PROFILE + "')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	String getProfile(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		RestUtils.decorateResponseHeaderForJson(response);
		String json = getGson().toJson(SecurityUtils.getCurrentUser());
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_PROFILE + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws BadRequestException, ServiceException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".", "exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			UserEntity currentUser = SecurityUtils.getCurrentUser();
			if (currentUser == null) {
				throw new IllegalStateException("Not authenticated?");
			}
			usersService.updateUserSafely(getGson().fromJson(requestBody, UserEntity.class), currentUser);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

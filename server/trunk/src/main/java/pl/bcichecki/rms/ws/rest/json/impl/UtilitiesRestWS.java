/**
 * Project:   rms-server
 * File:      UtilitiesRestWS.java
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.bcichecki.rms.exceptions.impl.BadRequestException;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.NotificationService;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

import com.google.gson.JsonParseException;

/**
 * @author Bartosz Cichecki
 */
public class UtilitiesRestWS extends AbstractRestWS {

	@Autowired
	private UsersService usersService;

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = "/forgotPassword/{username}", method = RequestMethod.GET)
	void forgotPassword(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) throws ServiceException {
		String newPassword = usersService.recreatePassword(username);
		notificationService.notifyUserAboutPasswordChange(username, newPassword, request.getLocale());
	}

	@RequestMapping(value = "/register", method = RequestMethod.PUT)
	void registerUser(HttpServletRequest request, HttpServletResponse response, @PathVariable Long userId) throws ServiceException,
	        BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't register \"nothing\".", "exceptions.badRequestExceptions.cantRegisterNothing");
		}
		try {
			UserEntity user = getGson().fromJson(requestBody, UserEntity.class);
			usersService.registerUser(user);
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

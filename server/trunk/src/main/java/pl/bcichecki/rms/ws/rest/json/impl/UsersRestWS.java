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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/users")
public class UsersRestWS extends AbstractRestWS {

	@Autowired
	protected UsersService usersService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_ROLES + "')")
	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET)
	@ResponseBody
	String getUsersWithRole(HttpServletRequest request, HttpServletResponse response, @PathVariable Long roleId,
			@RequestParam(value = "idAndVersionOnly", required = false, defaultValue = "false") boolean idAndVersionOnly)
			throws ServiceException {
		String json = getGson().toJson(usersService.getUsersWithRole(roleId, idAndVersionOnly));
		RestUtils.decorateResponseHeaderWithMD5(response, json);
		return json;
	}

}
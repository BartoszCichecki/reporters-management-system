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

import java.io.IOException;

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
import pl.bcichecki.rms.model.impl.DeviceEntity;
import pl.bcichecki.rms.services.DevicesService;
import pl.bcichecki.rms.utils.PrivilegeUtils;
import pl.bcichecki.rms.ws.rest.json.AbstractRestWS;
import pl.bcichecki.rms.ws.rest.json.utils.RestUtils;

/**
 * @author Bartosz Cichecki
 */
@Controller
@RequestMapping(value = "/devices")
public class DevicesRestWS extends AbstractRestWS {

	@Autowired
	protected DevicesService devicesService;

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_DEVICES + "')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	void createDevice(HttpServletRequest request, HttpServletResponse response) throws ServiceException,
			BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't create \"nothing\".",
					"exceptions.badRequestExceptions.cantCreateNothing");
		}
		try {
			devicesService.createDevice(getGson().fromJson(requestBody, DeviceEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_DEVICES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	void deleteDevice(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
			throws ServiceException {
		devicesService.deleteDevice(id);
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_DEVICES + "')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	String getAllDevices(HttpServletRequest request, HttpServletResponse response, @RequestParam(
			value = "idAndVersionOnly", required = false, defaultValue = "false") boolean idAndVersionOnly) {
		return getGson().toJson(devicesService.getAllDevices(idAndVersionOnly));
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.VIEW_DEVICES + "')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	String getDevice(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
			throws ServiceException {
		return getGson().toJson(devicesService.getDeviceById(id));
	}

	@PreAuthorize("hasRole('" + PrivilegeUtils.Values.MANAGE_DEVICES + "')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	void updateDevice(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException,
			BadRequestException {
		String requestBody = RestUtils.getRequestBody(request);
		if (StringUtils.isBlank(requestBody)) {
			throw new BadRequestException("You can't update \"nothing\".",
					"exceptions.badRequestExceptions.cantUpdateNothing");
		}
		try {
			devicesService.updateDevice(getGson().fromJson(requestBody, DeviceEntity.class));
		} catch (JsonParseException ex) {
			throw new BadRequestException("Error in submitted JSON!", "exceptions.badRequestExceptions.badJson", ex);
		}
	}

}

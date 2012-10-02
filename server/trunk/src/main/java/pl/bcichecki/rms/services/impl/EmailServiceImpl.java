/**
 * Project:   rms-server
 * File:      EmailServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;

import pl.bcichecki.rms.config.EmailConfiguration;
import pl.bcichecki.rms.exceptions.impl.ServiceException;
import pl.bcichecki.rms.model.impl.UserEntity;
import pl.bcichecki.rms.services.EmailService;
import pl.bcichecki.rms.services.NotificationService;
import pl.bcichecki.rms.services.UsersService;
import pl.bcichecki.rms.utils.ResourceBundleUtils;

/**
 * @author Bartosz Cichecki
 */
public class EmailServiceImpl implements NotificationService, EmailService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private EmailConfiguration emailConfiguration;

	@Override
	public boolean notifyUserAboutPasswordChange(String username, String newPassword, Locale locale) throws ServiceException {
		UserEntity user = usersService.getUserByUsername(username);
		return sendEmail(ResourceBundleUtils.getValue("notifications.passwordReminder.title", locale),
		        ResourceBundleUtils.getValue("notifications.passwordReminder.content", locale, newPassword), user.getEmail());
	}

	@Override
	public boolean sendEmail(String title, String content, List<String> recipents) throws ServiceException {
		SimpleEmail email;
		try {
			email = (SimpleEmail) emailConfiguration.getConfiguredEmail();
			email.setTo(recipents);
			email.setSubject(title);
			email.setMsg(content);
		} catch (EmailException ex) {
			throw new ServiceException("Could not configure email.", "exceptions.serviceExceptions.email.configError", ex);
		}
		try {
			return !StringUtils.isBlank(email.send());
		} catch (EmailException ex) {
			throw new ServiceException("Could not send email.", "exceptions.serviceExceptions.email.sendingError", ex);
		}
	}

	@Override
	public boolean sendEmail(String title, String content, String recipent) throws ServiceException {
		List<String> recipents = new ArrayList<String>();
		recipents.add(recipent);
		return sendEmail(title, content, recipents);
	}
}

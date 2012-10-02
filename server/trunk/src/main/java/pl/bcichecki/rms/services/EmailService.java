/**
 * Project:   rms-server
 * File:      EmailService.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      28-09-2012
 */

package pl.bcichecki.rms.services;

import java.util.List;

import pl.bcichecki.rms.exceptions.impl.ServiceException;

/**
 * @author Bartosz Cichecki
 */
public interface EmailService {

	boolean sendEmail(String title, String content, List<String> recipents) throws ServiceException;

	boolean sendEmail(String title, String content, String recipent) throws ServiceException;

}

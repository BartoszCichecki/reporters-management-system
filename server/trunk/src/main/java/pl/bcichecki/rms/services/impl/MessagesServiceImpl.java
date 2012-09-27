/**
 * Project:   rms-server
 * File:      MessagesServiceImpl.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      12-09-2012
 */

package pl.bcichecki.rms.services.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.services.MessagesService;

/**
 * @author Bartosz Cichecki
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MessagesServiceImpl implements MessagesService {

}

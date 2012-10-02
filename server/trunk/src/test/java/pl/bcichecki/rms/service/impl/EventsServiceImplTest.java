/**
 * Project:   rms-server
 * File:      EventsServiceImplTest.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      02-10-2012
 */

package pl.bcichecki.rms.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pl.bcichecki.rms.services.EventsService;

/**
 * @author Bartosz Cichecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EventsServiceImplTest {

	@Autowired
	private EventsService eventsService;

	@Test
	public void testGetDevicesEvents() {
		// eventsService.getDevicesEvents(1L, null, null);
	}

}

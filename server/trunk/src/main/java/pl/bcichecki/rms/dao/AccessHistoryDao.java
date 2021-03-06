/**
 * Project:   rms-server
 * File:      AccessHistoryDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.util.Date;
import java.util.List;

import pl.bcichecki.rms.model.impl.AccessHistoryEntity;

/**
 * @author Bartosz Cichecki
 */
public interface AccessHistoryDao extends GenericDao<AccessHistoryEntity> {

	List<AccessHistoryEntity> getAllByIpUsername(String ip, String username, Date from, Date till);

}

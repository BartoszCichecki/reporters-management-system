/**
 * Project:   rms-server
 * File:      GenericDao.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      19-08-2012
 */

package pl.bcichecki.rms.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import pl.bcichecki.rms.model.AbstractEntity;

/**
 * @author Bartosz Cichecki
 */
public interface GenericDao<T extends AbstractEntity> {

	boolean contains(T entity);

	void create(T entity);

	void delete(T entity);

	List<T> getAll();

	List<T> getAllByCriteria(CriteriaQuery<T> criteria);

	T getByCriteria(CriteriaQuery<T> criteria);

	T getById(Serializable id);

	CriteriaBuilder getCriteriaBuilder();

	void setEntityManager(EntityManager manager);

	void update(T entity);

}

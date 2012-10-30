package de.htw.fb4.bilderplattform.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/************************************************
 * <p>every DAO should be derived form AbstractDAO.
 * because in that case the DAO already has a
 * session factory</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public abstract class AbstractDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}

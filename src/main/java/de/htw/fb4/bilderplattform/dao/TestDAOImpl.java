package de.htw.fb4.bilderplattform.dao;

import org.springframework.transaction.annotation.Transactional;

/************************************************
 * <p>example DAO</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public class TestDAOImpl extends AbstractDAO {

	@Transactional
	public void saveString(String str) {
		Test test = new Test(str);
		sessionFactory.getCurrentSession().saveOrUpdate(test);
	}
}
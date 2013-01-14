package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Wojciech Konitzer
 * 
 */

public class UserPurchaseDAOImpl extends AbstractDAO {

	@Transactional
	public void saveUserPurchase(UserPurchase userPurchase) {
		sessionFactory.getCurrentSession().saveOrUpdate(userPurchase);
	}
	
	@Transactional
	public Integer getLastInsertedUserPurchaseID() {
		List<Integer> results = sessionFactory.getCurrentSession()
				.createCriteria(UserPurchase.class)
				.setProjection(Projections.max("idUserPurchase")).list();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
}

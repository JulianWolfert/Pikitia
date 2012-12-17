package de.htw.fb4.bilderplattform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public class PurchaseDAOImpl extends AbstractDAO {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Purchase_Image> getAllPurchasesByUserId(int idUser) {
		List<Purchase_Image> purchases = new ArrayList<Purchase_Image>();
		Session session = sessionFactory.getCurrentSession();
		try {
			//TODO by IdUser
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from Purchase_Image");
			purchases= query.list();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchases;
	}
}

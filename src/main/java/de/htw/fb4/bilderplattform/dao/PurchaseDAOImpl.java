package de.htw.fb4.bilderplattform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Peter Horn
 *
 */
public class PurchaseDAOImpl extends AbstractDAO {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Purchase_Image> getAllPurchasesByUserId(int idUser) {
		List<Purchase_Image> purchases = new ArrayList<Purchase_Image>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Purchase_Image.class);			
			purchases= criteria.list();
			List<Purchase_Image>user_purchases = new ArrayList<Purchase_Image>();
			//get users purchases only
			for(Purchase_Image p: purchases){
				if(p.getImage().getUser().getIdUser()==idUser){
					user_purchases.add(p);
				}
			}
			purchases=user_purchases;
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchases;
	}
}

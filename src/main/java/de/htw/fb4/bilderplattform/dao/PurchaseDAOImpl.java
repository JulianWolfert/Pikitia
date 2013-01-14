package de.htw.fb4.bilderplattform.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
			purchases = criteria.list();
			List<Purchase_Image> user_purchases = new ArrayList<Purchase_Image>();
			// get users purchases only
			for (Purchase_Image p : purchases) {
				if (p.getImage().getUser().getIdUser() == idUser) {
					user_purchases.add(p);
				}
			}
			purchases = user_purchases;
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchases;
	}

	@Transactional
	public GuestPurchase getGuestPurchaseById(int idGuestPurchase) {
		GuestPurchase guestPurchase = new GuestPurchase();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(GuestPurchase.class)
					.add(Restrictions.eq("idGuestPurchase", idGuestPurchase));
			guestPurchase = (GuestPurchase) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return guestPurchase;
	}
	
	@Transactional
	public User getUserByIdUserPurchase(int idUserPurchase) {
		// that should be it
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserPurchase.class)
				.add(Restrictions.eq("idUserPurchase", idUserPurchase));
		return ((UserPurchase) criteria.uniqueResult()).getUser();
//		User user = new User();
//		Session session = sessionFactory.getCurrentSession();
//		try {
//			Criteria criteria = session.createCriteria(UserPurchase.class)
//					.add(Restrictions.eq("idUserPurchase", idUserPurchase));
//			int idUser = ((UserPurchase) criteria.uniqueResult()).getUser_idUser();
//			criteria = session.createCriteria(User.class)
//					.add(Restrictions.eq("idUser", idUser));
//			user= (User) criteria.uniqueResult();
//		} catch (DataAccessException dae) {
//			session.getTransaction().rollback();
//		}
//		return user;
	}
	
	@Transactional
	public Purchase getPurchaseById(int idPurchase) {
		Purchase purchase = new Purchase();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Purchase.class)
					.add(Restrictions.eq("idPurchase", idPurchase));
			purchase = (Purchase) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return purchase;
	}
	
	@Transactional
	public Bankaccount getBankaccountByIdUser(int idUser){
		Bankaccount bank = new Bankaccount();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Bankaccount.class)
					.add(Restrictions.eq("User_idUser", idUser));
			bank = (Bankaccount) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			session.getTransaction().rollback();
		}
		return bank;
	}
	
	@Transactional
	public void savePurchase(Purchase purchase) {
		sessionFactory.getCurrentSession().saveOrUpdate(purchase);
	}
	
}

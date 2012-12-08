package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/************************************************
 * <p>
 * The User DAO
 * </p>
 * 
 * <p>
 * 
 * @author Josch Rossa
 *         </p>
 *         <p>
 *         02.11.2012
 *         </p>
 ************************************************/
public class UserDAOImpl extends AbstractDAO {

	@Transactional
	public void saveUser(User usr) {
		sessionFactory.getCurrentSession().saveOrUpdate(usr);
	}

	@Transactional
	public User getUserByID(int idUser) {
//TODO: Its not working like this (org.hibernate.LazyInitializationException: could not initialize proxy - no Session)		
//		User usr = (User) sessionFactory.getCurrentSession().load(User.class,
//				idUser);
//		return usr;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT u FROM User u where u.idUser = " + idUser);
		return (User) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllUser() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT u FROM User u where u.isDeleted = 0");
		return (List<User>) query.list();
	}

	@Transactional
	public void deleteUser(int idUser) {
		User usr = getUserByID(idUser);
		usr.setIsDeleted(true);
		sessionFactory.getCurrentSession().saveOrUpdate(usr);
	}

	@Transactional
	public void deleteUser(User user) {
		user.setIsDeleted(true);
		this.saveUser(user);
	}
}

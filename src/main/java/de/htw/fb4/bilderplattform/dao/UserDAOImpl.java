package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

/************************************************
 * <p>The User DAO</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
public class UserDAOImpl extends AbstractDAO {

	@Transactional
	public void saveUser(User usr) {
		sessionFactory.getCurrentSession().saveOrUpdate(usr);
	}
	
	@Transactional
	public User getUserByID(int idUser) {
		User usr = (User) sessionFactory.getCurrentSession().load(User.class, idUser);
		return usr;
	}
	
	@Transactional
	public User getUserByName(String username) throws Exception {
		List<User> allUser = getAllUser();
		for(User usr : allUser) {
			if(usr.getUsername().equals(username))
				return usr;
		}
		//TODO: custom Exception??
		throw new Exception("No user found by username: " + username);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllUser() {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u where u.isDeleted = 0");
		return (List<User>) query.list();
	}
	
	@Transactional
	public void deleteUser(int idUser) {
		User usr = getUserByID(idUser);
		usr.setDeleted(true);
		sessionFactory.getCurrentSession().saveOrUpdate(usr);
	}
	
}

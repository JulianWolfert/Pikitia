package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/************************************************
 * <p>message DAO</p>
 * 
 * <p>
 * @author ben
 * </p>
 * <p>
 * 10.11.2012
 * </p>
 ************************************************/
public class MessageDAOImpl extends AbstractDAO {

	@Transactional
	public void saveMessage(Message message) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(message);
	}
	
//	saveMessage
	
	
	
	
	
	// getting all messages from a specific user, only newest from specific topic
//	getAllMessagesFromUser
	
	// getting all messaged from a specific topic, from newest to oldest
//	getAllMessagesFromTopic
	
	/*
	public class UserDAOImpl extends AbstractDAO {

		@Transactional
		public void saveUser(User usr) {
			sessionFactory.getCurrentSession().saveOrUpdate(usr);
		}

		@Transactional
		public User getUserByID(int idUser) {
			User usr = (User) sessionFactory.getCurrentSession().load(User.class,
					idUser);
			return usr;
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
	*/
	
	
	
}



package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 10.11.2012
 * @author Benjamin Schock
 * 
 */

public class MessageDAOImpl extends AbstractDAO {

	
	@Transactional
	public void saveMessage(Message message) {
		sessionFactory.getCurrentSession().saveOrUpdate(message);
	}
	
	
	@Transactional
	public Message getMessageById(int idMessage) {
		Message message = (Message) sessionFactory.getCurrentSession().load(Message.class, idMessage);
		return message;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Message> getMessageList(int idUser) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT * FROM Message m WHERE m.idReceiver_idUser = " + idUser);
		return (List<Message>) query.list();
	}	

	
	@Transactional
	public void deleteMessage(int idMessage) {
		Message message = getMessageById(idMessage);
		deleteMessage(message);
	}

	
	@Transactional
	public void deleteMessage(Message message){
		message.setIsDeleted(true);
		sessionFactory.getCurrentSession().saveOrUpdate(message);
	}

}



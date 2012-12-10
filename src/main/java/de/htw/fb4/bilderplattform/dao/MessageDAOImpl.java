package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
	
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class)
				.add(Restrictions.eq("receiver.idUser", idUser))
				.add(Restrictions.eq("isDeleted", false))
				.addOrder(Order.asc("timeStamp"));

		return (List<Message>) criteria.list();
		//return criteria.list();
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



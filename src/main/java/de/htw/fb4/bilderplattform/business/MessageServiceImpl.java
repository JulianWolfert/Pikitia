package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.MessageDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/**
 * @since 04.12.2012
 * @author Benjamin Schock
 * 
 */

class MessageServiceImpl implements IMessageService {

	
	@Override
	public void saveMessage(Message message){
		if(message == null){
			return;
		}
		MessageDAOImpl messageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		messageDAO.saveMessage(message);
	}
	
	@Override
	public List<Message> getMessageList(int idUser){		
		MessageDAOImpl messageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		return messageDAO.getMessageList(idUser);		
	}
	
	
	@Override
	public void deleteMessage(int idMessage){
		MessageDAOImpl messageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		messageDAO.deleteMessage(idMessage);
	}
	
	@Override
	public void deleteMessage(Message message){
		MessageDAOImpl messageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		messageDAO.deleteMessage(message);
	}
	
	
	/*
	@Override
	public Message getMessageById(int idMessage) {
		MessageDAOImpl msgDAO = ApplicationContextProvider
				.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		List<Message> allUser = userDAO.getAllUser();
		for (User usr : allUser) {
			if (usr.getUsername().equals(username))
				return usr;
		}
		throw new UsernameNotFoundException(username);
	}

	
	@Override
	public List<User> getAllUser() throws UsernameNotFoundException {
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		return userDAO.getAllUser();
	}

	@Override
	@Transactional
	public void saveOrUpdateUser(User user) {
		if (user == null) {
			return;
		}
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		user.setLastUpdateDate(Calendar.getInstance().getTime());
		userDAO.saveUser(user);
	}

	@Override
	public void deleteUser(User user) {
		if (user == null) {
			return;
		}
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		userDAO.deleteUser(user);
	}
	*/

}

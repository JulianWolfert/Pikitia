package de.htw.fb4.bilderplattform.business;

import java.util.Calendar;
import java.util.List;


import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.MessageDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

class MessageServiceImpl implements IMessageService {

	
	@Override
	public void saveMessage(Message message){
		if(message == null){
			return;
		}
		MessageDAOImpl messageDAO = ApplicationContextProvider.getApplicationContext().getBean("msgDao", MessageDAOImpl.class);
		messageDAO.saveMessage(message);
	}
	
	
	
	/*
	@Override
	public User getUserByName(String username) throws UsernameNotFoundException {
		UserDAOImpl userDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		List<User> allUser = userDAO.getAllUser();
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

package de.htw.fb4.bilderplattform.business;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

class UserServiceImpl implements IUserService {

	@Override
	public User getUserByName(String username) throws UsernameNotFoundException {
		UserDAOImpl userDAO = ApplicationContextProvider.getApplicationContext().getBean("userDao", UserDAOImpl.class);
		List<User> allUser = userDAO.getAllUser();
		for(User usr : allUser) {
			if(usr.getUsername().equals(username))
				return usr;
		}
		throw new UsernameNotFoundException(username);
	}

}

package de.htw.fb4.bilderplattform.business;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.dao.User;

public interface IUserService {

	public User getUserByName(String username) throws UsernameNotFoundException;
	
	public void updateUser(User user);
	
}

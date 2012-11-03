package de.htw.fb4.bilderplattform.business;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.dao.User;

public interface IUserService {

	public User getUserByName(String username) throws UsernameNotFoundException;

	public List<User> getAllUser() throws UsernameNotFoundException;
	
	public void saveOrUpdateUser(User user);
	
	public void deleteUser(User user);
	
}

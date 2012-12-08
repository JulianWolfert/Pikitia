package de.htw.fb4.bilderplattform.business;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.dao.User;

/************************************************
 * <p>Provides all user concerning functionality</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
public interface IUserService {

	public User getUserByName(String username) throws UsernameNotFoundException;
	
	public User getUserByID(int idUser);

	public List<User> getAllUser() throws UsernameNotFoundException;
	
	public void saveOrUpdateUser(User user);
	
	public void deleteUser(User user);
	
	// jro: check if a user is authenticated in this session
	public boolean isAUserAuthenticated();
	
	// jro: Same as (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	public User getCurrentlyLoggedInUser();
	
}

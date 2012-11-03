package de.htw.fb4.bilderplattform.spring;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.dao.User;
/************************************************
 * <p>
 * Requests user details from a different provider.
 * Realizes spring UserDetailService 
 * </p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
public class SpringUserProviderService implements UserDetailsService  {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// ask business layer
		IUserService userService = BusinessCtx.getInstance().getUserService();
		User user = userService.getUserByName(username);
		return user;
	}

}

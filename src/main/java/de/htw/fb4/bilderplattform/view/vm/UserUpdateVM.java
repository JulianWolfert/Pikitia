package de.htw.fb4.bilderplattform.view.vm;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;


/**
 * @since 07.11.2012
 * @author Julian Wolfert
 * 
 */
public class UserUpdateVM {
	
	
	// the logged in user
	private User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	private String retypedPassword;
	
	public UserUpdateVM () {
		retypedPassword = user.getPassword();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	@NotifyChange("updateUser")
	@Command
	public void submit() {
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
	}

}

package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Wojciech Konitzer
 * 
 */
public class RegistrationVM {

	// the current user for registration
	private User user = new User();
	private String retypedPassword;

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

	@NotifyChange("newUser")
	@Command
	public void submit() {
		user.setIsNormalUser(true);
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
	}
}

package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;


/**
 * @since 07.11.2012
 * @author Julian Wolfert
 * 
 */
public class UserUpdateVM {
	
	
	// the logged in user
	private User user = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser();
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
		
		Messagebox.show(SpringPropertiesUtil.getProperty("user.userUpdateSuccess"), "Info", Messagebox.OK, Messagebox.INFORMATION);
			
	}

}

package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Wojciech Konitzer
 * 
 */
public class RegistrationVM {

	//Wired Elements with sClasses
	@Wire("#modalRegistration")
	private Window modalRegistration;
	
	@Wire
	Button closeButton;
	
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
	
	
	@Command
	public void closeThis() {
		this.modalRegistration.detach();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
}

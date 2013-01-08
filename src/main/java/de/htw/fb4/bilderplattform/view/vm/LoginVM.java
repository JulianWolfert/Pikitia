package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
/************************************************
 * <p>VM to the login.zul and loginAsPage.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 05.01.2013
 * </p>
 ************************************************/
public class LoginVM {

	@Wire("#modalLogin")
	private Window win;
	

	@Command
	public void resetPassword() {
		Executions.getCurrent().sendRedirect("/password_reset.zul");
	}
	
	@Command
	public void closeThis(){
		win.detach();
	}
	
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
}

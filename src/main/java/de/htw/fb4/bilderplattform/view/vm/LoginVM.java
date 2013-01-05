package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class LoginVM {

	@Wire("#modalLogin")
	private Window win;
	

	@Command
	public void resetPassword() {
		
	}
	
	@Command
	public void closeThis(){
		win.detach();
	}
	
}

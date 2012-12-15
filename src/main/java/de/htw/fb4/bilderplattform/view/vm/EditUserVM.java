package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

public class EditUserVM {
	private User user;
	
	@Wire("#modalEditUser")
	private Window win;
	
	public User getUser() {
		return user;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("idUser") Integer idUser) {
		this.user = BusinessCtx.getInstance().getUserService().getUserByID(idUser);
	}
	
	@Command
	public void updateUser(){
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(this.user);
	}
	
	@Command
	public void closeThis() {
		this.win.detach();
	}
}

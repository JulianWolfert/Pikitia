package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import de.htw.fb4.bilderplattform.view.abstraction.AbstractCommentAdministrationVM;

/************************************************
 * <p></p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 05.01.2013
 * </p>
 ************************************************/
public class AdminCommentAdministrationVM extends AbstractCommentAdministrationVM {

	@Init
	public void init() {
		super.init(Integer.parseInt(Executions.getCurrent().getParameter("idImage")), 
				Integer.parseInt(Executions.getCurrent().getParameter("idUser")));
	}
	
	@Command
	public void redirectToImageList() {
		Executions.getCurrent().sendRedirect("/admin/userImageList.zul?idUser="+ getIdUser());
	}
	
	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
}

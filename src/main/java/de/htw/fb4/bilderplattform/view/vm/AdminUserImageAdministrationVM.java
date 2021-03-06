package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.view.abstraction.AbstractUserImageAdministrationVM;

/************************************************
 * <p>VM to the userImageList.zul in folder admin</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 26.12.2012
 * </p>
 ************************************************/
public class AdminUserImageAdministrationVM extends AbstractUserImageAdministrationVM {


	@Init
	public void init() {
		super.init();
	}
	
	@Command
	public void editImage(@BindingParam("image") final Image image) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("image", image);
		Executions.createComponents("/admin/editImage.zul", null, sessionMap);
	}
	
	@Command
	public void imageComments(@BindingParam("image") final Image image) {
		Executions.sendRedirect("/admin/commentList.zul?idUser=" +
						image.getUser().getIdUser() +
						"&idImage=" + image.getIdImage());
	}
	
	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
	
}

package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Peter Horn
 *
 */
public class PurchaseAdministrationVM {
	private final static String img_folder="/images/";
	
	private ListModelList<Purchase_Image> purchaseList;
	private User user=null;

	@Init
	public void init() {
		int idUser = Integer.parseInt(Executions.getCurrent().getParameter("idUser"));
		
		this.user=BusinessCtx.getInstance().getUserService().getUserByID(idUser);
		this.purchaseList = new ListModelList<Purchase_Image>(BusinessCtx
				.getInstance().getPurchaseService().getUserPurchase(idUser));
	}
	
	public String getCustomer(int idPurchase){
		return "unknown";
	}
	
	public String getAbsoluteImgPath(String imgName){
		return img_folder+imgName;
	}

	public ListModelList<Purchase_Image> getPurchaseList() {
		return purchaseList;
	}

	public User getUser() {
		return user;
	}
	
	@Command
	public void redirectToStart() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}

	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
	@Command
	public void showPurchaseDetails(@BindingParam("idPurchase") final int idPurchase) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idPurchase", idPurchase);
		Executions.createComponents("/admin/purchaseDetails.zul", null, sessionMap);
	}
}

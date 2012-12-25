package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;

/**
 * 
 * @author Peter Horn
 *
 */
public class PurchaseAdministrationVM {
	
	// BUGFIX: double initialization
	private ListModelList<Purchase_Image> purchaseList = new ListModelList<Purchase_Image>();

	@Init
	public void init() {
		int idUser = Integer.parseInt(Executions.getCurrent().getParameter("idUser"));
		
		this.purchaseList = new ListModelList<Purchase_Image>(BusinessCtx
				.getInstance().getPurchaseService().getUserPurchase(idUser));
	}

	public ListModelList<Purchase_Image> getPurchaseList() {
		return purchaseList;
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
	public void showPurchaseDetails(@BindingParam("purchase") final Purchase_Image purchase) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idImage", purchase.getImage().getIdImage());
		sessionMap.put("idPurchase", purchase.getPurchase().getIdPurchase());
		Executions.createComponents("/admin/purchaseDetails.zul", null, sessionMap);
	}


}

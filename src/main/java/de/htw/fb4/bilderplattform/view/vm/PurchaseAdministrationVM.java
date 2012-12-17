package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;

public class PurchaseAdministrationVM {
	private ListModelList<Purchase_Image> purchaseList = new ListModelList<Purchase_Image>();

	@Init
	public void init() {
		//TODO
		int idUser= 1;
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
}

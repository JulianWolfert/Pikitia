package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

/**
 * 
 * @author Peter Horn
 *
 */
public class PurchaseDetailsVM {	
	@Wire("#modalShowPurchaseDetails")
	private Window win;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("idImage") Integer idImage, @ExecutionArgParam("idPurchase") Integer idPurchase) {
		//TODO
	}
	
	@Command
	public void closeThis() {
		this.win.detach();
	}
}

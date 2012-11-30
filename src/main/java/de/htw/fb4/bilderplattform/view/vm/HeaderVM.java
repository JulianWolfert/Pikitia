package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/************************************************
 * <p>Header functionality trigger: logout, search</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 28.11.2012
 * </p>
 ************************************************/
public class HeaderVM {
	
	private String search;
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getLogLabel() {
		if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()) {
			return SpringPropertiesUtil.getProperty("lbl.logout");
		}
		return SpringPropertiesUtil.getProperty("lbl.login");
	}
	
	public String getLogPath() {
		if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()) {
			return "/j_spring_security_logout";
		}
		return "./login.zul";
	}
	
	public String getSearch() {
		return search;
	}
	
	@Command
	private void search() {
		// TODO Auto-generated method stub

	}
	
	
	
	
	
}
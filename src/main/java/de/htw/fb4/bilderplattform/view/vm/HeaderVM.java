package de.htw.fb4.bilderplattform.view.vm;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.ISearchService;
import de.htw.fb4.bilderplattform.business.util.ResourcesUtil;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.User;
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
	
	@Wire("#cartLogo")
	A cartLogo;
	
	@Wire
	A regLabel;
	
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
	
	@Command
	public void logButtonClicked() {
		if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()) {
			Executions.getCurrent().sendRedirect("/j_spring_security_logout");
		}
		else {
			Executions.createComponents("/login.zul", null, null);
		}
	}
	
	@Command
	public void showRegistrationModal() {
		Executions.createComponents("/registration_modal.zul", null, null);
	}
	
	
	public String getSearch() {
		return search;
	}
	
	public boolean showAdminMenu() {
		if(isAUserAuthenticated() && isUserAdmin())
			return true;
		return false;
	}
	
	public boolean isAUserAuthenticated(){
		return BusinessCtx.getInstance().getUserService().isAUserAuthenticated();
	}
	
	public boolean isUserAdmin() {
		User usr = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser();
		return usr.getIsAdmin();
	}
	
	@Command
	public void search() {
		ISearchService searchService = BusinessCtx.getInstance().getSearchService();
		for(Image img : searchService.searchImages(search)) {
			System.out.println("FOUND: " + img.getFile());
		}

	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.cartLogo.setDroppable("true");
		this.cartLogo.addEventListener(Events.ON_DROP, new EventListener() {
			public void onEvent(Event e)
			{
				DropEvent dropEvent = (DropEvent) e;
				String img_id = dropEvent.getDragged().getId().substring(4);
				addToCart(img_id);
			}
		});
		
		if (isAUserAuthenticated() == true) {
			this.regLabel.setVisible(false);
		}
	}
	
	/* show cart*/
	
	@Command
	public void showCart() {
		Executions.getCurrent().sendRedirect("/cart.zul");
	}
	
	/* Logo click */
	
	@Command
	public void returnHome() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}
	
	/* show gallery for buying an image*/
	
	@Command
	public void showGallery() {
		Executions.getCurrent().sendRedirect("/gallery.zul");
	}
	
	/* User Menu*/
	
	@Command
	public void editUser() {
		Executions.getCurrent().sendRedirect("/user/userUpdate2.zul");
	}
	
	@Command
	public void addImage() {
		Executions.getCurrent().sendRedirect("/user/addImage.zul");
	}
	
	/** leads to a list of the users images */	
	@Command
	public void showImages() {
		User user = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser();
		Executions.sendRedirect("/user/userImageList.zul?idUser="+user.getIdUser()); 
	}
	
	@Command
	public void showMessageList() {
		Executions.createComponents("/user/messageList.zul", null, null);
	}
	
	/* Admin Menu */
	
	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
	
	private void addToCart(String id) {
		Session session = Sessions.getCurrent();
		List<String> imageIDsSession = (List<String>) session.getAttribute("imageIDs");
		
		if (imageIDsSession == null) {
			List<String> imageIDs = new ArrayList<String>();
			Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", BusinessCtx.getInstance().getImageService().getImageByID(Integer.parseInt(id)).getTitle()), "info", null, "top_right",2000);
			imageIDs.add(id);
			session.setAttribute("imageIDs", imageIDs);
		}
		else {
			if(!imageIDsSession.contains(id)) {
				Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", BusinessCtx.getInstance().getImageService().getImageByID(Integer.parseInt(id)).getTitle()), "info", null, "top_right",2000);
				imageIDsSession.add(id);
			}
			else
				Clients.showNotification("Das Bild liegt bereits im Warenkorb", "info", null, "top_right",2000);
				
			session.setAttribute("imageIDs", imageIDsSession);
		}
	}
}





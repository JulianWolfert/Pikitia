package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Benjamin Schock
 * 
 */
public class PurchaseOverviewVM {

	//Wired Elements with sClasses
	@Wire("#modalPurchaseOverview")
	private Window modalPurchaseOverview;
	
	@Wire
	Button closeButton;
	
	private List<Image> cartImages;
	private Double totalCartPrice;
	private GuestPurchase guestPurchase = null;
	
	private String email;
	private String firstname;
	private String surname;
	private String street;
	private String streetnumber;
	private String zipcode;
	private String city;
	private String bankaccountnumber;
	private String banknumber;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("cartImages") List<Image> cartImages,
			@ExecutionArgParam("totalCartPrice") Double totalCartPrice,
			@ExecutionArgParam("guestPurchase") GuestPurchase guestPurchase){
		
		Selectors.wireComponents(view, this, false);
		this.cartImages = cartImages;
		this.guestPurchase = guestPurchase;
		this.totalCartPrice = totalCartPrice;
				
	    if(SecurityContextHolder.getContext().getAuthentication() != null){
	    		    	
	    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	Bankaccount bankaccount = BusinessCtx.getInstance().getBankaccountService().getBankaccountByUserId(user.getIdUser());
	    	
	    	setEmail(user.getEmail());
	    	setFirstname(user.getUsername());
	    	
	    	setSurname("TestNachname");
	        setZipcode("12345");
	        setCity("TestStadt");
	        setStreet("TestStrasse");
	        setStreetnumber("123");
	        if (bankaccount != null){
	        	setBankaccountnumber(bankaccount.getAccount_nr());
	        	setBanknumber(bankaccount.getBank());
	        }
	     	        
	    	/*
	        setFirstname(user.getFirstname());
	        setSurname(user.getSurname());
	        setZipcode(user.getZipcode());
	        setCity(user.getCity());
	        setStreet(user.getStreet());
	        setStreetnumber(user.getStreetnumber());
			*/
	        
	    } else {
	    	
	    }
	    	
	}


	public List<Image> getCartImages() {
		return cartImages;
	}

	public void setCartImages(List<Image> cartImages) {
		this.cartImages = cartImages;
	}

	public GuestPurchase getGuestPurchase() {
		return guestPurchase;
	}

	public void setGuestPurchase(GuestPurchase guestPurchase) {
		this.guestPurchase = guestPurchase;
	}

	public Double getTotalCartPrice() {
		return totalCartPrice;
	}

	public void setTotalCartPrice(Double totalCartPrice) {
		this.totalCartPrice = totalCartPrice;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getStreetnumber() {
		return streetnumber;
	}


	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getBankaccountnumber() {
		return bankaccountnumber;
	}


	public void setBankaccountnumber(String bankaccountnumber) {
		this.bankaccountnumber = bankaccountnumber;
	}


	public String getBanknumber() {
		return banknumber;
	}


	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}

	
	//@NotifyChange("newUser")
	@Command
	public void submit() {
		/*final HashMap<String, Object> purchaseMap = new HashMap<String, Object>();
		purchaseMap.put("cartImages", this.getCartImages());
		purchaseMap.put("totalCartPrice", this.getTotalCartPrice());
		
		Executions.createComponents("/purchaseOverview_modal.zul", null, purchaseMap);*/
		Messagebox.show("blubblu");
		// TODO dieses Fenster beim Absenden druecken schliessen
		this.closeThis();
	}
	
	@Command
	public void enableSubmit(){
		// TODO bim chekcen der AGBs den Absenden Button aktivieren
	}

	
	@Command
	public void closeThis() {
		this.modalPurchaseOverview.detach();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
}





/*
@Wire("#modalMessageDetail")
private Window win;
private Message message;

public Message getMessage() {
	return message;
}

public void setMessage(Message message) {
	this.message = message;
}



@Command
public void contact() {
	closeThis();
	final HashMap<String, Object> messageMap = new HashMap<String, Object>();
	messageMap.put("email", message.getEmail());
	messageMap.put("subject", message.getSubject());
	messageMap.put("userName", message.getReceiver().getUsername());
	
	Executions.createComponents("/user/messageAnswer.zul", null, messageMap);
//	Sessions.getCurrent().setAttribute("receiver_idUser", "2");
//	Executions.getCurrent().sendRedirect("/contactForm.zul");
}

*/
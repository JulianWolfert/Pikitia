package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserPurchase;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Benjamin Schock
 * 
 */
public class PurchaseOverviewVM {

	// Wired Elements with sClasses
	@Wire("#modalPurchaseOverview")
	private Window modalPurchaseOverview;

	@Wire
	Button closeButton;
	@Wire
	Button submitCart;
	
	@Wire
	Checkbox checkTOS;
	@Wire
	Label lblTOS;

	private List<Image> cartImages;
	private Double totalCartPrice;
	private GuestPurchase guestPurchase = null;
	private UserPurchase userPurchase = null;

	private String email;
	private String firstname;
	private String surname;
	private String street;
	private String streetnumber;
	private String zipcode;
	private String city;
	private String bankaccountnumber;
	private String banknumber;

	private HashMap<String, String> registeredUserData = new HashMap<String, String>();

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		this.checkTOS.addEventListener("onCheck", new EventListener() {
			public void onEvent(Event event) throws Exception {
				if(checkTOS.isChecked()){
					submitCart.setDisabled(false);
					lblTOS.setValue("");
				}else{
					submitCart.setDisabled(true);
					lblTOS.setValue(SpringPropertiesUtil.getProperty("err.checkTermOfService"));
				}
			}
		});
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("cartImages") List<Image> cartImages,
			@ExecutionArgParam("totalCartPrice") Double totalCartPrice,
			@ExecutionArgParam("guestPurchase") GuestPurchase guestPurchase,
			@ExecutionArgParam("userPurchase") UserPurchase userPurchase,
			@ExecutionArgParam("registeredUserData") HashMap<String, String> registeredUserData){
		
		Selectors.wireComponents(view, this, false);
		this.cartImages = cartImages;
		this.guestPurchase = guestPurchase;
		this.totalCartPrice = totalCartPrice;
		
		this.guestPurchase = guestPurchase;
		this.userPurchase = userPurchase;
		this.registeredUserData = registeredUserData;
				
	    if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()){
	    	// weg damit! Der user kann doch die fomularfelder aendern. :-) 	
//	    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    	Bankaccount bankaccount = BusinessCtx.getInstance().getBankaccountService().getBankaccountByUserId(user.getIdUser());
	    
	    	//Registered User
	    	if(userPurchase != null){
		    	User user = userPurchase.getUser();
		    	Bankaccount bankaccount = BusinessCtx.getInstance().getBankaccountService().getBankaccountByUserId(user.getIdUser());
		    	
		    	setEmail(user.getEmail());
		    	
		    	setFirstname(getRegisteredUserDataValue("firstname"));
		    	setSurname(getRegisteredUserDataValue("surname"));
		        setZipcode(getRegisteredUserDataValue("zipcode"));
		        setCity(getRegisteredUserDataValue("city"));
		        setStreet(getRegisteredUserDataValue("street"));
		        setStreetnumber(getRegisteredUserDataValue("streetnumber"));
		        
		        if (bankaccount != null){
		        	setBankaccountnumber(bankaccount.getAccount_nr());
		        	setBanknumber(bankaccount.getBank());
		        } 
	    	}
	    } else {    	
	    	//Guest (without loggin)
	    	if(guestPurchase != null){
		    	setEmail(guestPurchase.getEmail());
		    	
		    	setFirstname(guestPurchase.getName());
		    	setSurname(guestPurchase.getSurname());
		        setZipcode(guestPurchase.getPostalcode());
		        setCity(guestPurchase.getCity());
		        setStreet(guestPurchase.getStreet());
		        setStreetnumber(guestPurchase.getStreet_nr());
		        
		        setBankaccountnumber(guestPurchase.getAccount_nr());
		        setBanknumber(guestPurchase.getBank());
	    	}
	    }    	
	}
	
	private String getRegisteredUserDataValue(String key){
		for (String mKey : this.registeredUserData.keySet()) {
			if(mKey.equals(key)) {
				return this.registeredUserData.get(key);
			}
		}
        return null;
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

	@Command
	public void submit() {
		if(this.guestPurchase != null){
			BusinessCtx.getInstance().getPurchaseService().saveGuestPurchase(this.cartImages, this.guestPurchase);
		}else if(this.userPurchase != null){
			BusinessCtx.getInstance().getPurchaseService().saveUserPurchase(this.cartImages, this.userPurchase);
		}
		
		//TODO: EMAIL versenden.
		
		Messagebox.show(SpringPropertiesUtil.getProperty("purchaseOverview.purchaseSendSuccess"), "Info", Messagebox.OK, Messagebox.INFORMATION);
		this.closeThis();
	}

	@Command
	public void closeThis() {
		this.modalPurchaseOverview.detach();
	}

}

/*
 * @Wire("#modalMessageDetail") private Window win; private Message message;
 * 
 * public Message getMessage() { return message; }
 * 
 * public void setMessage(Message message) { this.message = message; }
 * 
 * 
 * 
 * @Command public void contact() { closeThis(); final HashMap<String, Object>
 * messageMap = new HashMap<String, Object>(); messageMap.put("email",
 * message.getEmail()); messageMap.put("subject", message.getSubject());
 * messageMap.put("userName", message.getReceiver().getUsername());
 * 
 * Executions.createComponents("/user/messageAnswer.zul", null, messageMap); //
 * Sessions.getCurrent().setAttribute("receiver_idUser", "2"); //
 * Executions.getCurrent().sendRedirect("/contactForm.zul"); }
 */
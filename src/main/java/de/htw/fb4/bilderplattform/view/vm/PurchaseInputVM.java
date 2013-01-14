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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Benjamin Schock
 * 
 */
public class PurchaseInputVM {

	// Wired Elements with sClasses
	@Wire("#modalPurchaseInput")
	private Window modalPurchaseInput;

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

	private String email;
	private String firstname;
	private String surname;
	private String street;
	private String streetnumber;
	private String zipcode;
	private String city;
	private String bankaccountnumber;
	private String banknumber;

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
			@ExecutionArgParam("totalCartPrice") Double totalCartPrice) {

		Selectors.wireComponents(view, this, false);
		this.cartImages = cartImages;
		this.totalCartPrice = totalCartPrice;
		
	    if(BusinessCtx.getInstance().getUserService().isAUserAuthenticated()){
	    	
	    	User user = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser();
	    	//Bankaccount bankaccount = BusinessCtx.getInstance().getBankaccountService().getBankaccountByUserId(user.getIdUser());
	    	
	    	setEmail(user.getEmail());
	    	setFirstname(user.getUsername());
	    	
	    	setSurname("TestNachname");
	        setZipcode("12345");
	        setCity("TestStadt");
	        setStreet("TestStrasse");
	        setStreetnumber("123");
	        setBankaccountnumber("123456");
	        setBanknumber("12345678");
	        
	        /*
	        if (bankaccount != null){
	        	setBankaccountnumber(bankaccount.getAccount_nr());
	        	setBanknumber(bankaccount.getBank());
	        }
	        
	        setFirstname(user.getFirstname());
	        setSurname(user.getSurname());
	        setZipcode(user.getZipcode());
	        setCity(user.getCity());
	        setStreet(user.getStreet());
	        setStreetnumber(user.getStreetnumber());
			*/
	    }
	}


	public List<Image> getCartImages() {
		return cartImages;
	}

	public void setCartImages(List<Image> cartImages) {
		this.cartImages = cartImages;
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

	// @NotifyChange("newUser")
	@Command
	public void submit() {
		final HashMap<String, Object> purchaseMap = new HashMap<String, Object>();
		purchaseMap.put("cartImages", this.getCartImages());
		purchaseMap.put("totalCartPrice", this.getTotalCartPrice());
		
		// if there is no logged in user a guestPurchase has to be created
		if(SecurityContextHolder.getContext().getAuthentication() == null){

			GuestPurchase guestPurchase = new GuestPurchase();
			
			guestPurchase.setEmail(email);
			guestPurchase.setName(firstname);
			guestPurchase.setSurname(surname);
			guestPurchase.setStreet(street);
			guestPurchase.setStreet_nr(streetnumber);
			guestPurchase.setPostalcode(zipcode);
			guestPurchase.setCity(city);
			guestPurchase.setBank(bankaccountnumber);
			guestPurchase.setBank(banknumber);
			
			purchaseMap.put("guestPurchase", guestPurchase);
		} else {
			purchaseMap.put("guestPurchase", null);
		}
		// TODO hier das urchase Object ubergeben, entweder UserPurchase oder eben nur Purchase
		// Purchase Objekt hier erstellen
		// im naechsten Schritt in die DB schreiben, also beim finalen
		// Bestaetigen

		Executions.createComponents("/purchaseOverview_modal.zul", null,
				purchaseMap);
		this.closeThis();
	}

	@Command
	public void closeThis() {
		this.modalPurchaseInput.detach();
	}

}
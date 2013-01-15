package de.htw.fb4.bilderplattform.view.vm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Message;
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

	    	//Registered User
	    	if(userPurchase != null){
		    	User user = userPurchase.getUser();
		    	Bankaccount bankaccount = BusinessCtx.getInstance().getBankaccountService().getBankaccountByUserId(user.getIdUser());
		    	
		    	//setEmail(user.getEmail());
		    	setEmail(getRegisteredUserDataValue("email"));
		    	
		    	setFirstname(getRegisteredUserDataValue("firstname"));
		    	setSurname(getRegisteredUserDataValue("surname"));
		        setZipcode(getRegisteredUserDataValue("zipcode"));
		        setCity(getRegisteredUserDataValue("city"));
		        setStreet(getRegisteredUserDataValue("street"));
		        setStreetnumber(getRegisteredUserDataValue("streetnumber"));
		        
		        setBankaccountnumber(getRegisteredUserDataValue("bankaccountnumber"));
		        setBanknumber(getRegisteredUserDataValue("banknumber"));
		        
		        /*
		        if (bankaccount != null){
		        	setBankaccountnumber(bankaccount.getAccount_nr());
		        	setBanknumber(bankaccount.getBank());
		        } */
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
		sendEmailAndMessage();
		
		Messagebox.show(SpringPropertiesUtil.getProperty("purchaseOverview.purchaseSendSuccess01")  
				+ getTotalCartPrice() 
				+ SpringPropertiesUtil.getProperty("purchaseOverview.purchaseSendSuccess02"), 
				"Info", Messagebox.OK, Messagebox.INFORMATION);
		this.closeThis();
	}
	
	private void sendEmailAndMessage(){
			
		String companyName = SpringPropertiesUtil.getProperty("lbl.companyName");	
		String receiver = "";
		String subject = "";
		String receiverName = "";
		String messageContent = "";
		
		
		
		// Owner of each image gets informed
		for(Image img : cartImages){
			
			// Mail
			receiver = img.getUser().getEmail();
			subject = SpringPropertiesUtil.getProperty("purchase.subject") + " " + img.getTitle();	
			receiverName = img.getUser().getUsername();
			
			IMail mail = new MailImpl();
			mail.setSender(companyName)
				.setReceiver(receiver) // Verkaeufer
				.setSubject("[" + companyName +"] " + subject )
				.setMessage(preparedMailSeller(receiverName, img.getTitle()))
				.setTimeStamp(Calendar.getInstance().getTime());
			try {
				BusinessCtx.getInstance().getMailService().sendMail(mail);
			} catch (Exception e){}
			
			// Message
			messageContent = img.getTitle() + " " + SpringPropertiesUtil.getProperty("purchase.messageContent");
			Message msg = new Message(
					img.getUser(), SpringPropertiesUtil.getProperty("purchase.companymail"), 1, subject, messageContent);				
			try {
				BusinessCtx.getInstance().getMessageService().saveMessage(msg);
			} catch (Exception e) {}
			
		}
	}
	
	
	private String preparedMailSeller(String seller, String imgName) {
		return SpringPropertiesUtil.getProperty("purchase.mail01") + " "
			+ seller + ",\r\n\r\n" + SpringPropertiesUtil.getProperty("purchase.mail02") 
			+ " " + imgName 
			+ " " + SpringPropertiesUtil.getProperty("purchase.mail03") + "\r\n\r\n"
			+ SpringPropertiesUtil.getProperty("purchase.mail04");		 
	}
	

	@Command
	public void closeThis() {
		this.modalPurchaseOverview.detach();
	}
	
	
}

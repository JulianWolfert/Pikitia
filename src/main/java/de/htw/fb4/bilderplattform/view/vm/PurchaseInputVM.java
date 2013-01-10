package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Benjamin Schock
 * 
 */
public class PurchaseInputVM {

	//Wired Elements with sClasses
	@Wire("#modalPurchaseInput")
	private Window modalPurchaseInput;
	
	@Wire
	Button closeButton;
	
	private String email;
	private String firstname;
	private String surname;
	private String street;
	private String streetnumber;
	private String zipcode;
	private String city;
	private String bankaccountnumber;
	private String banknumber;



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
		//user.setIsNormalUser(true);
		//BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		Messagebox.show("bla");
		//Executions.createComponents("/registration_modal.zul", null, null);
	}

	
	@Command
	public void closeThis() {
		this.modalPurchaseInput.detach();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
}

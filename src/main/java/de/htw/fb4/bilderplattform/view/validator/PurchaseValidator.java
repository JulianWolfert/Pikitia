package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Benjamin Schock
 * 
 * 05.01.2013
 * 
 */
public class PurchaseValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		
		Map<String, Property> beanProps = 
				ctx.getProperties(ctx.getProperty().getBase());
	
		validateEmail(ctx, (String) beanProps.get("email").getValue());
		validateFirstname(ctx, (String) beanProps.get("firstname").getValue());
		validateSurname(ctx, (String) beanProps.get("surname").getValue());
		validateStreet(ctx, (String) beanProps.get("street").getValue());
		validateStreetNumber(ctx, (String) beanProps.get("streetnumber").getValue());
		validateZipCode(ctx, (String) beanProps.get("zipcode").getValue());		
		validateCity(ctx, (String) beanProps.get("city").getValue());
		validateBankAccountNumber(ctx, (String) beanProps.get("bankaccountnumber").getValue());
		validateBankNumber(ctx, (String) beanProps.get("banknumber").getValue());
		
	}
	
	private void validateEmail(ValidationContext ctx, String email) {
    	if(email == null || !email.matches(".+@.+\\.[a-z]+")) {
            this.addInvalidMessage(ctx, "email", SpringPropertiesUtil.getProperty("err.enterValidEMail"));           
        }
    }
	
	private void validateFirstname(ValidationContext ctx, String firstname) {
    	if(firstname == null || !firstname.matches("[a-zA-Z]+")) {
            this.addInvalidMessage(ctx, "firstname", SpringPropertiesUtil.getProperty("err.enterValidName"));           
        }
    }
	
	private void validateSurname(ValidationContext ctx, String surname) {
    	if(surname == null || !surname.matches("[a-zA-Z]+")) {
            this.addInvalidMessage(ctx, "surname", SpringPropertiesUtil.getProperty("err.enterValidName"));           
        }
    }
	
	private void validateStreet(ValidationContext ctx, String street) {
    	if(street == null) {
            this.addInvalidMessage(ctx, "street", SpringPropertiesUtil.getProperty("err.enterValidStreet"));           
        }
    }
	
	private void validateStreetNumber(ValidationContext ctx, String streetnumber) {
    	if(streetnumber == null) {
            this.addInvalidMessage(ctx, "streetnumber", SpringPropertiesUtil.getProperty("err.enterValidStreetNumber"));           
        }
    }
	
	private void validateZipCode(ValidationContext ctx, String zipcode) {
    	if(zipcode == null || !zipcode.matches("[0-9][0-9][0-9][0-9][0-9]")) {
            this.addInvalidMessage(ctx, "zipcode", SpringPropertiesUtil.getProperty("err.enterValidZipCode"));           
        }
    }
    
	private void validateCity(ValidationContext ctx, String city) {
    	if(city == null) {
            this.addInvalidMessage(ctx, "city", SpringPropertiesUtil.getProperty("err.enterValidCity"));           
        }
    }

	private void validateBankAccountNumber(ValidationContext ctx, String bankaccountnumber) {
    	if(bankaccountnumber == null || !bankaccountnumber.matches("[0-9]+")) {
            this.addInvalidMessage(ctx, "bankaccountnumber", SpringPropertiesUtil.getProperty("err.enterValidBankAccountNumber"));           
        }
    }
	
	private void validateBankNumber(ValidationContext ctx, String banknumber) {
    	if(banknumber == null || !banknumber.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]")) {
            this.addInvalidMessage(ctx, "banknumber", SpringPropertiesUtil.getProperty("err.enterValidBankAccount"));           
        }
    }	
}

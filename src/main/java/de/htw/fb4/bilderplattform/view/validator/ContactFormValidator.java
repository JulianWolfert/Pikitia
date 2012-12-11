package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;
/************************************************
 * <p>contactForm validator</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 08.12.2012
 * </p>
 ************************************************/
public class ContactFormValidator extends AbstractValidator {
    
    public void validate(ValidationContext ctx) {
        //all the bean properties
        Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
        
        validateReceiver(ctx, (Integer) beanProps.get("receiverId").getValue());
        validateName(ctx, (String) beanProps.get("nameSender").getValue());
        validateEmail(ctx, (String)beanProps.get("emailSender").getValue());
        validateSubject(ctx, (String)beanProps.get("subject").getValue());
        validateText(ctx, (String)beanProps.get("text").getValue());
    }
    
    private void validateReceiver(ValidationContext ctx, Integer receiverId) {
    	if(receiverId == -1) {
            this.addInvalidMessage(ctx, "receiverId", SpringPropertiesUtil.getProperty("err.enterValidReceiver"));           
        }
    }
    
    private void validateName(ValidationContext ctx, String name) {
    	if(name == null) {
            this.addInvalidMessage(ctx, "nameSender", SpringPropertiesUtil.getProperty("err.enterValidName"));           
        }
    }
    
    private void validateEmail(ValidationContext ctx, String email) {
    	if(email == null || !email.matches(".+@.+\\.[a-z]+")) {
            this.addInvalidMessage(ctx, "emailSender", SpringPropertiesUtil.getProperty("err.enterValidEMail"));           
        }
    }
    
    private void validateSubject(ValidationContext ctx, String subject) {
    	if(subject == null) {
            this.addInvalidMessage(ctx, "subject", SpringPropertiesUtil.getProperty("err.enterValidSubject"));           
        }
    }
    
    private void validateText(ValidationContext ctx, String text) {
    	if(text == null) {
            this.addInvalidMessage(ctx, "text", SpringPropertiesUtil.getProperty("err.enterValidText"));           
        }
    }
     
}

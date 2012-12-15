package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;
/************************************************
 * <p>messageAnswer validator</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 15.12.2012
 * </p>
 ************************************************/
public class MessageAnswerValidator extends AbstractValidator  {

	public void validate(ValidationContext ctx) {
        //all the bean properties
        Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
        validateText(ctx, (String)beanProps.get("text").getValue());
    }
	
	private void validateText(ValidationContext ctx, String text) {
    	if(text == null) {
            this.addInvalidMessage(ctx, "text", SpringPropertiesUtil.getProperty("err.enterValidText"));           
        }
    }
	
}

package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Wojciech Konitzer
 * 
 *         28.12.2012
 * 
 */
public class CommentFormValidator extends AbstractValidator {

	@Override
	public void validate(ValidationContext ctx) {
		// all the bean properties
		Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty()
				.getBase());
		validateText(ctx, (String) beanProps.get("text").getValue());
	}

	private void validateText(ValidationContext ctx, String text) {
		if (text == null) {
			this.addInvalidMessage(ctx, "text", SpringPropertiesUtil
					.getProperty("err.enterValidRatingText"));
		}
	}

}

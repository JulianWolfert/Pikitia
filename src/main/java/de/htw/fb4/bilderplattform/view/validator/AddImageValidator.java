package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

/**
 * 
 * @author Wojciech Konitzer
 * 
 */
public class AddImageValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		// all the bean properties
		Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty()
				.getBase());
		validateUploadedImage(ctx, (String) beanProps.get("uploadedImage")
				.getValue());
		validateDescription(ctx, (String) beanProps.get("description")
				.getValue());
		validatePrice(ctx, (float) beanProps.get("price").getValue());

	}

	private void validateUploadedImage(ValidationContext ctx, String path) {

	}

	private void validateDescription(ValidationContext ctx, String description) {

	}

	private void validatePrice(ValidationContext ctx, float price) {

	}

}

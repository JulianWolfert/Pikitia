package de.htw.fb4.bilderplattform.view.validator;

import java.text.DecimalFormat;
import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

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
		validateTitle(ctx, (String) beanProps.get("title").getValue());
		validateDescription(ctx, (String) beanProps.get("description")
				.getValue());

		validatePrice(ctx, (Double) beanProps.get("price").getValue());
	}

	private void validateTitle(ValidationContext ctx, String title) {

		
		if (title == null) {
			this.addInvalidMessage(ctx, "title", SpringPropertiesUtil
					.getProperty("err.enterValidImageTitle"));
		}
		else
			if (title.length() > 20) {
				this.addInvalidMessage(ctx, "title", SpringPropertiesUtil
						.getProperty("err.enterValidImageTitleLength"));
			}
	}

	private void validateDescription(ValidationContext ctx, String description) {
		if (description == null) {
			this.addInvalidMessage(ctx, "description", SpringPropertiesUtil
					.getProperty("err.enterValidImageDescription"));
		}
	}

	private void validatePrice(ValidationContext ctx, Double price) {
		if (price != null) {
			String strPrice = String.valueOf(price);
			strPrice = formatPrice(strPrice);

			if (!strPrice.matches("^\\d{1,3}(,\\d{2})*$")) {
				this.addInvalidMessage(ctx, "price", SpringPropertiesUtil
						.getProperty("err.enterValidImagePrice"));
			}
		} else {
			this.addInvalidMessage(ctx, "price", SpringPropertiesUtil
					.getProperty("err.enterValidImagePrice"));
		}
	}

	private String formatPrice(String strPrice) {
		if (strPrice != null) {
			String strPriceWithoutComma = strPrice.replace(',', '.');
//			//Euro symbol
//			String strPriceWithoutEuro = strPriceWithoutComma.replace("\u20AC", "");

			DecimalFormat f = new DecimalFormat("#0.00");
			String formatedString = f.format(new Double(strPriceWithoutComma));
			// System.out.println("Price = " + formatedString);

			return formatedString;
		}
		return strPrice;
	}

}

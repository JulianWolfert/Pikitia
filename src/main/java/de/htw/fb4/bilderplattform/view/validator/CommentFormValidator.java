package de.htw.fb4.bilderplattform.view.validator;

import java.util.Map;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.util.ResourcesUtil;
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
		validateUsername(ctx, (String) beanProps.get("username").getValue());
		validateText(ctx, (String) beanProps.get("text").getValue());
	}

	private void validateUsername(ValidationContext ctx, String username) {
		if(!BusinessCtx.getInstance().getUserService().isAUserAuthenticated()){
			if (username == null) {
				this.addInvalidMessage(ctx, "username", SpringPropertiesUtil
						.getProperty("err.enterValidRatingUsername"));
			} else {
				username = username.trim();
				if (!username.matches("[A-Za-z0-9]+")) {
					this.addInvalidMessage(ctx, "username",
							SpringPropertiesUtil.getProperty("err.usernameIsNotValid"));
				}else if(username.length() < 4){
					this.addInvalidMessage(ctx, "username", ResourcesUtil.loadPropertyWithWildcardValues("err.usernameIsTooShort", 4));
				}else if(username.length() > 20){
					this.addInvalidMessage(ctx, "username",
							ResourcesUtil.loadPropertyWithWildcardValues("err.usernameIsTooLong", 20));
				}else{
					try {
						BusinessCtx.getInstance().getUserService()
								.getUserByName(username);
						this.addInvalidMessage(ctx, "username",
								SpringPropertiesUtil.getProperty("err.usernameAlreadyExits"));
					} catch (UsernameNotFoundException unfe) {
		
					}
				}
			}
		}
	}
	
	private void validateText(ValidationContext ctx, String text) {
		if (text == null || text.trim().length() == 0) {
			this.addInvalidMessage(ctx, "text", SpringPropertiesUtil
					.getProperty("err.enterValidRatingText"));
		}else if(text.trim().length() > 300){
			this.addInvalidMessage(ctx, "text", ResourcesUtil.loadPropertyWithWildcardValues(
					"err.enterNotTooLongRatingText", 300));
		}
	}

}

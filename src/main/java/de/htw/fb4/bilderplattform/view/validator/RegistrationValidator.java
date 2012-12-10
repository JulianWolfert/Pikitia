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
 * @author Wojciech Konitzer
 * 
 */
public class RegistrationValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		// all the bean properties
		Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty()
				.getBase());
		validateUsername(ctx, (String) beanProps.get("username").getValue());
		validatePasswords(ctx, (String) beanProps.get("password").getValue(),
				(String) ctx.getValidatorArg("retypedPassword"));
		validateEmail(ctx, (String) beanProps.get("email").getValue());
	}

	private void validateUsername(ValidationContext ctx, String username) {
		if (username == null) {
			this.addInvalidMessage(ctx, "username",
					SpringPropertiesUtil.getProperty("err.enterValidUsername"));
		} else {
			username = username.trim();
			//TODO: changing regex
			if (!username.matches("[A-Za-z0-9]+")) {
				this.addInvalidMessage(ctx, "username",
						SpringPropertiesUtil.getProperty("err.usernameIsNotValid"));
			}else if(username.length() < 4){
				this.addInvalidMessage(ctx, "username",
						SpringPropertiesUtil.getProperty("err.usernameIsTooShort"));
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

	private void validatePasswords(ValidationContext ctx, String password,
			String retype) {
		if (password == null || retype == null || (!password.equals(retype))) {
			this.addInvalidMessage(ctx, "password",
					SpringPropertiesUtil.getProperty("err.bothPasswordsAreInvalid"));
		}else if(!password.matches("^\\S{6,}$")){
			this.addInvalidMessage(ctx, "password",
					SpringPropertiesUtil.getProperty("err.passwordIsTooShort"));
		}
	}

	private void validateEmail(ValidationContext ctx, String email) {
		if (email == null || !email.matches(".+@.+\\.[a-z]+")) {
			this.addInvalidMessage(ctx, "email", SpringPropertiesUtil.getProperty("err.chooseValidMail"));
		}
	}

}

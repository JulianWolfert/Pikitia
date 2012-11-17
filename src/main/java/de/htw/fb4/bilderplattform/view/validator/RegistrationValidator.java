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
					"Bitte geben Sie einen Benutzernamen mit mindestens 4 Zeichen ein!");
		} else {
			username = username.trim();
			//TODO: changing regex
			if (!username.matches("[A-Za-z0-9]+")) {
				this.addInvalidMessage(ctx, "username",
						"Der Benutzername enth\u00e4lt ung\u00fcltige Zeichen!");
			}else if(username.length() < 4){
				this.addInvalidMessage(ctx, "username",
						"Der Benutzername ist zu kurz!");
			}else{
				try {
					BusinessCtx.getInstance().getUserService()
							.getUserByName(username);
					this.addInvalidMessage(ctx, "username",
							"Dieser Benutzername existiert bereits!");
				} catch (UsernameNotFoundException unfe) {
	
				}
			}
		}
	}

	private void validatePasswords(ValidationContext ctx, String password,
			String retype) {
		if (password == null || retype == null || (!password.equals(retype))) {
			this.addInvalidMessage(ctx, "password",
					"Ihre Passw\u00f6rter stimmen nicht \u00fcberein!");
		}else if(!password.matches("^\\S{6,}$")){
			this.addInvalidMessage(ctx, "password",
					"Ihr Passwort ist zu kurz!");
		}
	}

	private void validateEmail(ValidationContext ctx, String email) {
		if (email == null || !email.matches(".+@.+\\.[a-z]+")) {
			this.addInvalidMessage(ctx, "email", SpringPropertiesUtil.getProperty("err.chooseValidMail"));
		}
	}

}

package de.htw.fb4.bilderplattform.view.vm;

import java.io.IOException;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;
/************************************************
 * <p>VM to the passwort_reset.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 05.01.2013
 * </p>
 ************************************************/
public class PasswordResetVM {

	@Wire
	Label lblError;
	@Wire
	Label lblSuccess;
	@Wire
	Label lblEmailValid;
	@Wire
	Label lblUserValid;
	
	private String userName;
	private String email;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Command
    public void submit() {
		lblError.setValue("");
		lblSuccess.setValue("");
		lblEmailValid.setValue("");
		lblUserValid.setValue("");
		try {
			IUserService userService = BusinessCtx.getInstance().getUserService();
			User usr = userService.getUserByName(userName);
			if(!usr.getEmail().equalsIgnoreCase(email)) {
				lblEmailValid.setValue(SpringPropertiesUtil.getProperty("err.emailMatch"));
			}
			else {
				String randString = RandomStringUtils.randomAlphabetic(6);
				usr.setPassword(randString);
				userService.saveOrUpdateUser(usr);
				sendNewPassword(randString, usr.getEmail());
			}
			//msg.passwordResetSuccess
			lblSuccess.setValue(SpringPropertiesUtil.getProperty("msg.passwordResetSuccess"));
		} catch (UsernameNotFoundException e) {
			lblUserValid.setValue(SpringPropertiesUtil.getProperty("err.userDoesntExist1") +
					" \"" + userName + "\" " +
					SpringPropertiesUtil.getProperty("err.userDoesntExist2"));
		} catch (AddressException e) {
			lblError.setValue(SpringPropertiesUtil.getProperty("err.mailSendError"));
		} catch (IOException e) {
			lblError.setValue(SpringPropertiesUtil.getProperty("err.mailSendError"));
		} catch (MessagingException e) {
			lblError.setValue(SpringPropertiesUtil.getProperty("err.mailSendError"));
		}
	}
	
	private void sendNewPassword(String newPassword, String email) throws AddressException, IOException, MessagingException {
		String companyName = SpringPropertiesUtil.getProperty("lbl.companyName");		
		// create email
		IMail mail = new MailImpl();
		mail.setSender(companyName)
			.setReceiver(email)
			.setSubject("[" + companyName +"]" + "Neues Passwort")
			.setMessage(preparedMailMessage(newPassword))
			.setTimeStamp(Calendar.getInstance().getTime());
		BusinessCtx.getInstance().getMailService().sendMail(mail);
	}
	
	private String preparedMailMessage(String password) {
		StringBuilder sbuild = new StringBuilder();
		sbuild.append("Ihr Passwort bei PIKITIA wurde geändert zu:\r\n\r\n");
		sbuild.append(password);
		sbuild.append("\r\n\r\n");
		return sbuild.toString();
	}
}

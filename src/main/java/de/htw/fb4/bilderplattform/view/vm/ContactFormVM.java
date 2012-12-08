package de.htw.fb4.bilderplattform.view.vm;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;
/************************************************
 * <p>VM to the contactForm.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 08.12.2012
 * </p>
 ************************************************/
public class ContactFormVM {
	
	private static final Logger logger = Logger.getLogger(ContactFormVM.class);
	
	private IUserService userService = BusinessCtx.getInstance().getUserService();
	
	private Integer receiverId = -1;
	private String receiverName;
	private String nameSender;
	private String emailSender;
	private String subject;
	private String text;
	
	public ContactFormVM() {
		try {
			Session session = Sessions.getCurrent();
			String userIdFromSession = (String) session.getAttribute("receiver_idUser");
			if(userIdFromSession != null) {
				logger.debug("userid in session is: " + userIdFromSession);
				this.receiverId = new Integer(userIdFromSession);
				User usr = userService.getUserByID(receiverId);
				this.receiverName = usr.getUsername();
				logger.debug("user with id: " + userIdFromSession + " is " + receiverName);
			}
			else {
				logger.debug("no user in session - contact form wont work");
				this.receiverId = -1;
				this.receiverName = SpringPropertiesUtil.getProperty("msg.empty");
			}
		} catch (Exception e) {
			logger.error("exception while setting user details");
			e.printStackTrace();
		}
	}
	
	/* getter/setter */
	
    public Integer getReceiverId() {
		return receiverId;
	}
    
    public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}
	
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getNameSender() {
		return nameSender;
	}

	public void setNameSender(String nameSender) {
		this.nameSender = nameSender;
	}

	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	/* more functionality */

	private void resetForm() {
		nameSender = "";
		emailSender = "";
		subject = "";
		text = "";
	}
	
	/* commands */

	@Command
    public void submit() {
		logger.info("contact message is without formal errors");
		logger.debug("message details = receiver: " + receiverName + 
				" sender: " + nameSender +
				" email sender: " + emailSender +
				" subject: " + subject + 
				" message: " + text);
		
		String companyName = SpringPropertiesUtil.getProperty("lbl.companyName");		
		// create email
		IMail mail = new MailImpl();
		mail.setSender(companyName)
			.setReceiver(emailSender)
			.setSubject("[" + companyName +"]" + subject)
			.setMessage(preparedMailMessage())
			.setTimeStamp(Calendar.getInstance().getTime());
		
		// create message
		Message msg = new Message(
				null, receiverId, emailSender, 1, subject, text);
		
		// send email and persist message
		try {
			BusinessCtx.getInstance().getMessageService().saveMessage(msg);
			logger.debug("message was successfully created");
			BusinessCtx.getInstance().getMailService().sendMail(mail);
			logger.debug("email was successfully sent");
		} catch (Exception e) {
			logger.error("error while sending email or persisting message object");
		}
		
		// reset form
	    resetForm();
    }
	
	
	private String preparedMailMessage() {
		String preStatement = SpringPropertiesUtil.getProperty("msg.mailPreTemplate01") + " "
				+ nameSender + SpringPropertiesUtil.getProperty("msg.mailPreTemplate02") 
				+ " " + receiverName 
				+ " " + SpringPropertiesUtil.getProperty("msg.mailPreTemplate03") + " ";
		String postStatement = " " + SpringPropertiesUtil.getProperty("msg.mailPostTemplate01");
		return preStatement + text + postStatement;
	}
	
}

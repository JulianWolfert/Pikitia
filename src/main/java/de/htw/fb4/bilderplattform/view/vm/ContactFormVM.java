package de.htw.fb4.bilderplattform.view.vm;

import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IImageService;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.dao.Image;
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
	
	@Wire("#contactForm")
	private Window win;
	
	private IUserService userService = BusinessCtx.getInstance().getUserService();
	private IImageService imageService = BusinessCtx.getInstance().getIImageService();
	
	private Integer receiverId = -1;
	private String receiverName;	
	private String nameSender;
	private String emailSender;
	private String subject;
	private String text;
	private User receiver;
	
	//second
	@Init
	public void init(@ExecutionArgParam("imageID") String imageID) {
		try {
			logger.debug("imageID: " + imageID + " was sent to contactForm.zul");
			// the old approach
//			Session session = Sessions.getCurrent();
//			String userIdFromSession = (String) session.getAttribute("receiver_idUser");
			Image img = imageService.getImageByID(Integer.parseInt(imageID));
			int userIdFromSession = img.getUser().getIdUser();
			if(userIdFromSession != 0) {
				logger.debug("userid in session is: " + userIdFromSession);
				this.receiverId = userIdFromSession;
				this.receiver = userService.getUserByID(receiverId);
				this.receiverName = receiver.getUsername();
				logger.debug("user with id: " + userIdFromSession + " is " + receiverName);
			}
			else {
				logger.debug("no user in session - contact form wont work");
				this.receiver = null;
				this.receiverId = -1;
				this.receiverName = SpringPropertiesUtil.getProperty("msg.empty");
			}
		} catch (Exception e) {
			logger.error("exception while setting user details");
			e.printStackTrace();
		}
	}
	
	//first
	public ContactFormVM() {
		
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
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
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
				receiver, emailSender, 1, subject, text);
		
		
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
				+ nameSender + ",\r\n\r\n" + SpringPropertiesUtil.getProperty("msg.mailPreTemplate02") 
				+ " " + receiverName 
				+ " " + SpringPropertiesUtil.getProperty("msg.mailPreTemplate03") + "\r\n\r\n ";
		String postStatement = "\r\n\r\n" + SpringPropertiesUtil.getProperty("msg.mailPostTemplate01");
		return preStatement + text + postStatement;
	}
	
	@Command
	public void testAnswer() {
		final HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("email", "s0537867@htw-berlin.de");
		messageMap.put("subject", "test");
		messageMap.put("userName", "josch");
		
		Executions.createComponents("/user/messageAnswer.zul", null, messageMap);
	}
	
	@Command
	public void closeThis(){
		win.detach();
	}
	
	
}

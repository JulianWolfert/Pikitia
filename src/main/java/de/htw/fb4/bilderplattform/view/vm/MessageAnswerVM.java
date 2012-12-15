package de.htw.fb4.bilderplattform.view.vm;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/************************************************
 * <p>VM to the messageAnswer.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 15.12.2012
 * </p>
 ************************************************/
public class MessageAnswerVM {

	private static final Logger logger = Logger.getLogger(MessageAnswerVM.class);
	
	@Wire("#modalMessageAnswer")
	private Window win;
	
	private String userName;
	private String receiverMail;
	private String subject;
	private String text;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("email") String receiverMail,
			@ExecutionArgParam("subject") String subject, @ExecutionArgParam("userName") String userName) {
		Selectors.wireComponents(view, this, false);
		this.userName = userName;
		this.receiverMail = receiverMail;
		this.subject = subject;
		logger.debug("message answer dialog initialized.");
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	private String preparedMailMessage() {
		String nameSender = userName;
		String s1 = "Auf Ihre PIKITIA Anfrage an " + nameSender + " erhalten Sie folgende Antwort:\r\n\r\n";
		return  s1 + text;
	}
	
	/* commands */

	@Command
    public void submit() {
		logger.info("answer message is without formal errors");
		logger.debug("message details = receiver: " + receiverMail + 
				" sender: " + userName +
				" subject: " + subject + 
				" message: " + text);
		
		String companyName = SpringPropertiesUtil.getProperty("lbl.companyName");		
		// create email
		IMail mail = new MailImpl();
		mail.setSender(companyName)
			.setReceiver(receiverMail)
			.setSubject("Re: " + subject)
			.setMessage(preparedMailMessage())
			.setTimeStamp(Calendar.getInstance().getTime());		
		
		// send email
		try {
			BusinessCtx.getInstance().getMailService().sendMail(mail);
			logger.debug("email was successfully sent");
		} catch (Exception e) {
			logger.error("error while sending email");
		}
    }
	

	@Command
	public void closeThis(){
		win.detach();
	}
}

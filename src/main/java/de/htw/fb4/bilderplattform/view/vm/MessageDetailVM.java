package de.htw.fb4.bilderplattform.view.vm;

import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.business.mail.MailImpl;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * @since 05.12.2012
 * @author Benjamin Schock
 * 
 */

public class MessageDetailVM {
	
	private static final Logger logger = Logger.getLogger(MessageAnswerVM.class);
	
	@Wire("#modalMessageDetail")
	private Window win;
	
	@Wire
	private Textbox answerBox;
	
	private Message message;
	
	private String text;
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedMessage") Message selectedMessage) {
		this.message = selectedMessage;
	}
	
	@Command
	public void contact() {
		closeThis();
		final HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("email", message.getEmail());
		messageMap.put("subject", message.getSubject());
		messageMap.put("userName", message.getReceiver().getUsername());
		
		Executions.createComponents("/user/messageAnswer.zul", null, messageMap);
//		Sessions.getCurrent().setAttribute("receiver_idUser", "2");
//		Executions.getCurrent().sendRedirect("/contactForm.zul");
	}
	
	@Command
	public void submit() {
		logger.info("answer message is without formal errors");
		logger.debug("message details = receiver: " + message.getEmail() + 
				" sender: " + BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser().getUsername() +
				" subject: " + message.getSubject() + 
				" message: " + text);
		
		String companyName = SpringPropertiesUtil.getProperty("lbl.companyName");		
		// create email
		IMail mail = new MailImpl();
		mail.setSender(companyName)
			.setReceiver(message.getEmail())
			.setSubject("Re: " + message.getSubject())
			.setMessage(preparedMailMessage())
			.setTimeStamp(Calendar.getInstance().getTime());		
		
		// send email
		try {
			BusinessCtx.getInstance().getMailService().sendMail(mail);
			logger.debug("email was successfully sent");
			Messagebox.show(SpringPropertiesUtil.getProperty("msg.answerSuccess"), "Info", Messagebox.OK, Messagebox.INFORMATION);
			closeThis();
		} catch (Exception e) {
			logger.error("error while sending email");
		}
	}
	
	private String preparedMailMessage() {
		String nameSender = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser().getUsername();
		String s1 = "Auf Ihre PIKITIA Anfrage an " + nameSender + " erhalten Sie folgende Antwort:\r\n\r\n";
		return  s1 + text;
	}
	
	@Command
	public void closeThis() {
		this.win.detach();
	}
}





package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.dao.Message;

/**
 * @since 05.12.2012
 * @author Benjamin Schock
 * 
 */

public class MessageDetailVM {
	
	@Wire("#modalMessageDetail")
	private Window win;
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedMessage") Message selectedMessage) {
		Selectors.wireComponents(view, this, false);
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
	public void closeThis() {
		this.win.detach();
	}
}





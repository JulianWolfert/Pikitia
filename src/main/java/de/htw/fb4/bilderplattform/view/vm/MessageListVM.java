package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * @since 05.12.2012
 * @author Benjamin Schock
 * 
 */

public class MessageListVM extends WebsiteLayoutVM {
	
	@Wire("#modalMessageList")
	private Window win;
	
	private class MessageModelList extends ListModelList<Message>{
		private static final long serialVersionUID = 1L;
		private Message selectedMessage = null;

		public MessageModelList(Collection<Message> messageList) {
			super(messageList);
		}
		
		public void setSelectedMessage(Message selectedMessage){
			this.selectedMessage = selectedMessage;
			ArrayList<Message> list = new ArrayList<>();
			list.add(selectedMessage);
			this.setSelection(list);
		}
	
		public Message getSelectedMessage(){
			return this.selectedMessage;
		}
	} 
	

	private static final long serialVersionUID = 1L;
	private User user = BusinessCtx.getInstance().getUserService().getCurrentlyLoggedInUser();
	private MessageModelList messageList = new MessageModelList(BusinessCtx.getInstance().getMessageService().getMessageList(user.getIdUser())) ;
	

	public ListModelList<Message> getMessageList() {
		return this.messageList;
	}

	
	public void setSelectedMessage(Message message) {
		this.messageList.setSelectedMessage(message);
	}

	
	public Message getSelectedMessage() {
		return this.messageList.getSelectedMessage();
	}

	@Init
	public void init() {
		if (!this.messageList.isEmpty()) {
			this.setSelectedMessage(messageList.get(0));
		}
	}

	
	@Command
	public void openMessage(@BindingParam("message") final Message message){
		Messagebox.show("bla");
	}
	

	@Command
	public void deleteMessage(@BindingParam("message") final Message message) {
		Messagebox.show("Nachricht loeschen?", "Nachricht loeschen", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (((Integer) event.getData()).intValue() == Messagebox.YES) {
					BusinessCtx.getInstance().getMessageService().deleteMessage(message);
					MessageListVM.this.messageList.remove(message);
					if (!MessageListVM.this.messageList.isEmpty()) {
						MessageListVM.this.messageList.setSelectedMessage(MessageListVM.this.messageList.get(0));
					}
					return;
				}
			}
		});
	}
	
	
	@Command
	public void showMessageDetail() {
		final HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("selectedMessage", this.messageList.getSelectedMessage());
		
		Executions.createComponents("/user/messageDetail.zul", null, messageMap);
    }
	
	
	@Command
	public void closeThis(){
		win.detach();
	}
}






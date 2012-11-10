package de.htw.fb4.bilderplattform.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;


/**
 * 
 * @author konitzer
 *
 */
public class DBInitialization implements InitializingBean{
	
	private final static List<User> adminUsers = new ArrayList<User>();
	private final static List<Message> initialMessages = new ArrayList<Message>();
	
	public DBInitialization(){
		adminUsers.add(new User("wojtek", "wojtek", "w.konitzer@gmx.de", true, true, false));
		adminUsers.add(new User("josch", "josch", "s0537867@htw-berlin.de", true, true, false));
		adminUsers.add(new User("peter", "peter", "s0523841@htw-berlin.de", true, true, false));
		adminUsers.add(new User("ben", "ben", "s0528397@htw-berlin.de", true, true, false));
		adminUsers.add(new User("julian", "julian", "bla@htw-berlin.de", true, true, false));
		
		initialMessages.add(new Message(4, 3, "Titel_01", "Text_01"));
		initialMessages.add(new Message(4, 3, "Titel_02", "Text_02"));
		initialMessages.add(new Message(3, 4, "Titel_03", "Text_03"));
		initialMessages.add(new Message(3, 4, "Titel_04", "Text_04"));
	}
	
	private void createAdminUsers(){
		for(User user : adminUsers){
			BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		}
	}
	
	private void createInitialMessages(){
		for(Message message : initialMessages){
		//	BusinessCtx.getInstance().getMessageService().saveMessage(message);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createAdminUsers();
		createInitialMessages();
	}
}
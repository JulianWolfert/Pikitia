package de.htw.fb4.bilderplattform.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;


/**
 * 
 * @author Wojciech Konitzer
 * 
 * TODO: moving to correct directory
 *
 */
public class DBInitialization implements InitializingBean{
	
	private final static List<User> users = new ArrayList<User>();
	private final static List<Message> initialMessages = new ArrayList<Message>();
	
	public DBInitialization(){
		//add normal users
		users.add(new User("wojtek", "wojtek", "w.konitzer@gmx.de", true, false, false));
		
		//add admin users
		users.add(new User("josch", "josch", "s0537867@htw-berlin.de", true, true, false));
		users.add(new User("peter", "peter", "s0523841@htw-berlin.de", true, true, false));
		users.add(new User("ben", "ben", "s0528397@htw-berlin.de", true, true, false));
		users.add(new User("julian", "julian", "bla@htw-berlin.de", true, true, false));
		users.add(new User("jonathan", "jonathan", "s0538298@htw-berlin.de", true, true, false));
		
		initialMessages.add(new Message(4, 3, 1, "Titel_01", "Text_01"));
		initialMessages.add(new Message(4, 3, 1, "Titel_02", "Text_02"));
		initialMessages.add(new Message(3, 4, 1, "Titel_03", "Text_03"));
		initialMessages.add(new Message(3, 4, 1, "Titel_04", "Text_04"));
		
		
	}
	
	private void createUsers(){
		for(User user : users){
			BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		}
	}
	
	private void createInitialMessages(){
		for(Message message : initialMessages){
			BusinessCtx.getInstance().getMessageService().saveMessage(message);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createUsers();
		createInitialMessages();
	}
	
	
}
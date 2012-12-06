package de.htw.fb4.bilderplattform.spring;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;
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
	private final static List<String> initialPicturePath = new ArrayList<String>();
	
	public DBInitialization(){
		//add normal users
		users.add(new User("wojtek", "wojtek", "w.konitzer@gmx.de", true, false, false));
		
		//add admin users
		users.add(new User("josch", "josch", "s0537867@htw-berlin.de", true, true, false));
		users.add(new User("peter", "peter", "s0523841@htw-berlin.de", true, true, false));
		users.add(new User("ben", "ben", "s0528397@htw-berlin.de", true, true, false));
		users.add(new User("julian", "julian", "bla@htw-berlin.de", true, true, false));
		users.add(new User("jonathan", "jonathan", "s0538298@htw-berlin.de", true, true, false));
				
		initialMessages.add(new Message(4, 3, "s0528397@htw-berlin.de", 1, "Titel_01", "Text_01"));
		initialMessages.add(new Message(4, 3, "s0528397@htw-berlin.de", 1, "Titel_02", "Text_02"));
		initialMessages.add(new Message(3, 4, "s0528397@htw-berlin.de", 1, "Titel_03", "Text_03"));
		initialMessages.add(new Message(3, 4, "s0528397@htw-berlin.de", 1, "Titel_04", "Text_04"));
		
		//TODO: file input stream / dann setImageData mit stream und image object
		// anschliesend setOrUpdateImage
		initialPicturePath.add("/images/initbild1.jpg");
		initialPicturePath.add("/images/initbild2.jpg");
		initialPicturePath.add("/images/initbild3.jpg");
		initialPicturePath.add("/images/initbild4.jpg");
		
	}
	
	private void createInitialPictures() throws FileNotFoundException{
		Integer counter = 0;
		for(String string : initialPicturePath){
			InputStream input = new FileInputStream(string);
			// TODO: image object noch mit infos fuellen
			//  dann input stream übergeben.
			
			Image image = new Image();
			image.setDescription("eine testdatei"+ counter +"ole ole");
			counter ++;
			image.setPrice(23.42);
			image.setTimeStamp(new Date());
			image.setTitle("ein Testbild" + counter);
			image.setUser(BusinessCtx.getInstance().getUserService().getUserByName("jonathan"));
			
			BusinessCtx.getInstance().getIImageService().saveOrUpdateImage(image, input);
		}
		
		
		
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
		createInitialPictures();
	}
	
	
}
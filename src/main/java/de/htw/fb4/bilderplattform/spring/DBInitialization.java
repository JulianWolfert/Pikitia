package de.htw.fb4.bilderplattform.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.ImageDAOImpl;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;


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
	private final static List<String> initialPictures = new ArrayList<String>();
	private final static List<String> initialThumbnailPictures = new ArrayList<String>();
	
	
	public DBInitialization(){
		//add normal users
		users.add(new User("wojtek", "wojtek", "w.konitzer@fhtw-berlin.de", true, false, false));
		
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
		
		initialPictures.add(this.getClass().getResource("/initimages/1.jpg").getFile());
		initialPictures.add(this.getClass().getResource("/initimages/2.jpg").getFile());
		initialPictures.add(this.getClass().getResource("/initimages/3.jpg").getFile());
		
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/1_thumbnail.jpg").getFile());
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/2_thumbnail.jpg").getFile());
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/3_thumbnail.jpg").getFile());
	}
	
	private void createInitialPictures() throws FileNotFoundException{
		Integer imgId = 0;
		User user = BusinessCtx.getInstance().getUserService().getUserByName("jonathan");
		
		for(String filename : initialPictures){
			InputStream input = new FileInputStream(filename);
			// TODO: image object noch mit infos fuellen
			//  dann input stream übergeben.
			Image image = new Image();
			image.setDescription("description"+ imgId);
			image.setPrice(23.42);
			image.setTimeStamp(new Date());
			image.setTitle("title" + imgId);
			imgId ++;
			
			//An der Stelle haben wir keinen eingeloggten User!
			BusinessCtx.getInstance().getIImageService().saveOrUpdateImage(image, input, user);
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
	
	private byte[] convertFileToBytes(File file) {
		FileInputStream fileInputStream;
		byte[] data = null;
		try {
			fileInputStream = new FileInputStream(file);
			data = new byte[(int) file.length()];
			fileInputStream.read(data);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	
	
}
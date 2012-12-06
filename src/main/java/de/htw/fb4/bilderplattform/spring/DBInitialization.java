package de.htw.fb4.bilderplattform.spring;

import java.io.FileNotFoundException;
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
	private final static List<byte[]> initialPictures = new ArrayList<byte[]>();
	private final static List<byte[]> initialPreviewPictures = new ArrayList<byte[]>();
	private final static List<byte[]> initialThumbnailPictures = new ArrayList<byte[]>();
	
	
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
		
		this.getClass().getResource("/initimages/1.jpg").getFile().getBytes();
		
		initialPictures.add(this.getClass().getResource("/initimages/1.jpg").getFile().getBytes());
		initialPictures.add(this.getClass().getResource("/initimages/2.jpg").getFile().getBytes());
		initialPictures.add(this.getClass().getResource("/initimages/3.jpg").getFile().getBytes());	
		
		initialPreviewPictures.add(this.getClass().getResource("/initimages/1_preview.jpg").getFile().getBytes());
		initialPreviewPictures.add(this.getClass().getResource("/initimages/2_preview.jpg").getFile().getBytes());
		initialPreviewPictures.add(this.getClass().getResource("/initimages/3_preview.jpg").getFile().getBytes());
		
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/1_preview.jpg").getFile().getBytes());
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/2_preview.jpg").getFile().getBytes());
		initialThumbnailPictures.add(this.getClass().getResource("/initimages/3_preview.jpg").getFile().getBytes());	
	}
	
	private void createInitialPictures() throws FileNotFoundException{
		Integer imgId = 0;
		for(byte[] picture : initialPictures){
//			InputStream input = new FileInputStream(string);
			// TODO: image object noch mit infos fuellen
			//  dann input stream übergeben.
			Image image = new Image();
			image.setDescription("description"+ imgId);
			
			image.setPrice(23.42);
			image.setTimeStamp(new Date());
			image.setTitle("title" + imgId);
			image.setUser(BusinessCtx.getInstance().getUserService().getUserByName("jonathan"));
		
			image.setPreview_file(initialPreviewPictures.get(imgId));
			image.setThumbnail_file(initialThumbnailPictures.get(imgId));
			
			imgId ++;
			image.setFile(picture);
			
			ImageDAOImpl imageDAO = ApplicationContextProvider
					.getApplicationContext()
					.getBean("imageDao", ImageDAOImpl.class);
			
			
			imageDAO.saveImage(image);
			
			//An der Stelle haben wir keinen eingeloggten User!
//			BusinessCtx.getInstance().getIImageService().saveOrUpdateImage(image, input);
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
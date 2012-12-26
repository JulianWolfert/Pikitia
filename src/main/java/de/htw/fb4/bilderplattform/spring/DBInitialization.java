package de.htw.fb4.bilderplattform.spring;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.zkoss.image.AImage;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IImageService;
import de.htw.fb4.bilderplattform.business.util.FileUtil;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.context.ServletContextProvider;

/**
 * 
 * @author Wojciech Konitzer
 * 
 *         TODO: moving to correct directory
 * 
 */
public class DBInitialization implements InitializingBean {
	private final static List<User> users = new ArrayList<User>();
	private final static List<Message> initialMessages = new ArrayList<Message>();
	private final static List<String> initialPictures = new ArrayList<String>();

	public DBInitialization() {
		// add normal users
		users.add(new User("wojtek", "wojtek", "w.konitzer@fhtw-berlin.de",
				true, false, false));

		// add admin users
		users.add(new User("josch", "josch", "s0537867@htw-berlin.de", true,
				true, false));
		users.add(new User("peter", "peter", "s0523841@htw-berlin.de", true,
				true, false));
		users.add(new User("ben", "ben", "s0528397@htw-berlin.de", true, true,
				false));
		users.add(new User("julian", "julian", "bla@htw-berlin.de", true, true,
				false));
		users.add(new User("jonathan", "jonathan", "s0538298@htw-berlin.de",
				true, true, false));

		initialMessages.add(new Message(users.get(3), "s0528397@htw-berlin.de",
				1, "Nachricht_1", "Lorem Ipsum 1"));
		initialMessages.add(new Message(users.get(3), "s0528397@htw-berlin.de",
				1, "Nachricht_2", "Lorem Ipsum 2"));
		initialMessages.add(new Message(users.get(2), "s0528397@htw-berlin.de",
				1, "Nachricht_3", "Lorem Ipsum 3"));
		initialMessages.add(new Message(users.get(2), "s0528397@htw-berlin.de",
				1, "Nachricht_4", "Lorem Ipsum 4"));

		 initialPictures.add("1.jpg");
		 initialPictures.add("2.jpg");
		 initialPictures.add("3.jpg");

	}

	private void createInitialPictures() throws IOException {
		Integer imgId = 0;
		IImageService imageService = BusinessCtx.getInstance().getImageService();
		User user = BusinessCtx.getInstance().getUserService()
				.getUserByName("jonathan");
		
		for (String filename : initialPictures) {			
			System.out.println("Initialisiere Image: " + imageService.getImagePath(filename));
			File file = new File(imageService.getImagePath(filename));
			InputStream input = new FileInputStream(file);
			Image image = new Image();
			image.setDescription("description" + imgId);
			image.setPrice(23.42);
			image.setTimeStamp(new Date());
			image.setTitle("title" + imgId);
			imgId++;
			
			byte[] img_data;
			try {
				img_data = FileUtil.fileToByte(file);
				org.zkoss.zul.Image img_gui = new org.zkoss.zul.Image();
				org.zkoss.image.AImage img_preview = new AImage("img"+imgId,
						new ByteArrayInputStream(img_data));
				img_gui.setContent(img_preview);
				imageService.saveOrUpdateImage(image, img_gui, user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private void createUsers() {
		for (User user : users) {
			BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		}
	}

	private void createInitialMessages() {
		for (Message message : initialMessages) {
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

package de.htw.fb4.bilderplattform.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Message;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

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
	private final static List<Resource> initialPictures = new ArrayList<Resource>();
	private final static List<Resource> initialThumbnailPictures = new ArrayList<Resource>();

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

		initialMessages.add(new Message(users.get(3), "s0528397@htw-berlin.de", 1, 
				"Nachricht_1", "Lorem Ipsum 1"));
		initialMessages.add(new Message(users.get(3), "s0528397@htw-berlin.de", 1, 
				"Nachricht_2", "Lorem Ipsum 2"));
		initialMessages.add(new Message(users.get(2), "s0528397@htw-berlin.de", 1, 
				"Nachricht_3", "Lorem Ipsum 3"));
		initialMessages.add(new Message(users.get(2), "s0528397@htw-berlin.de", 1, 
				"Nachricht_4", "Lorem Ipsum 4"));

		// add images
		initialPictures.add(ApplicationContextProvider.getApplicationContext()
				.getResource("classpath:initimages/1.jpg"));
		initialPictures.add(ApplicationContextProvider.getApplicationContext()
				.getResource("classpath:initimages/2.jpg"));
		initialPictures.add(ApplicationContextProvider.getApplicationContext()
				.getResource("classpath:initimages/3.jpg"));

		// initialThumbnailPictures.add(ApplicationContextProvider.getApplicationContext().getResource("classpath:initimages/1_thumbnail.jpg"));
		// initialThumbnailPictures.add(ApplicationContextProvider.getApplicationContext().getResource("classpath:initimages/2_thumbnail.jpg"));
		// initialThumbnailPictures.add(ApplicationContextProvider.getApplicationContext().getResource("classpath:initimages/3_thumbnail.jpg"));

	}

	private void createInitialPictures() throws IOException {
		Integer imgId = 0;
		User user = BusinessCtx.getInstance().getUserService()
				.getUserByName("jonathan");

		for (Resource resource : initialPictures) {
			InputStream input = resource.getInputStream();
			Image image = new Image();
			image.setDescription("description" + imgId);
			image.setPrice(23.42);
			image.setTimeStamp(new Date());
			image.setTitle("title" + imgId);
			imgId++;
			BusinessCtx.getInstance().getIImageService()
					.saveOrUpdateImage(image, input, user);
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

	
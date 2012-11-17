package de.htw.fb4.bilderplattform.business;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.ImageDAOImpl;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;
/**
 * 
 * @author Wojciech Konitzer
 * 
 * 17.11.2012
 *
 */
public class ImageServiceImpl implements IImageService {

	@Override
	public List<Image> getImagesByUsername(String username) {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("imageDao", ImageDAOImpl.class);
		List<Image> allImagesByUsername = imageDAO.getAllImagesByUsername(username);
		return allImagesByUsername;
	}

	@Override
	public List<Image> getAllImages() {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("imageDao", ImageDAOImpl.class);
		return imageDAO.getAllImages();
	}

	@Override
	public void saveOrUpdateImage(Image image) {
		if (image == null) {
			return;
		}
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("imageDao", ImageDAOImpl.class);
		image.setTimeStamp(Calendar.getInstance().getTime());
		imageDAO.saveImage(image);
	}

	@Override
	public void deleteImage(Image image) {
		if (image == null) {
			return;
		}
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext().getBean("imageDao", ImageDAOImpl.class);
		imageDAO.deleteImage(image);
	}

}

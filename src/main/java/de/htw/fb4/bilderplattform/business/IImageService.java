package de.htw.fb4.bilderplattform.business;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.User;

/************************************************
 * <p>Defines image uploading functionality</p>
 * <p>
 * @author Wojciech Konitzer
 * </p>
 * <p>
 * 17.11.2012
 * </p>
 ************************************************/
public interface IImageService {

	public List<Image> getImagesByUsername(String username);

	public List<Image> getAllImages();
	
	public Image getImageByID(int idImage);
	
	public void saveOrUpdateImage(Image image, User user);
	
	public void saveOrUpdateImage(Image image, org.zkoss.zul.Image zkImage, User user);
	
	public void deleteImage(Image image);
	
	public Integer getLastInsertedImageID();
	
	public String getImagePath();
	
	public String getImagePath(String filename);
}


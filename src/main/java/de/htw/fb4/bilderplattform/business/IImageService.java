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
	
	public void saveOrUpdateImage(Image image);
	
	public void saveOrUpdateImage(Image image, File imageFile, InputStream imageData);
	
	public InputStream getImageData(Image image);
	
	public void deleteImage(Image image);
	
}


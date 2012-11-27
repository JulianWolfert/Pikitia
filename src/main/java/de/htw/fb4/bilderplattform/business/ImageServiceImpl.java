package de.htw.fb4.bilderplattform.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
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
 *         17.11.2012
 * 
 */
public class ImageServiceImpl implements IImageService {

	@Override
	public List<Image> getImagesByUsername(String username) {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		List<Image> allImagesByUsername = imageDAO
				.getAllImagesByUsername(username);
		return allImagesByUsername;
	}

	@Override
	public List<Image> getAllImages() {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		return imageDAO.getAllImages();
	}

	@Override
	public void saveOrUpdateImage(Image image, File imageFile,
			InputStream imageData) {
		if (image == null) {
			return;
		}
		if (this.uploadImageToDirectory(image, imageFile, imageData)) {
			image.setFilename(imageFile.getAbsolutePath());
			this.saveOrUpdateImage(image);
		}
	}

	@Override
	public void saveOrUpdateImage(Image image) {
		if (image == null) {
			return;
		}
		image.setTimeStamp(new Date());
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		image.setTimeStamp(Calendar.getInstance().getTime());
		imageDAO.saveImage(image);
	}

	@Override
	public void deleteImage(Image image) {
		if (image == null) {
			return;
		}
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		imageDAO.deleteImage(image);
		this.deleteImageData(image);
	}

	@Override
	public InputStream getImageData(Image image) {
		try {
			return new FileInputStream(new File(image.getFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void deleteImageData(Image image) {
		File f = new File(image.getFilename());
		f.delete();
	}

	private boolean uploadImageToDirectory(Image image, File imageFile,
			InputStream imageData) {
		int b = -1;
		boolean returnedValue = false;
		OutputStream os = null;
		File oldImageFile = image.getFilename() != null ? new File(
				image.getFilename()) : null;
		try {
			// delete old image if exists
			if (oldImageFile != null) {
				if (oldImageFile.exists()) {
					oldImageFile.delete();
				}
			}
			imageFile.createNewFile();
			os = new FileOutputStream(imageFile);
			while ((b = imageData.read()) != -1) {
				os.write(b);
			}
			returnedValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (imageData != null) {
					imageData.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnedValue;
	}
}

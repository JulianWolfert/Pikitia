package de.htw.fb4.bilderplattform.business;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import de.htw.fb4.bilderplattform.business.util.FileUtil;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.ImageDAOImpl;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;
import de.htw.fb4.bilderplattform.spring.context.ServletContextProvider;

/**
 * 
 * @author Wojciech Konitzer
 * 
 *         17.11.2012
 * 
 */
public class ImageServiceImpl implements IImageService {
	// the size of the preview and thumb images in pixels
	private static final int PREVIEW_SIZE_PX = 800;
	private static final int THUMB_SIZE_PX = 170;

	//gefixt: wk, 26.12.2012 :-)
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
	public String getUsername(int idImage) {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		Image image = imageDAO.getImageByID(idImage);
		if(image != null){
			return image.getUser().getUsername();
		}
		return null;
	}

	@Override
	public void saveOrUpdateImage(Image image, User user) {
		if (image == null) {
			return;
		}
		image.setTimeStamp(new Date());
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		image.setTimeStamp(Calendar.getInstance().getTime());
		image.setUser(user);
		imageDAO.saveImage(image);
	}

	@Override
	public void saveOrUpdateImage(Image image, org.zkoss.zul.Image zkImage, User user) {
		this.setImageData(image, zkImage);
		this.saveOrUpdateImage(image, user);
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
	}
	
	@Override
	public Integer getLastInsertedImageID() {
		ImageDAOImpl imageDAO = ApplicationContextProvider.getApplicationContext().getBean("imageDao", ImageDAOImpl.class);
		return imageDAO.getLastInsertedImageID();
	}

	private void setImageData(Image image, org.zkoss.zul.Image zkImage) {
		if (zkImage == null || image == null) {
			return;
		}
		int b = -1;
		byte[] data = null;
		byte[] data_preview = null;
		byte[] data_thumb = null;
		InputStream in = zkImage.getContent().getStreamData();
		ArrayList<Integer> bytes = new ArrayList<Integer>();
		try {
			while ((b = in.read()) != -1) {
				bytes.add(b);
			}
			if (bytes.size() > 0) {
				// original
				data = new byte[bytes.size()];
				for (int i = 0; i < data.length; i++) {
					data[i] = bytes.get(i).byteValue();
				}
				data_preview = this.scaleImg(data, PREVIEW_SIZE_PX);			
				data_thumb = this.scaleImg(data_preview, THUMB_SIZE_PX);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Integer lastInsertedID = BusinessCtx.getInstance().getImageService().getLastInsertedImageID();
		if(lastInsertedID == null){
			lastInsertedID = 1;
		}else{
			lastInsertedID += 1;
		}
		String generatedFilename = String.valueOf(lastInsertedID);
		String imagesPath = BusinessCtx.getInstance().getImageService()
				.getImagePath();
		String filePath = imagesPath
				+ File.separator;

		String fileType = zkImage.getContent().getFormat();
		if(fileType != null) {
			generatedFilename += "." + fileType;
		}
		image.setFile(generatedFilename);
		
		FileUtil.saveFile(filePath + generatedFilename, data);
		
		String previewFilePath = imagesPath
				+ File.separator + "preview_" + image.getFile();
		FileUtil.saveFile(previewFilePath, data_preview);
		image.setPreview_file("preview_" + image.getFile());
		
		String thumbFilePath = imagesPath
				+ File.separator + "thumb_" + image.getFile();
		FileUtil.saveFile(thumbFilePath, data_thumb);
		image.setThumb_file("thumb_" + image.getFile());
	}
	
	private byte[] scaleImg(byte[] data, int pixel) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		BufferedImage img = ImageIO.read(in);
		in.close();
		int size = img.getHeight() > img.getWidth() ? img.getWidth()
				: img.getHeight();
		double scale = (double) pixel / (double) size;

		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(op.filter(img, null), "png", os);
		if(img != null){
			img.flush();
			img = null;
		}
		byte[] bytes = os.toByteArray();
		//close will automatically flush the stream
		os.close();
		return bytes;
	}

	@Override
	public Image getImageByID(int idImage) {
		ImageDAOImpl imageDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("imageDao", ImageDAOImpl.class);
		return imageDAO.getImageByID(idImage);
	}

	@Override
	public String getImagePath() {
		return ServletContextProvider.getServletContext()
				.getRealPath("/images");
	}

	@Override
	public String getImagePath(String filename) {
		return ServletContextProvider.getServletContext().getRealPath(
				"/images/" + filename);
	}

}

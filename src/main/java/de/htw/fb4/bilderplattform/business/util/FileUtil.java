package de.htw.fb4.bilderplattform.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.activation.MimetypesFileTypeMap;

/**
 * 
 * @author Wojciech Konitzer
 * 
 * 22.12.2012
 *
 */
public class FileUtil {

	public static void saveFile(String path, byte[] bytes) {
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] fileToByte(File file) throws Exception, IOException,
			UnsupportedEncodingException {
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fileInputStream.read(data);
		fileInputStream.close();
		return data;
	}

	public static byte[] fileNameToBytes(String filename) {
		if (filename == null) {
			return null;
		}
		File file = new File(filename);
		byte[] img_data = null;
		try {
			img_data = FileUtil.fileToByte(file);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img_data;
	}
	
	public static String getContentType(String filePath){
		if(filePath == null){
			return null;
		}
		File file = new File(filePath);
	    return new MimetypesFileTypeMap().getContentType(file);
	  }
}

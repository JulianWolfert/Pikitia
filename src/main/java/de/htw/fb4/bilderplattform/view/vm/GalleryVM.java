package de.htw.fb4.bilderplattform.view.vm;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

import de.htw.fb4.bilderplattform.business.BusinessCtx;

public class GalleryVM {

	private String keyword;
	
	@Wire(".imageList")
	private Div imageList;
	
	private Image selectedImage;
	

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Image getSelectedImage() {
		return selectedImage;
	}
	public void setSelectedImage(Image selectedImage) {
		this.selectedImage = selectedImage;
	}
	
	public void createGallery() {
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getIImageService().getAllImages();

		int n=0;
		try {
			for (int i=0; i < imgList.size(); i++) {
				byte[] img_data = imgList.get(i).getPreview_file();
				Image img_gui = new Image();	
				AImage img_preview = new AImage("test", new ByteArrayInputStream(img_data));
				img_gui.setContent(img_preview);
				img_gui.setId(imgList.get(i).getIdImage().toString() + n);
				this.imageList.appendChild(img_gui);
				if (i == imgList.size()-1 && n < 10){
					i=0;
					n++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.createGallery();
	}
		
	@Command
	@NotifyChange("imageList")
	public void search() {
		imageList.getChildren().clear();

		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getIImageService().getAllImages();

		int n=0;
		try {
			for (int i=0; i < imgList.size(); i++) {
				byte[] img_data = imgList.get(i).getPreview_file();
				Image img_gui = new Image();	
				AImage img_preview = new AImage("test", new ByteArrayInputStream(img_data));
				img_gui.setContent(img_preview);
				img_gui.setId(imgList.get(i).getIdImage().toString() + n);
				this.imageList.appendChild(img_gui);
				if (i == imgList.size()-1 && n < 4){
					i=0;
					n++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}
	


}

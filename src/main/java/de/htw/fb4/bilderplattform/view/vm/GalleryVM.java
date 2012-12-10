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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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

		generateImages(imgList);
		
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.createGallery();
	}
		
	@Command
	@NotifyChange("imageList")
	public void search() {
		
		if (keyword != "") {
		
			this.imageList.getChildren().clear();
			
			List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
					.getInstance().getSearchService().searchImages(keyword);
	
			generateImages(imgList);
		
		}
		
	}
	
	private void generateImages (List<de.htw.fb4.bilderplattform.dao.Image> imgList) {
		
		try {
			for (int i=0; i < imgList.size(); i++) {
				byte[] img_data = imgList.get(i).getPreview_file();
				Image img_gui = new Image();	
				AImage img_preview = new AImage("test", new ByteArrayInputStream(img_data));
				img_gui.setContent(img_preview);
				img_gui.setId(imgList.get(i).getIdImage().toString());
				img_gui.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						Session session = Sessions.getCurrent();
						List<String> imageIDsSession = (List<String>) session.getAttribute("imageIDs");
						
						if (imageIDsSession == null) {
							List<String> imageIDs = new ArrayList<String>();
							imageIDs.add(event.getTarget().getId());
							session.setAttribute("imageIDs", imageIDs);
						}
						else {
							imageIDsSession.add(event.getTarget().getId());
							session.setAttribute("imageIDs", imageIDsSession);
						}					
					}
				});
				
				this.imageList.appendChild(img_gui);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}

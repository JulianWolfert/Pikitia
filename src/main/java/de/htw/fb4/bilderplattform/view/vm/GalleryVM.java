package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;

public class GalleryVM {

	private String keyword;
	private List<Image> imageList;
	private Image selectedImage;
	
	public GalleryVM() {
		List<Image> initialImages = new ArrayList<Image>();
		
		Image img1 = new Image();
		img1.setFilename("/images/1.jpg");
		img1.setDescription("/images/1_s.jpg");
		Image img2 = new Image();
		img2.setFilename("/images/2.jpg");
		img2.setDescription("/images/2_s.jpg");
		Image img3 = new Image();
		img3.setFilename("/images/3.jpg");
		img3.setDescription("/images/3_s.jpg");
		Image img4 = new Image();
		img4.setFilename("/images/1.jpg");
		img4.setDescription("/images/1_s.jpg");
		Image img5 = new Image();
		img5.setFilename("/images/2.jpg");
		img5.setDescription("/images/2_s.jpg");
		Image img6 = new Image();
		img6.setFilename("/images/3.jpg");
		img6.setDescription("/images/3_s.jpg");
		Image img7 = new Image();
		img7.setFilename("/images/1.jpg");
		img7.setDescription("/images/1_s.jpg");
		Image img8 = new Image();
		img8.setFilename("/images/2.jpg");
		img8.setDescription("/images/2_s.jpg");
		Image img9 = new Image();
		img9.setFilename("/images/3.jpg");
		img9.setDescription("/images/3_s.jpg");
		Image img10 = new Image();
		img10.setFilename("/images/1.jpg");
		img10.setDescription("/images/1_s.jpg");
		Image img11 = new Image();
		img11.setFilename("/images/2.jpg");
		img11.setDescription("/images/2_s.jpg");
		Image img12 = new Image();
		img12.setFilename("/images/3.jpg");
		img12.setDescription("/images/3_s.jpg");
		
		initialImages.add(img12);
		initialImages.add(img11);
		initialImages.add(img10);
		initialImages.add(img9);
		initialImages.add(img8);
		initialImages.add(img7);
		initialImages.add(img6);
		initialImages.add(img5);
		initialImages.add(img4);
		initialImages.add(img3);
		initialImages.add(img2);
		initialImages.add(img1);
		
		imageList = initialImages;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Image> getImageList() {
		return imageList;
	}
	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
	public Image getSelectedImage() {
		return selectedImage;
	}
	public void setSelectedImage(Image selectedImage) {
		this.selectedImage = selectedImage;
	}
		
	@Command
	@NotifyChange("imageList")
	public void search() {

		List<Image> initialImages = new ArrayList<Image>();
		
		Image img1 = new Image();
		img1.setFilename("/images/1.jpg");
		img1.setDescription("/images/1_s.jpg");
		Image img2 = new Image();
		img2.setFilename("/images/2.jpg");
		img2.setDescription("/images/2_s.jpg");
		Image img3 = new Image();
		img3.setFilename("/images/3.jpg");
		img3.setDescription("/images/3_s.jpg");

		
		initialImages.add(img3);
		initialImages.add(img2);
		initialImages.add(img1);
		
		imageList = initialImages;
		
		
		
	}
	


}

package de.htw.fb4.bilderplattform.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.htw.fb4.bilderplattform.dao.Image;

public class SearchServiceImpl implements ISearchService {

	@Override
	public List<Image> searchImages(String searchString) {
		IImageService imgService = BusinessCtx.getInstance().getIImageService();
		List<Image> allImgs = imgService.getAllImages();
//		List<Image> allImgs = dummys();
//		
		StringTokenizer tokenizer = new StringTokenizer(searchString, " ");
		Set<Image> targetImages = new HashSet<>();
		while(tokenizer.hasMoreElements()) {
			targetImages.addAll(getRelevantImages(allImgs, tokenizer.nextToken()));
		}
		return new ArrayList<Image>(targetImages);
	}

	private List<Image> getRelevantImages(List<Image> allImages, String token) {
		Pattern pattern = Pattern.compile(token, Pattern.CASE_INSENSITIVE);
		List<Image> relevantImages = new ArrayList<Image>();
		for(Image img : allImages) {
//			Matcher match1 = pattern.matcher(img.getFilename());
			Matcher match2 = pattern.matcher(img.getTitle());
			Matcher match3 = pattern.matcher(img.getDescription());
			if(match2.find() || match3.find())
				relevantImages.add(img);
		}
		return relevantImages;
	}
	
	private List<Image> dummys() {
		List<Image> initialImages = new ArrayList<Image>();
		Image img1 = new Image();
//		img1.setFilename("test.jpg");
		img1.setTitle("Baum im Garten mit Haus");
		img1.setDescription("Hier sieht man einen Baum in einem Garten zu einem Haus.");
		
		Image img2 = new Image();
//		img2.setFilename("Gartenhaus.jpg");
		img2.setTitle("Gartenhaus");
		img2.setDescription("Auf diesem Bild sieht man ein Gartenhaus");
		
		Image img3 = new Image();
//		img3.setFilename("Rose_im_Garten.jpg");
		img3.setTitle("Weisse bluehende Rose in einem Garten");
		img3.setDescription("Hier ist eine weisse, bluehende Rose, also eine Blume, in einem Garten zu sehen.");
		
		Image img4 = new Image();
//		img4.setFilename("Schwarzes_Fass.jpg");
		img4.setTitle("Schwarzes Fass in dunklem Haus");
		img4.setDescription("Ein schwarzes Fass steht in einem dunklen Zimmer.");
		
		initialImages.add(img1);
		initialImages.add(img1);
		initialImages.add(img2);
		initialImages.add(img3);
		initialImages.add(img4);
		return initialImages;
	}
	
	
}

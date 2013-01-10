package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Button;
import org.zkoss.zhtml.I;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.util.ResourcesUtil;
import de.htw.fb4.bilderplattform.business.util.Util;


/************************************************
 * <p>Gallery</p>
 * <p>
 * @author Julian Wolfert
 * </p>
 * <p>
 * 18.12.2012
 * </p>
 ************************************************/
public class GalleryVM {

	//search string
	private String keyword;
	
	//div container for all images
	@Wire(".imageList")
	private Div imageList;
	
	@Wire
	private Label search_result_id;

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.createGallery();
	}
		
	@Command
	@NotifyChange("imageList")
	public void search() {
		
		if (keyword != null) {
		
			this.imageList.getChildren().clear();
			
			List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
					.getInstance().getSearchService().searchImages(keyword);
			
			if (imgList.isEmpty())
				this.search_result_id.setValue("No images found!");
			else {
				this.search_result_id.setValue("We found " + imgList.size() + " images for you!");
				generateImages(imgList);
			}		
		}
		
	}
	
	private void createGallery() {
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getImageService().getAllImages();

		this.search_result_id.setValue("We found " + imgList.size() + " images for you!");
		
		generateImages(imgList);
	}
	
	
	private void generateImages (List<de.htw.fb4.bilderplattform.dao.Image> imgList) {
		
		
		for (final de.htw.fb4.bilderplattform.dao.Image image : imgList) {				
			
			//Thumb
			Div thumb = new Div();
			thumb.setSclass("thumb");
			
			//Image
			Image img_gui = new Image();
			img_gui.setSrc("/images/" + image.getThumb_file());
			
			//ThumbContent
			Div thumb_content = new Div();
			thumb_content.setSclass("thumb_content");
			
			A linkCaption = new A();
			linkCaption.setId("img_" + image.getIdImage().toString());
			linkCaption.setDraggable("true");
			linkCaption.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event e) throws Exception {
					
					String img_id = e.getTarget().getId().substring(4);
					final HashMap<String, Object> ImageIDMap = new HashMap<String, Object>();
					ImageIDMap.put("imageID", img_id);
					ImageIDMap.put("buyButton", "true");
					Executions.createComponents("/gallery_modal.zul", null, ImageIDMap);
					
				}
			});
			
			//Caption
			Div caption = new Div();
			caption.setSclass("caption");
			
			Div caption_div = new Div();
			Label caption_label = new Label(image.getTitle());
			caption_label.setSclass("title");
			
			Div price_div = new Div();
			String price_format = Util.formatDouble(image.getPrice());
			Label price_label = new Label("\u20AC " + price_format);
			price_label.setSclass("price");

			
			
			//Info
			Div info = new Div();
			info.setSclass("info");				
			
			//Message & Star (Left)
			Div left_buttons = new Div();
			left_buttons.setSclass("buttons-left");
			
			//StarIcon
			Button starButton = new Button();
			starButton.setSclass("btn btn-mini");
			starButton.setId("sta_" + image.getIdImage().toString());
			starButton.addEventListener(Events.ON_CLICK, new EventListener() { 
				public void onEvent(Event e) 
				{ 
					String img_id = e.getTarget().getId().substring(4);
					final HashMap<String, Object> commentFormMap = new HashMap<String, Object>();
					commentFormMap.put("imageID", img_id);
					Executions.createComponents("/commentForm.zul", null, commentFormMap);
				} 
			}); 
			I starIcon = new I();
			starIcon.setSclass("icon-star");

			//MessageIcon
			Button messageButton = new Button();
			messageButton.setSclass("btn btn-mini");
			messageButton.setId("mes_" + image.getIdImage().toString());
			messageButton.addEventListener(Events.ON_CLICK, new EventListener() { 
				@Override
				public void onEvent(Event e) 
				{ 
					String img_id = e.getTarget().getId().substring(4);
					final HashMap<String, Object> contactFormMap = new HashMap<String, Object>();
					contactFormMap.put("imageID", img_id);
					Executions.createComponents("/contactForm.zul", null, contactFormMap);
					
//						Executions.sendRedirect("/contactForm.zul");
				} 
			}); 
			I messageIcon = new I();
			messageIcon.setSclass("icon-envelope");

			
			//Cart (Right)
			Div right_buttons = new Div();
			right_buttons.setSclass("buttons-right");
			
			//CartIcon
			Button cartButton = new Button();
			cartButton.setSclass("btn btn-mini");
			cartButton.setId("car_" + image.getIdImage().toString());
			cartButton.addEventListener(Events.ON_CLICK, new EventListener() {
				public void onEvent(Event e)
				{
					String img_id = e.getTarget().getId().substring(4);
					addToCart(img_id);
//					Clients.showNotification("Bild mit ID " + img_id + " hinzugefügt", "info", cartLogo, "top_right",2000);
				}
			});
			I cartIcon = new I();
			cartIcon.setSclass("icon-shopping-cart");


			//Append Children & create structure		
			starButton.appendChild(starIcon);
			left_buttons.appendChild(starButton);

			messageButton.appendChild(messageIcon);
			left_buttons.appendChild(messageButton);
			
			cartButton.appendChild(cartIcon);
			right_buttons.appendChild(cartButton);
			
			info.appendChild(left_buttons);
			info.appendChild(right_buttons);
			
			caption_div.appendChild(caption_label);
			price_div.appendChild(price_label);
			
			caption.appendChild(caption_div);
			caption.appendChild(price_div);
			
			linkCaption.appendChild(caption);
			
			thumb_content.appendChild(linkCaption);
			thumb_content.appendChild(info);
			
			
			thumb.appendChild(img_gui);
			thumb.appendChild(thumb_content);	
			
			
			this.imageList.appendChild(thumb);
		}
		
	}

	private void addToCart(String id) {
		Session session = Sessions.getCurrent();
		List<String> imageIDsSession = (List<String>) session.getAttribute("imageIDs");
		
		if (imageIDsSession == null) {
			List<String> imageIDs = new ArrayList<String>();
			Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", id), "info", null, "top_right",2000);
			imageIDs.add(id);
			session.setAttribute("imageIDs", imageIDs);
		}
		else {
			if(!imageIDsSession.contains(id)) {
				Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", id), "info", null, "top_right",2000);
				imageIDsSession.add(id);
			}
			else
				Clients.showNotification("Bereits vorhanden", "info", null, "top_right",2000);
				
			session.setAttribute("imageIDs", imageIDsSession);
		}
	}

}

package de.htw.fb4.bilderplattform.view.vm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
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
		
		if (keyword != "") {
		
			this.imageList.getChildren().clear();
			
			List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
					.getInstance().getSearchService().searchImages(keyword);
	
			generateImages(imgList);
		
		}
		
	}
	
	private void createGallery() {
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getIImageService().getAllImages();

		generateImages(imgList);
	}
	
	
	private void generateImages (List<de.htw.fb4.bilderplattform.dao.Image> imgList) {
		
		//For evey image in List
		try {
			for (int i=0; i < imgList.size(); i++) {
						
				
				//Thumb
				Div thumb = new Div();
				thumb.setSclass("thumb");
				
				//Image
				byte[] img_data = imgList.get(i).getPreview_file();
				Image img_gui = new Image();
				AImage img_preview = new AImage("test", new ByteArrayInputStream(img_data));
				img_gui.setContent(img_preview);
				
				//ThumbContent
				Div thumb_content = new Div();
				thumb_content.setSclass("thumb_content");
				
				A linkCaption = new A();
				linkCaption.setId("img_" + imgList.get(i).getIdImage().toString());
				linkCaption.setDraggable("true");
				linkCaption.addEventListener(Events.ON_CLICK, new EventListener() {
					@Override
					public void onEvent(Event e) throws Exception {
						
						String img_id = e.getTarget().getId().substring(4);
						final HashMap<String, String> ImageIDMap = new HashMap<String, String>();
						ImageIDMap.put("imageID", img_id);
						Executions.createComponents("/gallery_modal.zul", null, ImageIDMap);
						
					}
				});
				
				//Caption
				Div caption = new Div();
				caption.setSclass("caption");
				
				Div caption_div = new Div();
				Label caption_label = new Label(imgList.get(i).getTitle());
				caption_label.setSclass("title");
				
				Div price_div = new Div();
				Label price_label = new Label("$" + imgList.get(i).getPrice().toString());
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
				starButton.setId("sta_" + imgList.get(i).getIdImage().toString());
				starButton.addEventListener(Events.ON_CLICK, new EventListener() { 
					public void onEvent(Event e) 
					{ 
						//ToDO
					} 
				}); 
				I starIcon = new I();
				starIcon.setSclass("icon-star");

				//MessageIcon
				Button messageButton = new Button();
				messageButton.setSclass("btn btn-mini");
				messageButton.setId("mes_" + imgList.get(i).getIdImage().toString());
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
				cartButton.setId("car_" + imgList.get(i).getIdImage().toString());
				cartButton.addEventListener(Events.ON_CLICK, new EventListener() {
					public void onEvent(Event e)
					{
						String img_id = e.getTarget().getId().substring(4);
						addToCart(img_id);
						Component cartLogo = Path.getComponent("/cartLogo");
						Clients.showNotification("Bild mit ID " + img_id + " hinzugefügt", "info", cartLogo, "top_right",2000);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addToCart(String id) {
		Session session = Sessions.getCurrent();
		List<String> imageIDsSession = (List<String>) session.getAttribute("imageIDs");
		
		if (imageIDsSession == null) {
			List<String> imageIDs = new ArrayList<String>();
			imageIDs.add(id);
			session.setAttribute("imageIDs", imageIDs);
		}
		else {
			imageIDsSession.add(id);
			session.setAttribute("imageIDs", imageIDsSession);
		}
	}

}

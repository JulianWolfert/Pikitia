package de.htw.fb4.bilderplattform.view.vm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;

public class ModalGalleryVM {

	//Wired Elements with sClasses
	@Wire("#modalGalleryImage")
	private Window modalGalleryImage;
	
	@Wire(".imageBig")
	private Div imageDIV;
	
	@Wire(".rating_orange")
	private Div rating_orange;
	
	@Wire(".buttons")
	private Div buttons;
	
	//Wired Elements with IDs
	@Wire
	private Label title_id;
	
	@Wire
	private Label desc_id;
	
	@Wire
	private Label price_id;
	
	@Wire
	private Label rating_id;
	
	@Wire
	private Label uploader_id;
	
	@Wire
	private org.zkoss.zul.Button cartButton;
	
	//Just class members
	private de.htw.fb4.bilderplattform.dao.Image image_obj;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("imageID") String imageID,
			@ExecutionArgParam("buyButton") Boolean buyButton) {
		Selectors.wireComponents(view, this, false);
      
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getImageService().getAllImages();
		if (imageID != null) {
				for (int j=0; j < imgList.size(); j++) {
					if (imgList.get(j).getIdImage().toString().equals(imageID)) {
						
						this.image_obj = imgList.get(j);
						break;
					}
				}
			}
		
		try {		
			//Image
			Image img_gui = new Image();	
			img_gui.setSrc("images/" + image_obj.getPreview_file());
			this.imageDIV.appendChild(img_gui);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//StarButton
		Button starButton = new Button();
		starButton.setSclass("btn btn-mini");
		starButton.addEventListener(Events.ON_CLICK, new EventListener() { 
			public void onEvent(Event e) 
			{ 
				final HashMap<String, Object> commentFormMap = new HashMap<String, Object>();
				commentFormMap.put("imageID", image_obj.getIdImage().toString());
				Executions.createComponents("/commentForm.zul", null, commentFormMap);
			} 
		}); 
		I starIcon = new I();
		starIcon.setSclass("icon-star");

		//MessageButton
		Button messageButton = new Button();
		messageButton.setSclass("btn btn-mini");
		messageButton.addEventListener(Events.ON_CLICK, new EventListener() { 
			public void onEvent(Event e) 
			{ 
				final HashMap<String, Object> contactFormMap = new HashMap<String, Object>();
				contactFormMap.put("imageID", image_obj.getIdImage().toString());
				Executions.createComponents("/contactForm.zul", null, contactFormMap);
			} 
		}); 
		I messageIcon = new I();
		messageIcon.setSclass("icon-envelope");
		
		starButton.appendChild(starIcon);
		messageButton.appendChild(messageIcon);
		
		this.buttons.appendChild(starButton);
		this.buttons.appendChild(messageButton);
		
		rating_orange.setStyle("padding:10px;");
		
		title_id.setValue(this.image_obj.getTitle());
		String username = "-";
		if(this.image_obj.getUser() != null){
			username = this.image_obj.getUser().getUsername();
		}
		uploader_id.setValue("Uploaded by: " + username);
		desc_id.setValue(this.image_obj.getDescription());
		price_id.setValue("\u20AC " + this.image_obj.getPrice().toString().replace(".",","));
		rating_id.setValue("\u00D8 " + String.valueOf(BusinessCtx
				.getInstance().getCommentService().getAverageImageRating(this.image_obj.getIdImage())));
		cartButton.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event e) {
				addToCart(image_obj.getIdImage().toString());
				Component cartLogo = Path.getComponent("/cartLogo");
				Clients.showNotification("Bild mit ID " + image_obj.getIdImage().toString() + " hinzugefügt", "info", cartLogo, "top_right",2000);
				closeModalWindow();
			}
		});
		if (!buyButton)
			cartButton.setDisabled(true);
	}

	@Command("closeModalWindow") 
	public void closeModalWindow ()
	{
		modalGalleryImage.detach();		
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
			if(!imageIDsSession.contains(id))
				imageIDsSession.add(id);
			session.setAttribute("imageIDs", imageIDsSession);
		}
	}

}

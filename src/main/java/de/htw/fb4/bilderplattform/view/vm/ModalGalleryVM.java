package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hpsf.Util;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.util.ResourcesUtil;
import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

public class ModalGalleryVM {

	//Wired Elements with sClasses
	@Wire("#modalGalleryImage")
	private Window modalGalleryImage;
	
	@Wire(".imageBig")
	private Div imageDIV;
	
	@Wire
	private Div rating_orange_id;
	
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
	
	private List<Comment> comments;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, 
			@ExecutionArgParam("imageID") String imageID,
			@ExecutionArgParam("buyButton") Boolean buyButton) {
		Selectors.wireComponents(view, this, false);

		this.image_obj = BusinessCtx.getInstance().getImageService().getImageByID(Integer.parseInt(imageID));
		
//		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
//				.getInstance().getImageService().getAllImages();
			
//		for (int j=0; j < imgList.size(); j++) {
//					
//					if (imgList.get(j).getIdImage().toString().equals(imageID.toString())) {
//						this.image_obj = (imgList.get(j));
//						break;
//					}
//		}

		this.setComments(BusinessCtx.getInstance().getCommentService().getAllCommentsByImage(this.image_obj));
		
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
				commentFormMap.put("image", image_obj);
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
		
		
		title_id.setValue(this.image_obj.getTitle());
		String username = "-";
		if(this.image_obj.getUser() != null){
			username = this.image_obj.getUser().getUsername();
		}
		uploader_id.setValue("Uploaded by: " + username);
		desc_id.setValue(this.image_obj.getDescription());
		price_id.setValue("\u20AC " + de.htw.fb4.bilderplattform.business.util.Util.formatDouble(this.image_obj.getPrice()));
		
		Double avgRatingValue = BusinessCtx
				.getInstance().getCommentService().getAverageImageRating(this.image_obj);
		String avgRatingStr = "\u00D8 " + String.valueOf(avgRatingValue);
		if(avgRatingValue == 0){
			avgRatingStr = SpringPropertiesUtil.getProperty("lbl.noCommentAvailable");	
		}
		rating_id.setValue(avgRatingStr);
		
		
		rating_orange_id.setWidth((avgRatingValue/5)*100 +"%");
		
		cartButton.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event e) {
				addToCart(image_obj.getIdImage().toString());				
//				Clients.showNotification("Bild mit ID " +  + " hinzugefügt", "info", cartLogo, "top_right",2000);
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
	
	@GlobalCommand
	@NotifyChange("comments")
	public void refresh() {
		//System.out.println("test");
		
		Double avgRatingValue = BusinessCtx
				.getInstance().getCommentService().getAverageImageRating(this.image_obj);
		String avgRatingStr = "\u00D8 " + String.valueOf(avgRatingValue);
		if(avgRatingValue == 0){
			avgRatingStr = SpringPropertiesUtil.getProperty("lbl.noCommentAvailable");	
		}
		this.rating_id.setValue(avgRatingStr);
		rating_orange_id.setWidth((avgRatingValue/5)*100 +"%");
		this.setComments(BusinessCtx.getInstance().getCommentService().getAllCommentsByImage(image_obj));
	}
	
	private void addToCart(String id) {
		Session session = Sessions.getCurrent();
		List<String> imageIDsSession = (List<String>) session.getAttribute("imageIDs");
		
		if (imageIDsSession == null) {
			List<String> imageIDs = new ArrayList<String>();
			Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", image_obj.getIdImage().toString()), "info", null, "top_right",2000);
			imageIDs.add(id);
			session.setAttribute("imageIDs", imageIDs);
		}
		else {
			if(!imageIDsSession.contains(id)) {
				Clients.showNotification(ResourcesUtil.loadPropertyWithWildcardValues("notification.loadImage", image_obj.getIdImage().toString()), "info", null, "top_right",2000);
				imageIDsSession.add(id);
			}
			else
				Clients.showNotification("Bereits vorhanden", "info", null, "top_right",2000);
				
			session.setAttribute("imageIDs", imageIDsSession);
		}
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}

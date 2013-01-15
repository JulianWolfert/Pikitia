/*
package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.ext.*;
import org.zkoss.zk.au.*;
import org.zkoss.zk.au.out.*;
import org.zkoss.zul.*;

public class PurchaseDownloadVM extends GenericForwardComposer{

	
	Label lblHeader;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		try {
			super.doAfterCompose(comp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String[] parameter = (String[]) param.get("test");
		
		if (parameter != null)
			lblHeader.setValue( "Congratulations! Your parameters value is " + parameter[0] );
		else
			lblHeader.setValue( "No parameters found. URL should be something like http://yourserver/yoursite/main.zul?parameter=param-value" );
	}
}


*/

package de.htw.fb4.bilderplattform.view.vm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Br;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Tbody;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Th;
import org.zkoss.zhtml.Thead;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.util.Util;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Purchase;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.UserPurchase;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;


/************************************************
 * <p>CartVM</p>
 * <p>
 * @author Julian Wolfert
 * </p>
 * <p>
 * 18.12.2012
 * </p>
 ************************************************/
public class PurchaseDownloadVM extends GenericForwardComposer{

	
	@Wire
	private Div cart_table;
	
	@Wire
	private Label total_price_id;
	
	@Wire
	private Button checkout_button;
	
	//Image list in cart
	private List<Image> cart;	
	private Double total_price;
	
	public Double getTotal_price() {
		return total_price;
	}

	public List<Image> getCart() {
		return cart;
	}

	public void setCart(List<Image> cart) {
		this.cart = cart;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		
		Selectors.wireComponents(view, this, false);		
						
		//generateCartTable();
		
	}
	
	Label lblHeader;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		try {
			super.doAfterCompose(comp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] id = (String[]) param.get("id");
		
		if (id != null){
			//initializeElements(id[0]);
			lblHeader.setValue( "Kein valider Downloadlink, bitte navigieren sie zur Startseite!");
		} else {
			lblHeader.setValue( "Keine valide Seite, bitte gehen sie zur Startseite!" );
		}
		initializeElements(id[0]);
		generateCartTable();
	}
	
	
	@SuppressWarnings("null")
	private void initializeElements(String urlId) {	
		
		// wether its a UserPurchase or GuestPurchase
		Object objectPurchase = BusinessCtx.getInstance().getPurchaseService().get_Guest_User_PurchaseData(urlId);
		List<Purchase_Image> purchaseImages = null;
		List<String> imageIDs = new ArrayList<String>();
		
		if (objectPurchase instanceof UserPurchase){
			// hier muss man die Imageliste erstellen
			UserPurchase userPurchase = (UserPurchase)objectPurchase;
			purchaseImages = BusinessCtx.getInstance().getPurchaseService().getUserPurchasePurchaseImages(userPurchase);
			// nur zu testzwecken
			lblHeader.setValue(((UserPurchase) objectPurchase).getUrlId());
		} else if  (objectPurchase instanceof GuestPurchase){
			GuestPurchase guestPurchase = (GuestPurchase)objectPurchase;
			purchaseImages = BusinessCtx.getInstance().getPurchaseService().getGuestPurchasePurchaseImages(guestPurchase);
			// nur zu testzwecken
			lblHeader.setValue(((GuestPurchase) objectPurchase).getUrlId());			
		} else {
			return; // Fehler ausgeben
		}
		
		//List<de.htw.fb4.bilderplattform.dao.Image> imageIDs = null;
		for (Purchase_Image purchases : purchaseImages) {
			
			imageIDs.add(purchases.getImage().getIdImage().toString());
			
			
			
			//imageIDs.addAll(BusinessCtx.getInstance().getImageService().
			//		.getImageByID(purchases.getImage().getIdImage()).getIdImage().);
		}
				//BusinessCtx.getInstance().getImageService().getAllImages().subList(0, 1);
		
		
		//Session session = Sessions.getCurrent();
		//List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
		
		this.cart = new ArrayList<>();

		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getImageService().getAllImages();
		
		if (imageIDs != null) {
			for (int i=0; i < imageIDs.size(); i++) {
				for (int j=0; j < imgList.size(); j++) {
					
					if (imgList.get(j).getIdImage().toString().equals(imageIDs.get(i).toString())) {
						this.cart.add(imgList.get(j));
						break;
					}
				}
			}
		}
		
		//this.total_price = calculateTotalPrice();
		
	}
	
	
	
	private void initializeElements() {	
		
		
		Session session = Sessions.getCurrent();
		@SuppressWarnings("unchecked")
		List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
		
		this.cart = new ArrayList<>();

		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getImageService().getAllImages();
		
		if (imageIDs != null) {
			for (int i=0; i < imageIDs.size(); i++) {
				for (int j=0; j < imgList.size(); j++) {
					
					if (imgList.get(j).getIdImage().toString().equals(imageIDs.get(i).toString())) {
						this.cart.add(imgList.get(j));
						break;
					}
				}
			}
		}
		
		//this.total_price = calculateTotalPrice();
		
	}

	
	
	private void generateCartTable() {
		
		Table table = new Table();
		table.setSclass("table");
		
		Thead head = new Thead();
		Tr tr_head = new Tr();
		Th th_vorschau = new Th();
		th_vorschau.appendChild(new Label("Vorschau"));
		Th th_title = new Th();
		th_title.appendChild(new Label("Title"));
		Th th_price = new Th();
		th_price.appendChild(new Label("Price"));
		tr_head.appendChild(th_vorschau);
		tr_head.appendChild(th_title);
		tr_head.appendChild(th_price);
		head.appendChild(tr_head);
		//table.appendChild(head);
		
		Tbody table_body = new Tbody();
		
		for (int i=0; i < this.cart.size(); i++) {
			
			Tr tr = new Tr();
			
			Td td_vorschau = new Td();
			A aImage = new A();
			aImage.setId("img_" + this.cart.get(i).getIdImage().toString());
			aImage.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event e) throws Exception {
					
					String img_id = e.getTarget().getId().substring(4);
					final HashMap<String, String> ImageIDMap = new HashMap<String, String>();
					ImageIDMap.put("imageID", img_id);
					ImageIDMap.put("buyButton", "false");
					Executions.createComponents("/gallery_modal.zul", null, ImageIDMap);
					
				}
			});
			
			org.zkoss.zul.Image img_vorschau = new org.zkoss.zul.Image("images/" + this.cart.get(i).getThumb_file());
			aImage.appendChild(img_vorschau);
			td_vorschau.setSclass("vorschau");
			td_vorschau.appendChild(aImage);
			
			Td td_title = new Td();
			Label label_title = new Label(this.cart.get(i).getTitle());
			Label label_desc = new Label(this.cart.get(i).getDescription());
			label_desc.setSclass("desc");
			td_title.setSclass("title");
			td_title.appendChild(label_title);
			td_title.appendChild(new Br());
			td_title.appendChild(label_desc);
			
			Td td_delete = new Td();
			A a_delete = new A("", "images/list/download.jpg");
			a_delete.setId("del_" + this.cart.get(i).getIdImage().toString());
			a_delete.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event e) throws Exception {
					
					
					String img_id = e.getTarget().getId().substring(4);
					Image img = BusinessCtx.getInstance().getImageService().getImageByID(Integer.parseInt(img_id));
					File file = new File(BusinessCtx.getInstance().getImageService().getImagePath(img.getFile()));
					//System.out.print(file);
					String mimeType = file.toURL().openConnection().getContentType();
										
					Filedownload.save(file, mimeType);
					
				/*	
					Session session = Sessions.getCurrent();
					List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
					imageIDs.remove(img_id);
					session.setAttribute("imageIDs", imageIDs);
					initializeElements();
					cart_table.getChildren().clear();
					generateCartTable();
				*/
				}
			});
			td_delete.appendChild(a_delete);
			
			
				
			Td td_price = new Td();
			td_price.setSclass("price");
			
			Div div_price = new Div();
			div_price.setSclass("div_price");
			div_price.appendChild(new Label("\u20AC " + Util.formatDouble(this.cart.get(i).getPrice())));
			
			td_price.appendChild(div_price);
			
			tr.appendChild(td_vorschau);
			tr.appendChild(td_title);
			tr.appendChild(td_delete);
			//tr.appendChild(td_price);
			
			table_body.appendChild(tr);
			
		}
		
		
		table.appendChild(table_body);
		this.cart_table.appendChild(table);
		
		//total_price_id.setValue("Summe: " + "\u20AC " + Util.formatDouble(this.total_price));	
	}
	
/*
	@Command
	@NotifyChange("cart")
	public void delete() {
		if(!(this.getCart().isEmpty())){
			cart.clear();
			total_price = 0.00;
			total_price_id.setValue("Summe: " + "\u20AC " + Util.formatDouble(this.total_price));	
			cart_table.getChildren().clear();
			Session session = Sessions.getCurrent();
			List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
			imageIDs.clear();
			session.setAttribute("imageIDs", imageIDs);
		} else {
			Messagebox.show(SpringPropertiesUtil.getProperty("err.emptyCart"), "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Command
	public void checkout(){
		if(!(this.getCart().isEmpty())){	
			final HashMap<String, Object> cartMap = new HashMap<String, Object>();
			cartMap.put("cartImages", this.getCart());
			cartMap.put("totalCartPrice", this.getTotal_price());
			Executions.createComponents("/purchaseInput_modal.zul", null, cartMap);
		} else {
			Messagebox.show(SpringPropertiesUtil.getProperty("err.emptyCart"), "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	*/
	
}
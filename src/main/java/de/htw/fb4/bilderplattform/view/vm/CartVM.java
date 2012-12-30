package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
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
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;


/************************************************
 * <p>CartVM</p>
 * <p>
 * @author Julian Wolfert
 * </p>
 * <p>
 * 18.12.2012
 * </p>
 ************************************************/
public class CartVM {

	
	@Wire
	private Div cart_table;
	
	@Wire
	private Label total_price_id;
	
	@Wire
	private Button checkout_button;
	
	//Image list in cart
	private List<Image> cart;
	
	private Double total_price;
		
	public List<Image> getCart() {
		return cart;
	}

	public void setCart(List<Image> cart) {
		this.cart = cart;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		
		Selectors.wireComponents(view, this, false);
		
		initializeElements();		
		
		generateCartTable();
		
	}
	
	
	private void initializeElements() {
		Session session = Sessions.getCurrent();
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
		
		this.total_price = calculateTotalPrice();
		
	}

	private Double calculateTotalPrice() {
		
		Double tmp_total_price = 0.00;
		for (int i=0; i < this.cart.size(); i++)
		{
			tmp_total_price = tmp_total_price + (this.cart.get(i).getPrice());
		}
		
		return tmp_total_price;
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
			org.zkoss.zul.Image img_vorschau = new org.zkoss.zul.Image("images/" + this.cart.get(i).getFile());
			img_vorschau.setStyle("width:300px; height:170px;");
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
			A a_delete = new A("", "images/list/j_delete.png");
			a_delete.setId("del_" + this.cart.get(i).getIdImage().toString());
			a_delete.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event e) throws Exception {
					
					String img_id = e.getTarget().getId().substring(4);
					Session session = Sessions.getCurrent();
					List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
					imageIDs.remove(img_id);
					session.setAttribute("imageIDs", imageIDs);
					initializeElements();
					cart_table.getChildren().clear();
					generateCartTable();
					
				}
			});
			td_delete.appendChild(a_delete);
			
			
				
			Td td_price = new Td();
			td_price.setSclass("price");
			
			Div div_price = new Div();
			div_price.setSclass("div_price");
			div_price.appendChild(new Label("\u20AC " + this.cart.get(i).getPrice().toString().replace(".",",")));
			
			td_price.appendChild(div_price);
			
			tr.appendChild(td_vorschau);
			tr.appendChild(td_title);
			tr.appendChild(td_delete);
			tr.appendChild(td_price);
			
			table_body.appendChild(tr);
			
		}
		
		
		table.appendChild(table_body);
		this.cart_table.appendChild(table);
		
		total_price_id.setValue("Summe: " + "\u20AC " + this.total_price.toString().replace(".",","));	
	}

	@Command
	@NotifyChange("cart")
	public void delete() {
		cart_table.getChildren().clear();
		Session session = Sessions.getCurrent();
		List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
		imageIDs.clear();
		session.setAttribute("imageIDs", imageIDs);	
	}
	
	
}
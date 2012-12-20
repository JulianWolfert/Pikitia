package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
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

	//Image list in cart
	private List<Image> cart;
		
	public List<Image> getCart() {
		return cart;
	}

	public void setCart(List<Image> cart) {
		this.cart = cart;
	}

	@Init
	public void init()
	{
		Session session = Sessions.getCurrent();
		List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
		
		this.cart = new ArrayList<>();
		
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getIImageService().getAllImages();
		
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
		
	}
	
	@Command
	@NotifyChange("cart")
	public void delete() {
		cart.clear();
		Session session = Sessions.getCurrent();
		List<String> imageIDs = (List<String>) session.getAttribute("imageIDs");
		imageIDs.clear();
		session.setAttribute("imageIDs", imageIDs);	
	}
	
	
}
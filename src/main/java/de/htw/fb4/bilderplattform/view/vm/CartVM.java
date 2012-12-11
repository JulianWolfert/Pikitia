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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;

public class CartVM {

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
		
		cart = new ArrayList<>();
		
		List<de.htw.fb4.bilderplattform.dao.Image> imgList = BusinessCtx
				.getInstance().getIImageService().getAllImages();
		
		if (imageIDs != null) {
			for (int i=0; i < imageIDs.size(); i++) {
				for (int j=0; j < imgList.size(); j++) {
					if (imgList.get(j).getIdImage().toString() == imageIDs.get(i));
					cart.add(imgList.get(j));
					break;
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
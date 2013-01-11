package de.htw.fb4.bilderplattform.view.vm;

import java.text.DecimalFormat;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Peter Horn & Wojciech Konitzer
 * 
 */
public class AddImageVM {

	@Wire
	private Image uploadImg;

	private de.htw.fb4.bilderplattform.dao.Image image;

	public AddImageVM() {
		this.image = new de.htw.fb4.bilderplattform.dao.Image();
		// default values
		this.image.setPrice(0.0);
		this.image.setTitle("");
		this.image.setDescription("");
	}

	public de.htw.fb4.bilderplattform.dao.Image getImage() {
		return image;
	}

	public void setImage(de.htw.fb4.bilderplattform.dao.Image image) {
		DecimalFormat f = new DecimalFormat("#0.00"); 
		double price = this.image.getPrice();
		this.image.setPrice(new Double(f.format(price)));

		this.image = image;
		
//		System.out.println("formatierter Preis = " + this.image.getPrice());
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	// TODO: Image size checking!!!
	@Command
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		UploadEvent evt = null;
		if (ctx.getTriggerEvent() instanceof UploadEvent) {
			evt = (UploadEvent) ctx.getTriggerEvent();
			if (evt.getMedia() instanceof org.zkoss.image.Image) {
				uploadImg.setContent((org.zkoss.image.Image) evt.getMedia());
			}
		}
	}
	
	@Command
	public void cancel() {
		Executions.sendRedirect("/index.zul");
	}

	public String getUploadImageLabel() {
		return SpringPropertiesUtil.getProperty("lbl.uploadImage");
	}

	public String getNextPageLabel() {
		return SpringPropertiesUtil.getProperty("lbl.nextPage");
	}

	public String getCreateCancelLabel() {
		return SpringPropertiesUtil.getProperty("lbl.cancelOffer");
	}
	
	@Command
	public void loadOfferSummary() {
		if (this.image.getTitle() != null && this.image.getDescription() != null
				&& this.image.getPrice() != null && uploadImg.getContent() != null) {

			Sessions.getCurrent().setAttribute("image", this.image);
			Sessions.getCurrent().setAttribute("uploadImg", this.uploadImg);
						
			Executions.getCurrent().sendRedirect("/user/addImageSummary.zul");
		}
	}

}
package de.htw.fb4.bilderplattform.view.vm;

import java.io.File;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zul.Image;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;

import de.htw.fb4.bilderplattform.business.BusinessCtx;

public class AddImageVM {
	// TODO:
	private final String testFolder = "/home/eagle/Desktop/images";

	@Wire
	private Image uploadImg;

	private de.htw.fb4.bilderplattform.dao.Image image;

	public AddImageVM() {
		this.image = new de.htw.fb4.bilderplattform.dao.Image();
		// default values
		this.image.setPrice(0.0);
		this.image.setTitle("Titel");
		this.image.setDescription("Beschreibung");
	}

	public de.htw.fb4.bilderplattform.dao.Image getImage() {
		return image;
	}

	public void setImage(de.htw.fb4.bilderplattform.dao.Image image) {
		this.image = image;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

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
	public void save() {
		if (uploadImg.getContent() == null) {
			Messagebox.show("Es wurde kein Bild hochgeladen.");
			return;
		}
		// TODO: user ermitteln!!!
		this.image.setUser(BusinessCtx.getInstance().getUserService()
				.getUserByName("peter"));
		File f = new File(this.testFolder, this.image.getUser().getUsername()
				+ "_" + (new Date()).getTime() + "_"
				+ uploadImg.getContent().getName());
		BusinessCtx
				.getInstance()
				.getIImageService()
				.saveOrUpdateImage(this.image, f,
						this.uploadImg.getContent().getStreamData());
	}
}
package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;
/************************************************
 * <p>VM to the editImage.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 03.01.2013
 * </p>
 ************************************************/
public class EditImageVM {
	private Image image;
	
	@Wire("#modalEditImage")
	private Window win;
	
	public Image getImage() {
		return image;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("image") Image image) {
		this.image = image;
	}
	
	@Command
	public void updateImage(){
		// user remains the same
		BusinessCtx.getInstance().getImageService().saveOrUpdateImage(image, image.getUser());
	}
	
	@Command
	public void closeThis() {
		this.win.detach();
	}
}

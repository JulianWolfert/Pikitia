package de.htw.fb4.bilderplattform.view.vm;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.IImageService;
import de.htw.fb4.bilderplattform.dao.Image;

/************************************************
 * <p>
 * VM to the commentForm.zul
 * </p>
 * <p>
 * 
 * @author Wojciech Konitzer
 *         </p>
 *         <p>
 *         23.12.2012
 *         </p>
 ************************************************/
public class CommentFormVM {

	private static final Logger logger = Logger.getLogger(ContactFormVM.class);

	@Wire("#commentForm")
	private Window win;

	private IImageService imageService = BusinessCtx.getInstance()
			.getImageService();

	private Integer stars;
	private String text;

	// second
	@Init
	public void init(@ExecutionArgParam("imageID") String imageID) {
		logger.debug("imageID: " + imageID + " was sent to commentForm.zul");
		Image img = imageService.getImageByID(Integer.parseInt(imageID));
	}

	// first
	public CommentFormVM() {

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	private void resetForm() {
		stars = 0;
		text = "";
	}

	@Command
	public void submit() {
		
	}

	@Command
	public void closeThis() {
		win.detach();
	}

}

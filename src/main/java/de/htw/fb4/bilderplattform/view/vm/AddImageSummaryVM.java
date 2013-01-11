package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Image;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Wojciech Konitzer
 * 
 */
public class AddImageSummaryVM {

	private de.htw.fb4.bilderplattform.dao.Image image;
	private Image uploadImg;
	private String imgPrice;

	public AddImageSummaryVM() {
		if (Sessions.getCurrent().getAttribute("image") != null) {
			this.image = (de.htw.fb4.bilderplattform.dao.Image) Sessions
					.getCurrent().getAttribute("image");
		}
		if (Sessions.getCurrent().getAttribute("uploadImg") != null) {
			this.uploadImg = (Image) Sessions.getCurrent().getAttribute(
					"uploadImg");
		}
	}

	public de.htw.fb4.bilderplattform.dao.Image getImage() {
		return image;
	}

	public Image getUploadImg() {
		return uploadImg;
	}
	
	public String getImgPrice() {
		return de.htw.fb4.bilderplattform.business.util.Util.formatDouble(this.image.getPrice());
	}

	public String getCreateOfferLabel() {
		return SpringPropertiesUtil.getProperty("lbl.createOffer");
	}
	
	public String getCreateCancelLabel() {
		return SpringPropertiesUtil.getProperty("lbl.cancelOffer");
	}
	
	@Command
	public void cancel() {
		Executions.sendRedirect("/index.zul");
	}

	@Command
	public void save() {
		if (uploadImg.getContent() == null) {
			Messagebox.show(SpringPropertiesUtil
					.getProperty("err.chooseImageToUpload"));
			return;
		}
		BusinessCtx
				.getInstance()
				.getImageService()
				.saveOrUpdateImage(
						this.image,
						this.uploadImg,
						BusinessCtx.getInstance().getUserService()
								.getCurrentlyLoggedInUser());

		Sessions.getCurrent().removeAttribute("image");
		Sessions.getCurrent().removeAttribute("uploadImg");

		Executions.sendRedirect("/index.zul");
	}
}

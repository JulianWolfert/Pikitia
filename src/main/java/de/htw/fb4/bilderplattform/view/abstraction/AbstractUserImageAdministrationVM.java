package de.htw.fb4.bilderplattform.view.abstraction;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;
/************************************************
 * <p>abstract class to image administration view models</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 05.01.2013
 * </p>
 ************************************************/
public class AbstractUserImageAdministrationVM extends AbstractRedirection {

	private ListModelList<Image> imageList = null;

	public void init() {
		int idUser = Integer.parseInt(Executions.getCurrent().getParameter(
				"idUser"));

		String userName = BusinessCtx.getInstance().getUserService()
				.getUserByID(idUser).getUsername();

		this.imageList = new ListModelList<Image>(BusinessCtx.getInstance()
				.getImageService().getImagesByUsername(userName));

	}

	public ListModelList<Image> getImageList() {
		return imageList;
	}
	
	public void setImageList(ListModelList<Image> imageList) {
		this.imageList = imageList;
	}

	@Command
	public void deleteImage(@BindingParam("image") final Image image) {
		Messagebox.show("Bild \"" + image.getTitle() + "\" l\u00F6schen?",
				"Bild l\u00F6schen", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (((Integer) event.getData()).intValue() == Messagebox.YES) {
							BusinessCtx.getInstance().getImageService()
									.deleteImage(image);
							AbstractUserImageAdministrationVM.this.imageList
									.remove(image);
							return;
						}
					}
				});
	}
	
	
}

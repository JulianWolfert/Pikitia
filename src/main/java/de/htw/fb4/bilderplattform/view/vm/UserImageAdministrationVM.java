package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Image;

/************************************************
 * <p>VM to the userImageList.zul</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 26.12.2012
 * </p>
 ************************************************/
public class UserImageAdministrationVM {

	private ListModelList<Image> imageList = null;
	
	@Init
	public void init() {
		int idUser = Integer.parseInt(Executions.getCurrent().getParameter("idUser"));
		
		String userName = BusinessCtx.getInstance().getUserService().getUserByID(idUser).getUsername();
		
		this.imageList = new ListModelList<Image>(BusinessCtx
				.getInstance().getImageService().getImagesByUsername(userName));
		
		// alternative solution - not necessary anymore, due to getImagesByUsername is working now :-)
//		List<Image> userImgs = new ArrayList<Image>();
//		List<Image> allImgs = BusinessCtx.getInstance().getImageService().getAllImages();
//		for(Image img : allImgs) {
//			if(img.getUser().getUsername().equals(userName))
//				userImgs.add(img);
//		}
//		this.imageList = new ListModelList<Image>(userImgs);
		// alternative solution end
	}

	public ListModelList<Image> getImageList() {
		return imageList;
	}
	
	@Command
	public void deleteImage(@BindingParam("image") final Image image) {
		Messagebox.show("Bild \"" + image.getTitle() + "\" löschen?",
				"Bild löschen", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (((Integer) event.getData()).intValue() == Messagebox.YES) {
							BusinessCtx.getInstance().getImageService().deleteImage(image);
							UserImageAdministrationVM.this.imageList.remove(image);
							return;
						}
					}
				});
	}

	@Command
	public void editImage(@BindingParam("image") final Image image) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("image", image);
		Executions.createComponents("/admin/editImage.zul", null, sessionMap);
	}
	
	@Command
	public void imageComments(@BindingParam("image") final Image image) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idImage", image.getIdImage());
		//TODO: realize edit comments
//		Executions.createComponents("/admin/comments.zul", null, sessionMap);
	}
	
	@Command
	public void redirectToUserList() {
		Executions.getCurrent().sendRedirect("/admin/userList.zul");
	}
	
	@Command
	public void redirectToStart() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}
	
}

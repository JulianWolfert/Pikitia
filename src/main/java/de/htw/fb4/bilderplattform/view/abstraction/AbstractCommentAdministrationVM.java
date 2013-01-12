package de.htw.fb4.bilderplattform.view.abstraction;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.Image;

/************************************************
 * <p>abstract class to comment administration view models</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 05.01.2012
 * </p>
 ************************************************/
public class AbstractCommentAdministrationVM extends AbstractRedirection {

	private ListModelList<Comment> commentList = null;
	private int idUser;

	public void init(int idImage, int idUser) {
		this.idUser = idUser;
		Image image = BusinessCtx.getInstance().getImageService().getImageByID(idImage);
		this.commentList = new ListModelList<Comment>(
				BusinessCtx.getInstance().getCommentService().getAllCommentsByImage(image));
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public ListModelList<Comment> getCommentList() {
		return commentList;
	}
	
	
	@Command
	public void deleteComment(@BindingParam("comment") final Comment comment) {
		Messagebox.show("Wollen Sie den Kommentar wirklich löschen?",
				"Kommentar löschen", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (((Integer) event.getData()).intValue() == Messagebox.YES) {
							BusinessCtx.getInstance().getCommentService().deleteComment(comment.getIdComment());
							AbstractCommentAdministrationVM.this.commentList.remove(comment);
							return;
						}
					}
				});
	}
	
}

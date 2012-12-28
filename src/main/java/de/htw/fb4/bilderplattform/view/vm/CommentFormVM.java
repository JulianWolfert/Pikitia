package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.rating.Rating;
import org.zkoss.rating.event.RatingEvent;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.ICommentService;
import de.htw.fb4.bilderplattform.business.IImageService;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.dao.Comment;
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

	private static final Logger logger = Logger.getLogger(CommentFormVM.class);

	@Wire("#commentForm")
	private Window win;

	private ICommentService commentService = BusinessCtx.getInstance().getCommentService();

	private static List<Integer> stars = new ArrayList<Integer>();
	
	static{
		stars.add(1);
		stars.add(2);
		stars.add(3);
		stars.add(4);
		stars.add(5);
    }
	
	private Integer selectedStarValue;
	private String text;
	private Integer idImage;

	public CommentFormVM() {

	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,  @ExecutionArgParam("imageID") String imageID) {
		Selectors.wireComponents(view, this, false);
		logger.debug("imageID: " + imageID + " was sent to commentForm.zul");
		this.idImage = Integer.parseInt(imageID);
		this.selectedStarValue = stars.get(0);
	}

	public String getText() {
		return text;
	}

	public List<Integer> getStars() {
		return new ArrayList<Integer>(stars);
	}

	public Integer getSelectedStarValue() {
		return selectedStarValue;
	}

	public void setSelectedStarValue(Integer selectedStarValue) {
		this.selectedStarValue = selectedStarValue;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Command
	public void submit() {
		Comment comment = new Comment(this.getSelectedStarValue(), this.getText(), this.idImage);
		commentService.saveOrUpdateComment(comment);
		closeThis();
	}

	@Command
	public void closeThis() {
		win.detach();
	}

}

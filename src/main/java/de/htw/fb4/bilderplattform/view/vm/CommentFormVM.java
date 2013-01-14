package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zhtml.Li;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.business.ICommentService;
import de.htw.fb4.bilderplattform.business.IUserService;
import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

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

	private ICommentService commentService = BusinessCtx.getInstance()
			.getCommentService();
	private IUserService userService = BusinessCtx.getInstance()
			.getUserService();

	@Wire
	private Li starCssStyle;
	
	private Integer currentStarValue;
	private String text;
	private Image image;
	private String username;

	public CommentFormVM() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("image") Image image) {
		Selectors.wireComponents(view, this, false);
		logger.debug("imageID: " + image.getIdImage() + " was sent to commentForm.zul");
		this.image = image;
		this.currentStarValue = 1;
	}

	public boolean isAUserAuthenticated() {
		return userService.isAUserAuthenticated();
	}

	public String getText() {
		return text;
	}

	public String getUsername() {
		if (userService.isAUserAuthenticated()) {
			return userService.getCurrentlyLoggedInUser().getUsername();
		}
		return username;
	}

	public Integer getCurrentStarValue() {
//		System.out.println("getStarValue" + currentStarValue);
		return currentStarValue;
	}

	public void setCurrentStarValue(Integer currentStarValue) {
//		System.out.println("setCurrentStarValue" + currentStarValue);
		this.currentStarValue = currentStarValue;
	}

	public void setText(String text) {
		if(text != null){
			this.text = text.trim();
		}else{
			this.text = text;
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Command
	public void submit() {
		String usrname = this.username.trim();
		if (!userService.isAUserAuthenticated()) {
			usrname = this.username + " "
					+ SpringPropertiesUtil.getProperty("lbl.anonymousUser");
		}
		Comment comment = new Comment(this.getCurrentStarValue(), this.getText(), this.image, usrname);
		commentService.saveOrUpdateComment(comment);
		closeThis();
	}

	@Command
	public void closeThis() {
		Map args = new HashMap();
		BindUtils.postGlobalCommand(null, null, "refresh", args);
		win.detach();
	}

	@Command
	public void setStarValue(@BindingParam("currentStarValue") final Integer currentStarValue, @BindingParam("cssStyle") final String cssStyle) {
		this.starCssStyle.setStyle(cssStyle);
		this.setCurrentStarValue(currentStarValue);
//		System.out.println(" currentStarValue: " + currentStarValue);
	}

}

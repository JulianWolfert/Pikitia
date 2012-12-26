package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.User;

/************************************************
 * <p>Defines comment and rating functionality</p>
 * <p>
 * @author Wojciech Konitzer
 * </p>
 * <p>
 * 26.12.2012
 * </p>
 ************************************************/
public interface ICommentService {
	
	public void saveOrUpdateComment(Comment comment, int imageID, int userID);
	
	public double getAverageImageRating(int idImage);
	
}

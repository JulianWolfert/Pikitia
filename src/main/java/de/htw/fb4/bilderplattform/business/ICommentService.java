package de.htw.fb4.bilderplattform.business;

import de.htw.fb4.bilderplattform.dao.Comment;

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

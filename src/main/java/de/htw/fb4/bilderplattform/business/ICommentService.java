package de.htw.fb4.bilderplattform.business;

import java.util.List;

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
	
	public void saveOrUpdateComment(Comment comment);
	
	public double getAverageImageRating(int idImage);
	
	public List<Comment> getAllCommentsByImageID(int idImage);
	
	public void deleteComment(int idComment);
	
	public void deleteComment(Comment comment);
	
}

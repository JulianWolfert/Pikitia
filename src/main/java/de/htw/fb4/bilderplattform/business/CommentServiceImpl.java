package de.htw.fb4.bilderplattform.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.CommentDAOImpl;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.ImageDAOImpl;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/**
 * 
 * @author Wojciech Konitzer
 * 
 * 26.12.2012
 *
 */
public class CommentServiceImpl implements ICommentService {

	//TODO: Using userID
	@Override
	public void saveOrUpdateComment(Comment comment, int imageID, int userID) {
		if (comment == null) {
			return;
		}
		comment.setImage_idImage(imageID);	
		CommentDAOImpl commentDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("commentDao", CommentDAOImpl.class);
		commentDAO.saveComment(comment);	
	}

	@Override
	public double getAverageImageRating(int idImage) {
		CommentDAOImpl commentDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("commentDao", CommentDAOImpl.class);
		
		List<Comment> comments = commentDAO.getAllRatingsByImageID(idImage);
		
		double averageRating = 0f;
		int sumAllRatings = 0;
		if(comments.size() > 0) {
			for (Comment comment : comments) {
				sumAllRatings += comment.getStars();
			}
			averageRating = sumAllRatings * 1.0d / (5*comments.size());
			averageRating = Math.rint(averageRating*100)/100;
		}
		return averageRating;
	}

}

package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.CommentDAOImpl;
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
	public void saveOrUpdateComment(Comment comment) {
		if (comment == null) {
			return;
		}
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
			averageRating = sumAllRatings * 1.0d / comments.size();
			averageRating = Math.rint(averageRating*100)/100;
		}
		return averageRating;
	}

}

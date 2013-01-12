package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Comment;
import de.htw.fb4.bilderplattform.dao.CommentDAOImpl;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/**
 * 
 * @author Wojciech Konitzer
 * 
 * 26.12.2012
 *
 */
public class CommentServiceImpl implements ICommentService {

	@Override
	public void saveOrUpdateComment(Comment comment) {
		if (comment == null) {
			return;
		}
		CommentDAOImpl commentDAO = getDAO();
		commentDAO.saveComment(comment);	
	}

	@Override
	public double getAverageImageRating(Image image) {
		CommentDAOImpl commentDAO = getDAO();
		
		List<Comment> comments = commentDAO.getAllCommentsByImage(image);
		
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

	@Override
	public List<Comment> getAllCommentsByImage(Image image) {
		CommentDAOImpl commentDAO = getDAO();
		List<Comment> comments = commentDAO.getAllCommentsByImage(image);
		
		return comments;
	}

	@Override
	public void deleteComment(int idComment) {
		deleteComment(getDAO().getCommentyID(idComment));
	}

	@Override
	public void deleteComment(Comment comment) {
		getDAO().deleteComment(comment);
	}
	
	// jro: to avoid redundancy
	private CommentDAOImpl getDAO() {
		CommentDAOImpl commentDAO = ApplicationContextProvider
				.getApplicationContext()
				.getBean("commentDao", CommentDAOImpl.class);
		return commentDAO;
	}

}

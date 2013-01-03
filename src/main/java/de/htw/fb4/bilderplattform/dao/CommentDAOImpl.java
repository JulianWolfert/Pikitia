package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/************************************************
 * <p>
 * The Comment DAO
 * </p>
 * 
 * <p>
 * 
 * @author Wojciech Konitzer
 *         </p>
 *         <p>
 *         26.12.2012
 *         </p>
 ************************************************/
public class CommentDAOImpl extends AbstractDAO {
	
	static final Logger logger = Logger.getLogger(ImageDAOImpl.class);
	
	@Transactional
	public void saveComment(Comment comment) {
		sessionFactory.getCurrentSession().saveOrUpdate(comment);
	}
	
	@Transactional
	public List<Comment> getAllCommentsByImageID(int idImage) {
		List<Comment> comments = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Comment.class).add(
					Restrictions.eq("Image_idImage", idImage));
			comments = criteria.list();
		} catch (DataAccessException dae) {
			logger.error("getAllRatingByImageID throws exception: ", dae);
			session.getTransaction().rollback();
		}
		return comments;
	}

}

package de.htw.fb4.bilderplattform.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/************************************************
 * <p>
 * The Image DAO
 * </p>
 * 
 * <p>
 * 
 * @author Wojciech Konitzer
 *         </p>
 *         <p>
 *         17.11.2012
 *         </p>
 ************************************************/
public class ImageDAOImpl extends AbstractDAO {

	static final Logger logger = Logger.getLogger(ImageDAOImpl.class);

	@Transactional
	public void saveImage(Image image) {
		sessionFactory.getCurrentSession().saveOrUpdate(image);
	}

	@Transactional
	public Image getImageByID(int idImage) {
		Image image = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Image.class).add(
					Restrictions.eq("idImage", idImage));
			image = (Image) criteria.uniqueResult();
		} catch (DataAccessException dae) {
			logger.error("getImageByID throws exception: ", dae);
			session.getTransaction().rollback();
		}
		return image;
	}
	
//	@Transactional
//	public Image getImageByID(int idImage) {
//		Query query = sessionFactory.getCurrentSession().createQuery(
//				"SELECT i FROM Image i where i.idImage = " + idImage);
//		return (Image) query.uniqueResult();
//	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> getAllImages() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Image");
		return (List<Image>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> getAllImagesByUsername(String username) {
		List<Image> images = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			StringBuilder hqlBuilder = new StringBuilder(); 
        	hqlBuilder.append("SELECT DISTINCT image ");
        	hqlBuilder.append("FROM Image image ");
        	hqlBuilder.append("INNER JOIN image.user as user "); 
        	hqlBuilder.append("WHERE user.username = '" + username + "'");   
			
        	Query query = session.createQuery(hqlBuilder.toString());
			
			images = query.list();
		} catch (DataAccessException dae) {
			logger.error("getAllImagesByUsername throws exception: ", dae);
			session.getTransaction().rollback();
		}
		return images;
	}

	@Transactional
	public void deleteImage(Image image) {
//		ICommentService commentServ = BusinessCtx.getInstance().getCommentService();
//		List<Comment> commentsToImage = commentServ.getAllCommentsByImageID(image.getIdImage());
//		try {
//			for(Comment c : commentsToImage) {
//				commentServ.deleteComment(c);
//			}
//			sessionFactory.getCurrentSession().delete(image);
//		}
//		catch (Exception e) {
//			logger.error("delete image failed due to dependencies.");
//			sessionFactory.getCurrentSession().getTransaction().rollback();
//		}
		
		// if entities are defined correctly thats all we have to do to delete comments as well
		sessionFactory.getCurrentSession().delete(image);
	}

	@Transactional
	public Integer getLastInsertedImageID() {
		List<Integer> results = sessionFactory.getCurrentSession()
				.createCriteria(Image.class)
				.setProjection(Projections.max("idImage")).list();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
	
	@Transactional
	public List<Image> getBest(int count){
		List<Image> img_list = new ArrayList<Image>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Comment.class)
					.setProjection( Projections.projectionList()
							.add( Projections.groupProperty("image"))
							.add( Projections.alias(Projections.avg("stars"), "stars_avg")))
					.addOrder(Order.desc("stars_avg"));
			List list= criteria.list();
			for(int i=0; i<count && i<list.size(); i++){
				Object[] row=(Object[])list.get(i);
				Image img= (Image)row[0];
				img_list.add(img);
			}
		} catch (DataAccessException dae) {
			logger.error("getBest throws exception: ", dae);
			session.getTransaction().rollback();
		}
		return img_list;
	}
}

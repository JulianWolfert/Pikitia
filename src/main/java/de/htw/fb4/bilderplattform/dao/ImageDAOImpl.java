package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;

import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

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
		Image image = (Image) sessionFactory.getCurrentSession().load(
				Image.class, idImage);
		return image;
	}

	@Transactional
	public Image getImageByUsername(String username) {
		Image image = (Image) sessionFactory.getCurrentSession().load(
				Image.class, username);
		return image;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> getAllImages() {
		//TODO: FUNZT NICHT!!!!!!!
		// List<Image> images = null;
		// Session session = sessionFactory.getCurrentSession();
		//
		// Transaction transaction = session.beginTransaction();
		// try {
		// images = session.createQuery("from Image").list();
		// transaction.commit();
		// } catch (DataAccessException dae) {
		// logger.error("getAllImages throws exception: ", dae);
		// transaction.rollback();
		// }
		// return images;
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT u FROM Image u");
		return (List<Image>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> getAllImagesByUsername(String username) {
		List<Image> images = null;
		Session session = sessionFactory.getCurrentSession();

		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Image.class).add(
					Restrictions.eq("username", username));
			images = criteria.list();
			transaction.commit();
		} catch (DataAccessException dae) {
			logger.error("getAllImagesByUsername throws exception: ", dae);
			transaction.rollback();
		}
		return images;
	}

	@Transactional
	public void deleteImage(int idImage) {
		Image image = getImageByID(idImage);
		sessionFactory.getCurrentSession().saveOrUpdate(image);
	}

	@Transactional
	public void deleteImage(Image image) {
		this.saveImage(image);
	}
}

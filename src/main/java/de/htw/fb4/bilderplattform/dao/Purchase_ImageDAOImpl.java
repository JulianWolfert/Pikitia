package de.htw.fb4.bilderplattform.dao;

import org.springframework.transaction.annotation.Transactional;

public class Purchase_ImageDAOImpl extends AbstractDAO {

	@Transactional
	public void savePurchase_Image(Purchase_Image purchaseImage) {
		sessionFactory.getCurrentSession().saveOrUpdate(purchaseImage);
	}

}

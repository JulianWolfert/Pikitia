package de.htw.fb4.bilderplattform.business;

import java.util.List;
import de.htw.fb4.bilderplattform.dao.PurchaseDAOImpl;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

/**
 * 
 * @author Peter Horn
 *
 */
public class PurchaseServiceImpl implements IPurchaseService {

	@Override
	public List<Purchase_Image> getUserPurchase(int idUser) {
		PurchaseDAOImpl purchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("purchaseDao", PurchaseDAOImpl.class);

		return purchaseDAO.getAllPurchasesByUserId(idUser);
	}
}

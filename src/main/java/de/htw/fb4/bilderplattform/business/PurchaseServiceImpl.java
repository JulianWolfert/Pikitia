package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Purchase;
import de.htw.fb4.bilderplattform.dao.PurchaseDAOImpl;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.User;
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
				.getApplicationContext().getBean("purchaseDao",
						PurchaseDAOImpl.class);

		return purchaseDAO.getAllPurchasesByUserId(idUser);
	}

	@Override
	public Object get_Guest_User_PurchaseData(int idPurchase) {
		Object returnedValue = null;
		PurchaseDAOImpl purchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("purchaseDao",
						PurchaseDAOImpl.class);
		Purchase purchase = purchaseDAO.getPurchaseById(idPurchase);

		if (purchase.getGuestPurchase_idGuestPurchase() != null) {
			returnedValue = purchaseDAO.getGuestPurchaseById(purchase
					.getGuestPurchase_idGuestPurchase());
		} else if (purchase.getUserPurchase_idUserPurchase() != null) {
			returnedValue = purchaseDAO.getUserByIdUserPurchase(purchase
					.getUserPurchase_idUserPurchase());
		}

		return returnedValue;
	}

	@Override
	public Bankaccount getBankaccountByIdUser(int idUser) {
		PurchaseDAOImpl purchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("purchaseDao",
						PurchaseDAOImpl.class);
		return purchaseDAO.getBankaccountByIdUser(idUser);
	}
	
	@Override
	public void saveUserPurchase (List<Image> imageList){
		
		// Purchase, UserPurchase, Purchase_Image speichern
		
		
	}
	
	@Override
	public void saveGuestPurchase(List<Image> imageList, GuestPurchase guestPurchase){
	
		// Purchase, Purchase_Image, GuestPurchase speichern
	}
}

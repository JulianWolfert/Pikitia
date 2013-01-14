package de.htw.fb4.bilderplattform.business;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.GuestPurchaseDAOImpl;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Purchase;
import de.htw.fb4.bilderplattform.dao.PurchaseDAOImpl;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserPurchase;
import de.htw.fb4.bilderplattform.dao.UserPurchaseDAOImpl;
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
	public void saveUserPurchase (List<Image> imageList, UserPurchase userPurchase){
		// Purchase, UserPurchase, Purchase_Image speichern
		UserPurchaseDAOImpl userPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userPurchaseDao",
						UserPurchaseDAOImpl.class);
		
		userPurchaseDAO.saveUserPurchase(userPurchase);
		Integer userPurchaseId = userPurchaseDAO.getLastInsertedUserPurchaseID();
		
		Purchase purchase = new Purchase();
		Date date = new Date();
		purchase.setDate(date);
		purchase.setOrder_nr(String.valueOf(date.getTime()));
		purchase.setUserPurchase_idUserPurchase(userPurchaseId);
		
		PurchaseDAOImpl purchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("purchaseDao",
						PurchaseDAOImpl.class);
		purchaseDAO.savePurchase(purchase);
	}
	
	@Override
	public void saveGuestPurchase(List<Image> imageList, GuestPurchase guestPurchase){
		// Purchase, Purchase_Image, GuestPurchase speichern
		GuestPurchaseDAOImpl guestPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("guestPurchaseDao",
						GuestPurchaseDAOImpl.class);
		
		guestPurchaseDAO.saveGuestPurchase(guestPurchase);
		Integer guestPurchaseId = guestPurchaseDAO.getLastInsertedGuestPurchaseID();
		
		Purchase purchase = new Purchase();
		Date date = new Date();
		purchase.setDate(date);
		purchase.setOrder_nr(String.valueOf(date.getTime()));
		purchase.setGuestPurchase_idGuestPurchase(guestPurchaseId);
		
		PurchaseDAOImpl purchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("purchaseDao",
						PurchaseDAOImpl.class);
		purchaseDAO.savePurchase(purchase);
	}
	
	
}

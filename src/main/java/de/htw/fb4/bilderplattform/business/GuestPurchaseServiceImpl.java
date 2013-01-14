package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.GuestPurchaseDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

class GuestPurchaseServiceImpl implements IGuestPurchaseService {

	@Override
	public GuestPurchase getGuestPurchaseByID(int guestPurchaseID) {
		GuestPurchaseDAOImpl guestPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("guestPurchaseDao", GuestPurchaseDAOImpl.class);
		return guestPurchaseDAO.getGuestPurchaseByID(guestPurchaseID);
	}

	@Override
	public void saveGuestPurchase(GuestPurchase guestPurchase) {
		if (guestPurchase == null) {
			return;
		}
		GuestPurchaseDAOImpl guestPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("guestPurchaseDao", GuestPurchaseDAOImpl.class);
		guestPurchaseDAO.saveGuestPurchase(guestPurchase);
		
	}

	@Override
	public List<GuestPurchase> getAllGuestPurchases() {
		GuestPurchaseDAOImpl guestPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("guestPurchaseDao", GuestPurchaseDAOImpl.class);
		return guestPurchaseDAO.getAllGuestPurchases();
	}
	
}

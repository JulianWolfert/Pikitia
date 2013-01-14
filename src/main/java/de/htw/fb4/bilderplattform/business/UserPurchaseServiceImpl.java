package de.htw.fb4.bilderplattform.business;

import de.htw.fb4.bilderplattform.dao.UserPurchase;
import de.htw.fb4.bilderplattform.dao.UserPurchaseDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

public class UserPurchaseServiceImpl implements IUserPurchaseService {
	
	@Override
	public void saveUserPurchase(UserPurchase userPurchase) {
		if (userPurchase == null) {
			return;
		}
		UserPurchaseDAOImpl userPurchaseDAO = ApplicationContextProvider
				.getApplicationContext().getBean("userPurchaseDao", UserPurchaseDAOImpl.class);
		userPurchaseDAO.saveUserPurchase(userPurchase);
	}
}

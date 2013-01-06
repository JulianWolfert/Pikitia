package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Peter Horn
 * 
 */
public interface IPurchaseService {
	public List<Purchase_Image> getUserPurchase(int idUser);
	/**
	 * returns a GuestPurchase-, a User-Object null
	 * 
	 * @param idPurchase
	 * @return a GuestPurchase-, a User-Object or null
	 */
	public Object get_Guest_User_PurchaseData(int idPurchase);
	public Bankaccount getBankaccountByIdUser(int idUser);
}

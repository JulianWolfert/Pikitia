package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.Image;
import de.htw.fb4.bilderplattform.dao.Purchase_Image;
import de.htw.fb4.bilderplattform.dao.UserPurchase;

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
	public Object get_Guest_User_PurchaseData(String urlId);
	public Bankaccount getBankaccountByIdUser(int idUser);
	
	public void saveUserPurchase(List<Image> imageList, UserPurchase userPurchase);
	public void saveGuestPurchase(List<Image> imageList, GuestPurchase guestPurchase);
	

	
}

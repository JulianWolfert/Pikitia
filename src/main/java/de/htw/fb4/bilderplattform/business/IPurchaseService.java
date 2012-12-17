package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Purchase_Image;

/**
 * 
 * @author Peter Horn
 * 
 */
public interface IPurchaseService {
	public List<Purchase_Image> getUserPurchase(int idUser);
}

package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.GuestPurchase;


/**
 * 
 * @author Benjamin Schock
 * 
 */

public interface IGuestPurchaseService {

	public GuestPurchase getGuestPurchaseByID(int guestPurchaseID);
	
	public void saveGuestPurchase(GuestPurchase guestPurchase);
	
	public List<GuestPurchase> getAllGuestPurchases();
	
}

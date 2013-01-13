package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Bankaccount;

/**
 * @since 03.01.2012
 * @author Benjamin Schock
 * 
 */

public interface IBankaccountService {

	public void saveOrUpdateUser(Bankaccount bankaccount);
		
	public Bankaccount getBankaccountByUserId(int userId);

	public Bankaccount getBankaccountById(int idBankaccount);
	
}


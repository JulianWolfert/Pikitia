package de.htw.fb4.bilderplattform.business;

import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.BankaccountDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;

class BankaccountServiceImpl implements IBankaccountService {

	@Override
	public void saveOrUpdateUser(Bankaccount bankaccount) {
		if (bankaccount == null) {
			return;
		}
		BankaccountDAOImpl bankaccountDAO = ApplicationContextProvider
				.getApplicationContext().getBean("bankDao", BankaccountDAOImpl.class);
		bankaccountDAO.saveBankaccount(bankaccount);
	}
	
	@Override
	public Bankaccount getBankaccountById(int idBankaccount) {
		BankaccountDAOImpl bankaccountDAO = ApplicationContextProvider
				.getApplicationContext().getBean("bankDao", BankaccountDAOImpl.class);
		return bankaccountDAO.getBankaccountById(idBankaccount);
		
	}

	@Override
	public Bankaccount getBankaccountByUserId(int idUser) {
		BankaccountDAOImpl bankaccountDAO = ApplicationContextProvider
				.getApplicationContext().getBean("bankDao", BankaccountDAOImpl.class);
		return bankaccountDAO.getBankaccountByUserId(idUser);
		
	}
}

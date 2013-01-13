package de.htw.fb4.bilderplattform.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 03.01.2013
 * @author Benjamin Schock
 * 
 */

public class BankaccountDAOImpl extends AbstractDAO {

	
	@Transactional
	public void saveBankaccount(Bankaccount bankaccount) {
		sessionFactory.getCurrentSession().saveOrUpdate(bankaccount);
	}
	
	
	@Transactional
	public Bankaccount getBankaccountById(int idBankaccount) {
		Bankaccount bankaccount = (Bankaccount) sessionFactory.getCurrentSession().load(Bankaccount.class, idBankaccount);
		return bankaccount;
	}
	
	@Transactional
	public Bankaccount getBankaccountByUserId(int idUser) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bankaccount.class)
				.add(Restrictions.eq("bankaccountOwner.idUser", idUser));
		return (Bankaccount) criteria.uniqueResult();
		/*
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT b FROM Bankaccount ba where ba.user = " + idUser);
		return (Bankaccount) query.uniqueResult();
		*/
	}
	
	
	
	/*
	@Transactional
	public void deleteBankaccount(int idBankaccount) {
		Bankaccount bankaccount = getBankaccountById(idBankaccount);
		deleteBankaccount(bankaccount);
	}

	
	@Transactional
	public void deleteBankaccount(Bankaccount bankaccount){
		bankaccount.setIsDeleted(true);
		sessionFactory.getCurrentSession().saveOrUpdate(bankaccount);
	}
	*/

}



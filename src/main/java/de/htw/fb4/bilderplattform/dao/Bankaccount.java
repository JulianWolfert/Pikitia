package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * 
 * @author Peter Horn
 *
 */
@Entity
@Table(name = "Bankaccount")
public class Bankaccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idBankaccount")
	private Integer idBankaccount;

	@Column(name = "account_nr", nullable = false)
	private String account_nr;

	@Column(name = "bank", nullable = false)
	private String bank;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "idBankaccountOwner_idUser", referencedColumnName = "idUser")
	private User bankaccountOwner;
	
	public Bankaccount(){
		super();
	}

	public Bankaccount(User bankaccountOwner, String account_nr, String bank) {
		super();
		this.bankaccountOwner = bankaccountOwner;
		this.account_nr = account_nr;
		this.bank = bank;
		
	}

	public String getAccount_nr() {
		return account_nr;
	}

	public String getBank() {
		return bank;
	}

	public Integer getIdBankaccount() {
		return idBankaccount;
	}

	public void setAccount_nr(String account_nr) {
		this.account_nr = account_nr;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setIdBankaccount(Integer idBankaccount) {
		this.idBankaccount = idBankaccount;
	}
	
	public User getBankaccountOwner() {
		return bankaccountOwner;
	}
	
	public void setBankaccountOwner(User bankaccountOwner) {
		this.bankaccountOwner = bankaccountOwner;
	}

}
package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private Integer account_nr;

	@Column(name = "bank", nullable = false)
	private Integer bank;

	@Column(name = "User_idUser", nullable = false)
	private Integer User_idUser;

	public Integer getAccount_nr() {
		return account_nr;
	}

	public Integer getBank() {
		return bank;
	}

	public Integer getIdBankaccount() {
		return idBankaccount;
	}

	public Integer getUser_idUser() {
		return User_idUser;
	}

	public void setAccount_nr(Integer account_nr) {
		this.account_nr = account_nr;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public void setIdBankaccount(Integer idBankaccount) {
		this.idBankaccount = idBankaccount;
	}

	public void setUser_idUser(Integer user_idUser) {
		User_idUser = user_idUser;
	}
}
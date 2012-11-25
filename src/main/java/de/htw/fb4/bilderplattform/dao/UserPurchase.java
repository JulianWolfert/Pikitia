package de.htw.fb4.bilderplattform.dao;

import javax.persistence.*;

/**
 * 
 * @author Peter Horn
 *
 */
@Entity
@Table(name = "UserPurchase")
public class UserPurchase {

	@Id
	@GeneratedValue
	@Column(name = "idUserPurchase")
	private Integer idUserPurchase;

	@Column(name = "User_idUser", nullable = false)
	private Integer User_idUser;

	public Integer getIdUserPurchase() {
		return idUserPurchase;
	}

	public Integer getUser_idUser() {
		return User_idUser;
	}

	public void setIdUserPurchase(Integer idUserPurchase) {
		this.idUserPurchase = idUserPurchase;
	}

	public void setUser_idUser(Integer user_idUser) {
		User_idUser = user_idUser;
	}
}

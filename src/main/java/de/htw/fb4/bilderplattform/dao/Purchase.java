package de.htw.fb4.bilderplattform.dao;

import java.util.Date;

import javax.persistence.*;


/**
 * 
 * @author Peter Horn
 *
 */
@Entity
@Table(name = "Purchase")
public class Purchase {

	@Id
	@GeneratedValue
	@Column(name = "idPurchase")
	private Integer idPurchase;

	@Column(name = "order_nr", nullable = false)
	private String order_nr;

	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "UserPurchase_idUserPurchase")
	private Integer UserPurchase_idUserPurchase;

	@Column(name = "GuestPurchase_idGuestPurchase")
	private Integer GuestPurchase_idGuestPurchase;

	public Date getDate() {
		return date;
	}

	public Integer getGuestPurchase_idGuestPurchase() {
		return GuestPurchase_idGuestPurchase;
	}

	public Integer getIdPurchase() {
		return idPurchase;
	}

	public String getOrder_nr() {
		return order_nr;
	}

	public Integer getUserPurchase_idUserPurchase() {
		return UserPurchase_idUserPurchase;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setIdPurchase(Integer idPurchase) {
		this.idPurchase = idPurchase;
	}

	public void setGuestPurchase_idGuestPurchase(
			Integer guestPurchase_idGuestPurchase) {
		GuestPurchase_idGuestPurchase = guestPurchase_idGuestPurchase;
	}

	public void setOrder_nr(String order_nr) {
		this.order_nr = order_nr;
	}
	
	public void setUserPurchase_idUserPurchase(
			Integer userPurchase_idUserPurchase) {
		UserPurchase_idUserPurchase = userPurchase_idUserPurchase;
	}
}

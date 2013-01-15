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
	
	@Column(name = "urlId")
	private String urlId;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public Integer getIdUserPurchase() {
		return idUserPurchase;
	}

	public String getUrlId(){
		return urlId;
	}
	
	public User getUser() {
		return user;
	}

	public void setIdUserPurchase(Integer idUserPurchase) {
		this.idUserPurchase = idUserPurchase;
	}
	
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}

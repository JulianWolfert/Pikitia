package de.htw.fb4.bilderplattform.dao;

import javax.persistence.*;

/**
 * 
 * @author Peter Horn, Benjamin Schock
 *
 */
@Entity
@Table(name = "GuestPurchase")
public class GuestPurchase {
	@Id
	@GeneratedValue
	@Column(name = "idGuestPurchase")
	private Integer idGuestPurchase;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "street_nr", nullable = false)
	private String street_nr;

	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "postalcode", nullable = false)
	private String postalcode;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "bank", nullable = false)
	private String bank;

	@Column(name = "account_nr", nullable = false)
	private String account_nr;

	public String getAccount_nr() {
		return account_nr;
	}

	public String getBank() {
		return bank;
	}

	public String getEmail() {
		return email;
	}

	public Integer getIdGuestPurchase() {
		return idGuestPurchase;
	}

	public String getName() {
		return name;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public String getStreet() {
		return street;
	}

	public String getStreet_nr() {
		return street_nr;
	}
	
	public String getCity() {
		return city;
	}

	public String getSurname() {
		return surname;
	}

	public void setAccount_nr(String account_nr) {
		this.account_nr = account_nr;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIdGuestPurchase(Integer idGuestPurchase) {
		this.idGuestPurchase = idGuestPurchase;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStreet_nr(String street_nr) {
		this.street_nr = street_nr;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
}

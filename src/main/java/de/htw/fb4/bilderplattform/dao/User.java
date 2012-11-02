package de.htw.fb4.bilderplattform.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/************************************************
 * <p>user database object</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
@Entity
@Table(name="User", uniqueConstraints = {@UniqueConstraint(columnNames={"username"})})
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUser")
	private Integer idUser;

	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="isNormalUser", nullable=false)
	private boolean isNormalUser;
	
	@Column(name="isAdmin", nullable=false)
	private boolean isAdmin;
	
	@Column(name="lastUpdateDate", nullable=false)
	private Date lastUpdateDate;
	
	@Column(name="isDeleted", nullable=false, columnDefinition ="tinyint(1) default 0")
	private boolean isDeleted;

	/* 
	 * Methods
	 * 
	 */
	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNormalUser() {
		return isNormalUser;
	}

	public void setNormalUser(boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
	
}

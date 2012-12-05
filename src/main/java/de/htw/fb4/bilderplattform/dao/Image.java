package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mysql.jdbc.Blob;

/************************************************
 * <p>
 * image database object
 * </p>
 * 
 * <p>
 * 
 * @author Wojciech Konitzer
 *         </p>
 *         <p>
 *         17.11.2012
 *         </p>
 ************************************************/
@Entity
@Table(name = "Image")
public class Image implements Serializable {

	private static final long serialVersionUID = -4154608821111026121L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idImage")
	private Integer idImage;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@Column(name = "file")
	private Blob file;

	@Column(name = "preview_file")
	private Blob preview_file;

	@Column(name = "timeStamp")
	private Date timeStamp = new Date();

	//TODO: statt username -> idUser
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username")
	private User user;

	public Image() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Blob getFilename() {
		return file;
	}

	public void setFilename(Blob filename) {
		this.file = filename;
	}
	
	public Blob getPreview_file() {
		return preview_file;
	}
	
	public void setPreview_file(Blob preview_file) {
		this.preview_file = preview_file;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

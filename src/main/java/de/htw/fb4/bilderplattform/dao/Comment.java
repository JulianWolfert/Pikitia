package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Peter Horn
 * @author Wojciech Konitzer
 *
 */
@Entity
@Table(name = "Comment") //TODO: w.k. Wieso heisst die Klasse Comment und nicht Rating?!
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idComment")
	private Integer idComment;

	@Column(name = "stars", nullable=false)
	private Integer stars;

	@Column(name = "comment")
	private String comment;

//	@Column(name = "Image_idImage", nullable=false)
	@ManyToOne(fetch = FetchType.EAGER)
	private Image image;
	
	@Column(name = "timeStamp")
	private Date timeStamp = new Date();
	
	@Column(name = "Image_username", nullable=false)
	private String image_username;

	//required for hibernate queries
	public Comment(){
		
	}
	
	public Comment(Integer stars, String comment, Image image, String username) {
		super();
		this.stars = stars;
		this.comment = comment;
		this.image = image;
		this.image_username = username;
	}

	public String getComment() {
		return comment;
	}

	public Integer getIdComment() {
		return idComment;
	}

	public Image getImage() {
		return image;
	}

	public Integer getStars() {
		return stars;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getImage_username() {
		return image_username;
	}
	
}

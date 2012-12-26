package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Peter Horn
 * @author Wojciech Konitzer
 *
 */
@Entity
@Table(name = "Comment")
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

	@Column(name = "Image_idImage", nullable=false)
	private Integer Image_idImage;
	
	@Column(name = "timeStamp")
	private Date timeStamp = new Date();

	public String getComment() {
		return comment;
	}

	public Integer getIdComment() {
		return idComment;
	}

	public Integer getImage_idImage() {
		return Image_idImage;
	}

	public Integer getStars() {
		return stars;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setImage_idImage(Integer image_idImage) {
		Image_idImage = image_idImage;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
	
}

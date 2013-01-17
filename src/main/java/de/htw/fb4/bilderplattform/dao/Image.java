package de.htw.fb4.bilderplattform.dao;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.htw.fb4.bilderplattform.business.util.FileUtil;

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

	@Column(name = "price", columnDefinition = "Decimal(10,2)")
	private Double price;

	@Column(name = "file")
	private String file;

	@Column(name = "preview_file")
	private String preview_file;
	
	@Column(name = "thumb_file")
	private String thumb_file;

	@Column(name = "timeStamp")
	private Date timeStamp = new Date();
	
//	@ManyToOne(fetch = FetchType.LAZY) - caused an exception
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "User_idUser", referencedColumnName = "idUser")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="image", cascade = {CascadeType.ALL})
	private List<Comment> comments = new ArrayList<Comment>();
	
//Version 300
	@OneToMany(fetch = FetchType.LAZY, mappedBy="image", cascade = {CascadeType.ALL})
	private List<Purchase_Image> purchase_image = new ArrayList<Purchase_Image>();

	public Image() {

	}

	public Integer getIdImage() {
		return idImage;
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

	public String getFile() {
		return file;
	}
	
	public byte[] getFileAsBytes() throws UnsupportedEncodingException, IOException, Exception{
		if(this.file == null){
			return null;
		}
		return FileUtil.fileNameToBytes(this.file);
	}

	public void setFile(String filename) {
		this.file = filename;
	}
	
	public String getPreview_file() {
		return preview_file;
	}
	
	public byte[] getPreviewFileAsBytes(String path) throws UnsupportedEncodingException, IOException, Exception{
		if(this.preview_file == null){
			return null;
		}
		return FileUtil.fileNameToBytes(path + this.preview_file);
	}
	
	public void setPreview_file(String preview_file) {
		this.preview_file = preview_file;
	}

	public String getThumb_file() {
		return thumb_file;
	}
	
	public byte[] getThumbFileAsBytes(String path) throws UnsupportedEncodingException, IOException, Exception{
		if(this.thumb_file == null){
			return null;
		}
		return FileUtil.fileNameToBytes(path + this.thumb_file);
	}

	public void setThumb_file(String thumb_file) {
		this.thumb_file = thumb_file;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void addComment(Comment comment){
		this.comments.add(comment);
	}

}

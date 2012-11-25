package de.htw.fb4.bilderplattform.dao;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 
 * @author Peter Horn
 *
 */
@Entity
@Table(name = "Purchase_Image")
public class Purchase_Image implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Purchase_idPurchase", nullable=false)
	private Integer Purchase_idPurchase;

	@Id
	@Column(name = "Image_idImage", nullable=false)
	private Integer Image_idImage;

	public Integer getImage_idImage() {
		return Image_idImage;
	}

	public Integer getPurchase_idPurchase() {
		return Purchase_idPurchase;
	}

	public void setImage_idImage(Integer image_idImage) {
		Image_idImage = image_idImage;
	}

	public void setPurchase_idPurchase(Integer purchase_idPurchase) {
		Purchase_idPurchase = purchase_idPurchase;
	}
}

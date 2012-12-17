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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Image_idImage", referencedColumnName = "idImage")
	private Image image;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Purchase_idPurchase", referencedColumnName = "idPurchase")
	private Purchase perchase;

	public Purchase getPerchase() {
		return perchase;
	}

	public Image getImage() {
		return image;
	}
}

package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Image;

/************************************************
 * <p>Provides search functionality</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 02.11.2012
 * </p>
 ************************************************/
public interface ISearchService {

	public List<Image> searchImages(String searchString);
	
}

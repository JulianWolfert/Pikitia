package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Image;

public interface ISearchService {

	public List<Image> searchImages(String searchString);
	
}

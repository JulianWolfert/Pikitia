package de.htw.fb4.bilderplattform.business;

/************************************************
 * <p>example service</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public interface ITestService {

	/************************************************
	 * <p>example method. stores a test string in
	 * the database using the persistence layer</p>
	 * <p>
	 * @author Josch Rossa
	 * </p>
	 * <p>
	 * 24.10.2012
	 * </p>
	 ************************************************/
	public void createNewTestStringInDatabase(String str);
	
}

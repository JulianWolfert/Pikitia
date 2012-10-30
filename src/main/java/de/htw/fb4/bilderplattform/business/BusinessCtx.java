package de.htw.fb4.bilderplattform.business;

/************************************************
 * <p>gives access to all business services</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public class BusinessCtx {

	// singleton
	private static BusinessCtx instance = new BusinessCtx();
	
	// services
	private ITestService testService; 
	
	// singleton getter
	public static BusinessCtx getInstance() {
		return instance;
	}

	// initiates service only if it is really used
	public ITestService getTestService() {
		if(testService == null)
			this.testService = new TestServiceImpl();
		return testService;
	}
	
}

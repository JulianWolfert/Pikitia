package de.htw.fb4.bilderplattform.business;

/************************************************
 * <p>gives access to all business services</p>
 * <p>
 * @author Josch Rossa
 * 		   ben
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
	private IUserService userService;
	private IMessageService messageService;
	
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
	
	public IUserService getUserService() {
		if(userService == null)
			this.userService = new UserServiceImpl();
		return userService;
	}
	
	public IMessageService getMessageService() {
		if(messageService == null)
			this.messageService = new MessageServiceImpl();
		return messageService;
	}
	
}

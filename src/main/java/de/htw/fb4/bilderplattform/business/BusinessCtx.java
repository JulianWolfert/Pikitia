package de.htw.fb4.bilderplattform.business;

/************************************************
 * <p>gives access to all business services</p>
 * <p>
 * @author Josch Rossa,
 * 		   ben, konitzer
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
	private IMailService mailService;
	private IImageService imageService;
	private ISearchService searchService;
	private IPurchaseService purchaseService;
	
	// singleton getter
	public static BusinessCtx getInstance() {
		synchronized (instance) {
			return instance;
		}
	}

	// initiates service only if it is really used
	public ITestService getTestService() {
		if(testService == null)
			this.testService = new TestServiceImpl();
		return testService;
	}
	
	public IUserService getUserService() {
		if(userService == null) {
			this.userService = new UserServiceImpl();
		}
		return userService;
	}
	
	public IMessageService getMessageService() {
		if(messageService == null)
			this.messageService = new MessageServiceImpl();
		return messageService;
	}
	
	public IMailService getMailService() {
		if(mailService == null)
			this.mailService = new MailServiceImpl();
		return mailService;
	}
	
	public IImageService getIImageService() {
		if(imageService == null)
			this.imageService = new ImageServiceImpl();
		return imageService;
	}
	
	public ISearchService getSearchService() {
		if(searchService == null)
			this.searchService = new SearchServiceImpl();
		return searchService;
	}
	
	public IPurchaseService getPurchaseService(){
		if(this.purchaseService==null){
			this.purchaseService=new PurchaseServiceImpl();
		}
		return this.purchaseService;
	}	
}

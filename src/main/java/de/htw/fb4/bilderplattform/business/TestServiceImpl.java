package de.htw.fb4.bilderplattform.business;

import de.htw.fb4.bilderplattform.dao.TestDAOImpl;
import de.htw.fb4.bilderplattform.spring.context.ApplicationContextProvider;


// Attention: service implementation has default visibility
class TestServiceImpl implements ITestService {

	@Override
	public void createNewTestStringInDatabase(String str) {
		/*
		 *  in this case the business layer service does nothing but
		 *  using a single data layer function - but usually there are more
		 *  complex scenarios
		 *  
		 */
		TestDAOImpl testDAO = ApplicationContextProvider.getApplicationContext().getBean("myTestDao", TestDAOImpl.class);
		testDAO.saveString(str);
	}

}

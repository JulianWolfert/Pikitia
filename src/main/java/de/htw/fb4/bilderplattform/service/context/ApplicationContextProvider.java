package de.htw.fb4.bilderplattform.service.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/************************************************ 
 * <p>
 * gives access to the spring context and to the
 * DAO Objects which are defined in the
 * applicationContext.xml
 * </p>
 * <p>
 * e.g.
 * define the DAO object as follows:
 * </p>
 * {@code
 *  <bean id="myTestDao" class="de.htw.fb4.persist.TestDAOImpl">
 *  	<property name="sessionFactory" ref="mySessionFactory">
 *  </bean>
 * }
 * <p>
 * to access the DAO object use the following expression:
 * </p>
 * <code>
 * TestDAOImpl testDAO = AppCtxProvider.getApplicationContext().getBean("myTestDao", TestDAOImpl.class);
 * </code>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext appctx)
			throws BeansException {
		ApplicationContextProvider.context = appctx;
	}
}

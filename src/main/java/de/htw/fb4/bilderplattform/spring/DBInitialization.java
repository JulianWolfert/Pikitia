package de.htw.fb4.bilderplattform.spring;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author konitzer
 *
 */
public class DBInitialization implements InitializingBean{
	
	private void createAdminUsers(){
		User user = new User("wojtek", "wojtek", true, true, new Date(), false);
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		
		user = new User("josch", "josch", true, true, new Date(), false);
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createAdminUsers();
	}
}

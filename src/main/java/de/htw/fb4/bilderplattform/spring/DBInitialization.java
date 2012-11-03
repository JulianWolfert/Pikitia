package de.htw.fb4.bilderplattform.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author konitzer
 *
 */
public class DBInitialization implements InitializingBean{
	
	private final static List<User> adminUsers = new ArrayList<User>();
	
	public DBInitialization(){
		adminUsers.add(new User("wojtek", "wojtek", "w.konitzer@gmx.de", true, true, false));
		adminUsers.add(new User("josch", "josch", "s0537867@htw-berlin.de", true, true, false));
		adminUsers.add(new User("peter", "peter", "s0523841@htw-berlin.de", true, true, false));
	}
	
	private void createAdminUsers(){
		for(User user : adminUsers){
			BusinessCtx.getInstance().getUserService().saveOrUpdateUser(user);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createAdminUsers();
	}
}
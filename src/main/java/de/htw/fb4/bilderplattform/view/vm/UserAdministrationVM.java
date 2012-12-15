package de.htw.fb4.bilderplattform.view.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

/**
 * 
 * @author Peter Horn
 *
 */
public class UserAdministrationVM extends WebsiteLayoutVM{
	private class UserModelList extends ListModelList<User>{
		private static final long serialVersionUID = 1L;
		
		private User selectedUser=null;

		public UserModelList(Collection<User> userList) {
			super(userList);
		}
		
		public void setSelectedUser(User selectedUser){
			this.selectedUser=selectedUser;
			ArrayList<User> list = new ArrayList<>();
			list.add(selectedUser);
			this.setSelection(list);
		}
		
		public User getSelectedUser(){
			return this.selectedUser;
		}
	} 
	private UserModelList userList = new UserModelList(BusinessCtx.getInstance().getUserService().getAllUser()) ;

	@Init
	public void init() {
		if (!this.userList.isEmpty()) {
			this.userList.setSelectedUser(userList.get(0));
		}
	}

	public ListModelList<User> getUserList() {
		return this.userList;
	}

	public void setSelectedUser(User selected) {
		this.userList.setSelectedUser(selected);
	}

	public User getSelectedUser() {
		return this.userList.getSelectedUser();
	}

	@Command
	public void updateUser() {
		BusinessCtx.getInstance().getUserService().saveOrUpdateUser(this.getSelectedUser());
	}

	@Command
	public void deleteUser(@BindingParam("user") final User user) {
		Messagebox.show("Benutzer \"" + user.getUsername() + "\" löschen?", "Benutzer löschen", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (((Integer) event.getData()).intValue() == Messagebox.YES) {
					BusinessCtx.getInstance().getUserService().deleteUser(user);
					UserAdministrationVM.this.userList.remove(user);
					if (!UserAdministrationVM.this.userList.isEmpty()) {
						UserAdministrationVM.this.userList.setSelectedUser(UserAdministrationVM.this.userList.get(0));
					}
					return;
				}
			}
		});
	}
	
	@Command
	public void editUser(@BindingParam("user") final User user) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idUser", user.getIdUser());
		Executions.createComponents("<popup path>", null, sessionMap);
	}
}
package de.htw.fb4.bilderplattform.view.vm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.User;

public class UserAdministrationVM {
	private User selectedUser;
	private List<User> users = BusinessCtx.getInstance().getUserService()
			.getAllUser();

	@Init
	public void init() {
		if (!this.users.isEmpty()) {
			this.selectedUser = users.get(0);
		}
	}

	public List<User> getUserList() {
		return this.users;
	}

	public void setSelectedUser(User selected) {
		this.selectedUser = selected;
	}

	public User getSelectedUser() {
		return this.selectedUser;
	}

	@Command
	public void updateUser() {
		BusinessCtx.getInstance().getUserService()
				.saveOrUpdateUser(this.selectedUser);
	}

	@Command
	public void deleteUser() {
		BusinessCtx.getInstance().getUserService()
				.deleteUser(this.selectedUser);
	}
}
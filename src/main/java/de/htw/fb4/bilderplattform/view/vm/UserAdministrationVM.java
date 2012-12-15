package de.htw.fb4.bilderplattform.view.vm;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
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
public class UserAdministrationVM {
	private ListModelList<User> userList = new ListModelList<>(BusinessCtx.getInstance().getUserService().getAllUser());

	public List<User> getUserList() {
		return this.userList;
	}

	@Command
	public void redirectToStart() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}


	@Command
	public void deleteUser(@BindingParam("user") final User user) {
		Messagebox.show("Benutzer \"" + user.getUsername() + "\" löschen?", "Benutzer löschen", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (((Integer) event.getData()).intValue() == Messagebox.YES) {
					BusinessCtx.getInstance().getUserService().deleteUser(user);
					UserAdministrationVM.this.userList.remove(user);
					return;
				}
			}
		});
	}
	
	@Command
	public void editUser(@BindingParam("user") final User user) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("idUser", user.getIdUser());
		Executions.createComponents("/admin/editUser.zul", null, sessionMap);
	}
}
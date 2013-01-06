package de.htw.fb4.bilderplattform.view.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;
import de.htw.fb4.bilderplattform.dao.Bankaccount;
import de.htw.fb4.bilderplattform.dao.GuestPurchase;
import de.htw.fb4.bilderplattform.dao.User;
import de.htw.fb4.bilderplattform.dao.UserPurchase;

/**
 * 
 * @author Peter Horn
 * 
 */
public class PurchaseDetailsVM {
	public class Customer {
		private String name;
		private String email;
		private String bank;
		private String account;

		public Customer(String name, String email, String bank, String account) {
			this.name = name;
			this.email = email;
			this.bank = bank;
			this.account = account;
		}

		public String getAccount() {
			return account;
		}

		public String getBank() {
			return bank;
		}

		public String getEmail() {
			return email;
		}

		public String getName() {
			return name;
		}
	}

	private Customer customer = new Customer("unbekannt", "unbekannt",
			"unbekannt", "unbekannt");

	@Wire("#modalShowPurchaseDetails")
	private Window win;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("idPurchase") Integer idPurchase) {
		Object customerData = BusinessCtx.getInstance().getPurchaseService()
				.get_Guest_User_PurchaseData(idPurchase);
		if (customerData == null) {
			return;
		}

		if (customerData instanceof GuestPurchase) {
			GuestPurchase guest = (GuestPurchase) customerData;
			this.customer.name = guest.getName() + " " + guest.getSurname() + " (Gast)";
			this.customer.email = guest.getEmail();
			this.customer.bank = guest.getBank();
			this.customer.account = guest.getAccount_nr();
		} else if (customerData instanceof User) {
			User user = (User) customerData;
			Bankaccount bank = BusinessCtx.getInstance().getPurchaseService()
					.getBankaccountByIdUser(user.getIdUser());
			this.customer.name = user.getUsername() + " (User)";
			this.customer.email = user.getEmail();
			this.customer.bank = bank.getBank();
			this.customer.account = bank.getAccount_nr();
		}
	}

	@Command
	public void closeThis() {
		this.win.detach();
	}

	public Customer getCustomer() {
		return customer;
	}
}

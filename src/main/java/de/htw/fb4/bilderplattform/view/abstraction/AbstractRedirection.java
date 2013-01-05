package de.htw.fb4.bilderplattform.view.abstraction;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class AbstractRedirection {

	@Command
	public void redirectToStart() {
		Executions.getCurrent().sendRedirect("/index.zul");
	}
	
}

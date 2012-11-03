package de.htw.fb4.bilderplattform.view.vm;


import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class WebsiteLayoutVM {

	private static final String searchUrl = "http://www.zkoss.org/doc/searchresult.jsp?cx=008321236477929467003%3A63kdpeqkkvw&cof=FORID%3A11&q=";

	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Command
	public void search() {
		Executions.getCurrent().sendRedirect(searchUrl.concat(search), "_zk");
	}

}

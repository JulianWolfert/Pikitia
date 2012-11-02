package de.htw.fb4.bilderplattform.view.formmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.InputEvent;

import de.htw.fb4.bilderplattform.dao.User;

public class RegistrationFormViewModel {

	private User user;
	private String dateFormat;
	private int memoHeight = 6;
	private String foregroundColour = "#000000", backgroundColour = "#FDC966";

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getForegroundColour() {
		return foregroundColour;
	}

	public void setForegroundColour(String foregroundColor) {
		this.foregroundColour = foregroundColor;
	}

	public String getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(String backgroundColor) {
		this.backgroundColour = backgroundColor;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getMemoHeight() {
		return memoHeight;
	}

	public void setMemoHeight(int memoHeight) {
		this.memoHeight = memoHeight;
	}
}

package de.htw.fb4.bilderplattform.view;

import org.springframework.beans.BeansException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import de.htw.fb4.bilderplattform.business.BusinessCtx;



/************************************************
 * <p>example "codebehind". this file belongs to a .zul zk file</p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 24.10.2012
 * </p>
 ************************************************/
public class TestCodebehind extends SelectorComposer<Window> {
	private static final long serialVersionUID = 1L;
	
	@Wire
	Textbox input;
	
	@Wire
	Label resultVal;
	
	@Wire
	Vlayout result;
	
	@Listen("onClick=#save")
	public void submit(Event event) {
		try {
			String str = input.getValue();
			// we use the business layer at this point!!
			BusinessCtx.getInstance().getTestService().createNewTestStringInDatabase(str);
			resultVal.setValue("String: " + str + " erfolgreich gespeichert.");
		} catch (WrongValueException e) {
			resultVal.setValue("Fehler beim Speichern");
			e.printStackTrace();
		} catch (BeansException e) {
			resultVal.setValue("Fehler beim Speichern");
			e.printStackTrace();
		}
	}
	
}

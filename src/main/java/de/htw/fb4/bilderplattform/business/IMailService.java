package de.htw.fb4.bilderplattform.business;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import de.htw.fb4.bilderplattform.business.mail.IMail;

/************************************************
 * <p>Defines email sending functionality</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 15.11.2012
 * </p>
 ************************************************/
public interface IMailService {

	/**
	 * Sends a mail instance using the javamail api.
	 * 
	 * @param mail - email content */
	public void sendMail(IMail mail) throws IOException, AddressException, MessagingException;
	
}

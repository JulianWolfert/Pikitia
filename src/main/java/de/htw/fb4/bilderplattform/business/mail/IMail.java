package de.htw.fb4.bilderplattform.business.mail;

import java.util.Date;

/************************************************
 * <p>
 * A mail instance can be sent by javamail api
 * </p>
 * 
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 14.11.2012
 * </p>
 ************************************************/
public interface IMail {

	/** Additional info in the mail body.
	 *  The from field will be filled with pikitia.info@gmail.com  */
	public String getSender();
	/** Additional info in the mail body.
	 *  The from field will be filled with pikitia.info@gmail.com  */
	public IMail setSender(String sender);
	public String getReceiver();
	public IMail setReceiver(String receiver);
	public String getSubject();
	public IMail setSubject(String subject);
	public String getMessage();
	public IMail setMessage(String message);
	/** Additional info in the mail body. */
	public Date getTimeStamp();
	/** Additional info in the mail body. */
	public IMail setTimeStamp(Date timeStamp);
	
}

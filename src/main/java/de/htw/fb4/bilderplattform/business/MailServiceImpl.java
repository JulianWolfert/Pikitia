package de.htw.fb4.bilderplattform.business;

import java.io.IOException;
import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.htw.fb4.bilderplattform.business.mail.IMail;
import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/************************************************
 * <p>Defines email sending functionality</p>
 * <p>
 * @author Josch Rossa
 * </p>
 * <p>
 * 15.11.2012
 * </p>
 ************************************************/
public class MailServiceImpl implements IMailService {
		
	@SuppressWarnings("restriction")
	@Override
	public void sendMail(IMail mail) throws IOException, AddressException,
			MessagingException {		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Session session = createSession();
		Transport transport = session.getTransport();
		MimeMessage message = createMimeMessage(session, mail);
		transport.connect();
		Transport.send(message);
		transport.close();
	}
	
	// uses the properties defined in: mail.properties
	private Properties getConnectionProperties() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol",
				SpringPropertiesUtil.getProperty("mail.transport.protocol"));
		props.setProperty("mail.host",
				SpringPropertiesUtil.getProperty("mail.host"));
		props.put("mail.smtp.auth",
				SpringPropertiesUtil.getProperty("mail.smtp.auth"));
		props.put("mail.smtp.port",
				SpringPropertiesUtil.getProperty("mail.smtp.port"));
		props.put("mail.debug",
				SpringPropertiesUtil.getProperty("mail.debug"));
		props.put("mail.smtp.socketFactory.port",
				SpringPropertiesUtil.getProperty("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class",
				SpringPropertiesUtil.getProperty("mail.smtp.socketFactory.class"));
		props.put("mail.smtp.socketFactory.fallback",
				SpringPropertiesUtil.getProperty("mail.smtp.socketFactory.fallback"));
		return props;
	}
	
	private Session createSession() {
		Session session = Session.getDefaultInstance(getConnectionProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								SpringPropertiesUtil.getProperty("user"),
								SpringPropertiesUtil.getProperty("password"));
					}
				});
		boolean debug = Boolean.parseBoolean(SpringPropertiesUtil.getProperty("mail.debug"));
		session.setDebug(debug);
		return session;
	}

	private MimeMessage createMimeMessage(Session session, IMail mail) throws MessagingException {
		InternetAddress addressFrom = new InternetAddress(mail.getSender());
		MimeMessage message = new MimeMessage(session);
		message.setSender(addressFrom);
		message.setSubject(mail.getSubject());
		message.setContent(mail.getMessage() 
				+ "\n\n" + mail.getTimeStamp() 
				+ " : " + mail.getSender(),
				"text/plain");
		String sendTo[] = { mail.getReceiver() };
		if (sendTo != null) {
			InternetAddress[] addressTo = new InternetAddress[sendTo.length];
			// if we want to send mails to more than one recipient - here is already the functionality ;-)
			for (int i = 0; i < sendTo.length; i++) {
				addressTo[i] = new InternetAddress(sendTo[i]);
			}
			message.setRecipients(Message.RecipientType.TO, addressTo);
		}
		return message;
	}
	
}

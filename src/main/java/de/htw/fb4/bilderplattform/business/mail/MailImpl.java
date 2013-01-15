package de.htw.fb4.bilderplattform.business.mail;

import java.util.Date;

public class MailImpl implements IMail {

	private String sender;
	private String receiver;
	private String subject;
	private String message;
	private Date timeStamp;
	
	public String getSender() {
		return sender;
	}
	public IMail setSender(String sender) {
		this.sender = sender;
		return this;
	}
	public String getReceiver() {
		return receiver;
	}
	public IMail setReceiver(String receiver) {
		this.receiver = receiver;
		return this;
	}
	public String getSubject() {
		return subject;
	}
	public IMail setSubject(String subject) {
		this.subject = subjectFilter(subject);
		return this;
	}
	public String getMessage() {
		return message;
	}
	public IMail setMessage(String message) {
		this.message = message;
		return this;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public IMail setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}
	
	private String subjectFilter(String subject) {
		subject = subject.replaceAll("ß", "ss");
		subject = subject.replaceAll("ü", "ue");
		subject = subject.replaceAll("ä", "ae");
		subject = subject.replaceAll("ö", "oe");
		return subject;
	}

}

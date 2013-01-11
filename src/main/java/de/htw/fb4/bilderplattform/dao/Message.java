package de.htw.fb4.bilderplattform.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @since 10.11.2012
 * @author Benjamin Schock
 * 
 */

@Entity
@Table(name="Message")
public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMessage")
	private Integer idMessage;
	
	@Column(name = "timeStamp")
	private Date timeStamp;
	
	//@Column(name = "receiver")
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "idReceiver_idUser", referencedColumnName = "idUser")
	private User receiver;
	
	
	
	// Foreign-Key relationships must be realized
	//@ManyToOne 
	//@JoinColumn(name="idSender")
//	@Column(name="idReceiver_idUser", nullable=false)
//	private Integer idReceiver_idUser;
	
	// if its a Mail, then this shouldn't be empty
	@Column(name="email")
	private String email;
	
	// to which conversation/topic belongs this certain message
	@Column(name="idTopic", nullable=false)
	private Integer idTopic;
	
	@Column(name="subject")
	private String subject;

	@Column(name="text")
	private String text;
	
	@Column(name = "isDeleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = false;
	
	@Column(name = "isRead", nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean isRead = false;
	
	// constructor declaration
	public Message(){
		super();
	}
	
	// constructor for starting a new conversation
//	public Message(Integer idSender, Integer idReceiver, String title, String text) {
//		super();
//		// find the last topicId and increment it by 1
//		this.idSender = idSender;
//		this.idReceiver = idReceiver;
//		this.title = title;
//		this.text = text;
//	}
	
	// constructor for replying to an existent message
	public Message(User receiver, String email, Integer idTopic, String subject, String text) {
		super();
		this.timeStamp = new Date();
		this.receiver = receiver;
		this.email = email;
		this.idTopic = idTopic;
		this.subject = subject;
		this.text = text;		
	}

	
	
	//methods
	public Integer getIdMessage(){
		return this.idMessage;
	}
	


	public Date getTimeStamp(){
		return this.timeStamp;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Integer getIdTopic() {
		return this.idTopic;
	}
	
	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}

	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public boolean getIsDeleted(){
		return this.isDeleted;
	}
	
	public void setIsDeleted(boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	
	public boolean getIsRead(){
		return this.isRead;
	}
	
	public void setIsRead(boolean isRead){
		this.isRead = isRead;
	}
}

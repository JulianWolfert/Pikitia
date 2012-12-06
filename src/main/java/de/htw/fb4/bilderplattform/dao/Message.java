package de.htw.fb4.bilderplattform.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	// Foreign-Key relationships must be realized
	//@ManyToOne 
	//@JoinColumn(name="idSender")
	@Column(name="idSender_idUser")
	private Integer idSender_idUser;

	// Foreign-Key relationships must be realized
	//@ManyToOne 
	//@JoinColumn(name="idSender")
	@Column(name="idReceiver_idUser", nullable=false)
	private Integer idReceiver_idUser;
	
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
	
		
	
	// constructor for starting a new conversation
//	public Message(Integer idSender, Integer idReceiver, String title, String text) {
//		
//		// find the last topicId and increment it by 1
//		this.idSender = idSender;
//		this.idReceiver = idReceiver;
//		this.title = title;
//		this.text = text;
//	}
	
	// constructor for replying to an existent message
	public Message(Integer idSender_idUser, Integer idReceiver_idUser, String email, Integer idTopic, String subject, String text) {
	
		this.timeStamp = new Date();
		this.idSender_idUser = idSender_idUser;
		this.idReceiver_idUser = idReceiver_idUser;
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
	
	public Integer getIdSender_idUser() {
		return this.idSender_idUser;
	}

	public void setIdSender(Integer idSender_idUser) {
		this.idSender_idUser = idSender_idUser;
	}
	
	public Integer getIdReceiver_idUser() {
		return this.idReceiver_idUser;
	}

	public void setIdReceivwer(Integer idReceiver_idUser) {
		this.idReceiver_idUser = idReceiver_idUser;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
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
}

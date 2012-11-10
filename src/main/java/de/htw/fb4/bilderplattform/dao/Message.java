package de.htw.fb4.bilderplattform.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/************************************************
 * <p>message database object</p>
 * 
 * <p>
 * @author ben
 * </p>
 * <p>
 * 10.11.2012
 * </p>
 ************************************************/
@Entity
@Table(name="Message")
public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMessage")
	private Integer idMessage;
	
	@Column(name = "timeStamp")
	private Date timeStamp = new Date();
	
	// Foreign-Key relationships must be realized
	//@ManyToOne 
	//@JoinColumn(name="idSender")
	@Column(name="idSender")
	private Integer idSender;

	// Foreign-Key relationships must be realized
	//@ManyToOne 
	//@JoinColumn(name="idSender")
	@Column(name="idReceiver", nullable=false)
	private Integer idReceiver;
	
	// to which conversation/topic belongs this certain message
	@Column(name="idTopic", nullable=false)
	private Integer idTopic;
	
	@Column(name="title")
	private String title;

	@Column(name="text")
	private String text;
	
	
	// constructor for starting a new conversation
	public Message(Integer idSender, Integer idReceiver, String title, String text) {
		
		// find the last topicId and increment it by 1
		this.idSender = idSender;
		this.idReceiver = idReceiver;
		this.title = title;
		this.text = text;
	}
	
	// constructor for replying to an existent message
	public Message(Integer idSender, Integer idReceiver, Integer idTopic, String title, String text) {
		
		this.idSender = idSender;
		this.idReceiver = idReceiver;
		this.idTopic = idTopic;
		this.title = title;
		this.text = text;
	}
	
	
	//methods
	public Integer getIdMessage(){
		return this.idMessage;
	}
	
	public Date getTimeStamp(){
		return this.timeStamp;
	}
	
	public Integer getIdSender() {
		return this.idSender;
	}

	public void setIdSender(Integer idSender) {
		this.idSender = idSender;
	}
	
	public Integer getIdReceiver() {
		return this.idReceiver;
	}

	public void setIdReceivwer(Integer idReceiver) {
		this.idReceiver = idReceiver;
	}
	
	public Integer getIdTopic() {
		return this.idTopic;
	}
	
	public void setIdTopic(Integer idTopic) {
		this.idTopic = idTopic;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}

}

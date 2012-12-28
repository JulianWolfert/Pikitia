package de.htw.fb4.bilderplattform.business;

import java.util.List;

import de.htw.fb4.bilderplattform.dao.Message;

/**
 * @since 04.12.2012
 * @author Benjamin Schock
 * 
 */

public interface IMessageService {

	public void saveMessage(Message message);
	
	public List<Message> getMessageList(int idUser);
	
	public void deleteMessage(int idMessage);
	
	public void deleteMessage(Message message);
	
	//public Message getMessageById(int idMessage);
	
}


package com.app.web.social.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.app.web.social.model.converter.StringListConverter;

@Entity
@Table(name="privatemessages",  uniqueConstraints={ @UniqueConstraint( columnNames={"message_id"} ) }     )
public class PrivateMessage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id", nullable=false, unique=true, length=22)
	private Long messageId;
   
	@Column(name="sender", nullable=false, unique=false, length=25)
	private String messageSender;
	
	@Column(name="recipient", nullable=false, unique=false, length=270)
	@Convert(converter = StringListConverter.class)
	private List<String> messageRecipients;
	  
	@Column(name="subject", nullable=false, unique=false, length=100)
	private String messageSubject;
	
	@Column(name="text", nullable=true, unique=false, length=2000)
	private String messageText;

	@Column(name="sentDate", nullable=false, unique=false)
	private Timestamp sentDate;
	
	public PrivateMessage()
	{
		
	}
	
	public PrivateMessage (Long messageId, String messageSender, List<String> messageRecipients,
			String messageSubject,String messageText, Timestamp sentDate) 
	{
		this.messageId = messageId;
		this.messageSender = messageSender;
		this.messageRecipients = messageRecipients;
		this.messageSubject = messageSubject;
		this.messageText = messageText;
		this.sentDate = sentDate;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}

	public List<String> getMessageRecipients() {
		return messageRecipients;
	}
 
	public void setMessageRecipients(List<String> messageRecipients) {
		this.messageRecipients = messageRecipients;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Timestamp getSentDate() {
		return sentDate;
	}

	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}
	
	
	
	
} 

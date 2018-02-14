package com.app.web.social.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.OneToMany;

import com.app.web.social.model.Attachment;
import com.app.web.social.model.converter.StringListConverter;

@Entity
@Table(name = "privateMessages",  uniqueConstraints ={ @UniqueConstraint( columnNames = {"message_id"} ) }     )
public class PrivateMessage implements Serializable 
{
	private static final long serialVersionUID = -8914840082826396792L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	@Column(name = "message_id", nullable = false, unique = true, length = 22)
	private Long messageId;
   
	@Column(name = "sender", nullable = false, unique = false, length = 25)
	private String messageSender;
	
	@Column(name = "recipient", nullable = false, unique = false, length = 270)
	@Convert(converter = StringListConverter.class)
	private List<String> messageRecipients;
	  
	@Column(name = "subject", nullable = false, unique = false, length = 100)
	private String messageSubject;
	
	@Column(name="text", nullable=false, unique=false, length=2000)
	@Basic(fetch = FetchType.LAZY)
	private String messageText;

	@Column(name = "sentDate", nullable = false, unique = false)
	private Timestamp sentDate;
	
	@Column(name = "anyAttachment", nullable = false, unique = false)
	private boolean anyAttachment=false;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "message", fetch = FetchType.EAGER)
	private Set<Attachment> attachments;
	 
	@Transient
	private List<CommonsMultipartFile> fileUpload;
	
	@Column(name = "anyoneRemoved", nullable= false, unique = false)
    private boolean anyoneRemoved;
	
    public PrivateMessage()
	{
		
	}
	
	public PrivateMessage (Long messageId, String messageSender, List<String> messageRecipients, String messageSubject,
		String messageText, Timestamp sentDate, boolean anyAttachment, Set<Attachment> attachments, boolean anyoneRemoved) 
	{
		this.messageId = messageId;
		this.messageSender = messageSender;
		this.messageRecipients = messageRecipients;
		this.messageSubject = messageSubject;
		this.messageText = messageText;
		this.sentDate = sentDate;
		this.anyAttachment = anyAttachment;
		this.attachments = attachments;
		this.anyoneRemoved = anyoneRemoved;
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

	
	public boolean isAnyAttachment() {
		return anyAttachment;
	}

	public void setIsAnyAttachment(boolean anyAttachment) {
		this.anyAttachment = anyAttachment;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(Attachment attachment)
	{
		this.attachments.add(attachment);
	}
	
	public void removeAttachment(Attachment attachment)
	{
		this.attachments.remove(attachment);
	} 

	public List<CommonsMultipartFile> getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(List<CommonsMultipartFile> fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	
	public boolean isAnyoneRemoved() {
		return anyoneRemoved;
	}

	public void setAnyoneRemoved(boolean anyoneRemoved) {
		this.anyoneRemoved = anyoneRemoved;
	}

	public boolean validateFiles(List<CommonsMultipartFile> fileUpload)
	{
		if(fileUpload.size()>5) return false;
		long totalSize = 0L;
		for(CommonsMultipartFile file : fileUpload)
		{
		  	totalSize+=file.getSize();
		  	String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		  	if(extension.equals("exe")) return false;
		}
		
		return totalSize<=2000000L;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (anyAttachment ? 1231 : 1237);
		result = prime * result + (anyoneRemoved ? 1231 : 1237);
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((fileUpload == null) ? 0 : fileUpload.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((messageRecipients == null) ? 0 : messageRecipients.hashCode());
		result = prime * result + ((messageSender == null) ? 0 : messageSender.hashCode());
		result = prime * result + ((messageSubject == null) ? 0 : messageSubject.hashCode());
		result = prime * result + ((messageText == null) ? 0 : messageText.hashCode());
		result = prime * result + ((sentDate == null) ? 0 : sentDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivateMessage other = (PrivateMessage) obj;
		if (anyAttachment != other.anyAttachment)
			return false;
		if (anyoneRemoved != other.anyoneRemoved)
			return false;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (fileUpload == null) {
			if (other.fileUpload != null)
				return false;
		} else if (!fileUpload.equals(other.fileUpload))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (messageRecipients == null) {
			if (other.messageRecipients != null)
				return false;
		} else if (!messageRecipients.equals(other.messageRecipients))
			return false;
		if (messageSender == null) {
			if (other.messageSender != null)
				return false;
		} else if (!messageSender.equals(other.messageSender))
			return false;
		if (messageSubject == null) {
			if (other.messageSubject != null)
				return false;
		} else if (!messageSubject.equals(other.messageSubject))
			return false;
		if (messageText == null) {
			if (other.messageText != null)
				return false;
		} else if (!messageText.equals(other.messageText))
			return false;
		if (sentDate == null) {
			if (other.sentDate != null)
				return false;
		} else if (!sentDate.equals(other.sentDate))
			return false;
		return true;
	}
	
	
	
} 

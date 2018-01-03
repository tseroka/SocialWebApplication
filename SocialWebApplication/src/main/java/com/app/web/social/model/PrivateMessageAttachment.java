package com.app.web.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="privateMessagesAttachments", uniqueConstraints={ @UniqueConstraint( columnNames={"message_id"} ) }     )
public class PrivateMessageAttachment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id", nullable=false, unique=false, length=22)
	private Long messageId;

	@Column(name="attachmentName", nullable=false, unique=false, length=100)
	private String attachmentName;
	
	@Column(name="fileType", nullable=false, unique=false, length=100)
	private String fileType;
	
	// @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name="attachment", nullable=false)
    private byte[] attachment;
 
	public PrivateMessageAttachment() 
	{
		
	}
	
	public PrivateMessageAttachment(String attachmentName, String fileType, byte[] attachment)
	{
		this.attachmentName = attachmentName;
		this.fileType = fileType;
		this.attachment = attachment;
	}
	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	
}

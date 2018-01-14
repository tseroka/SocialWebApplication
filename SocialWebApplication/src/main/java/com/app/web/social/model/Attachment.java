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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Attachments", uniqueConstraints = { @UniqueConstraint( columnNames = {"attachment_id"} ) }     )
public class Attachment 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "attachment_id", nullable = false, unique = true, length = 22)
	private Long attachmentId;

	@Column(name = "fileName", nullable = false, unique = false, length = 100)
	private String fileName;
	
	@Column(name = "fileType", nullable = false, unique = false, length = 100)
	private String fileType;
	
	@Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "fileContent", nullable = false)
    private byte[] fileContent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "message_id", nullable = false)
	private PrivateMessage message;

	
	public Attachment() 
	{
		
	}
	
	public Attachment(Long attachmentId, String fileName, String fileType, byte[] fileContent, PrivateMessage message)
	{
		this.attachmentId = attachmentId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContent = fileContent;
		this.message = message;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long messageId) {
		this.attachmentId = messageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public PrivateMessage getMessage() {
		return message;
	}

	public void setMessage(PrivateMessage message) {
		this.message = message;
	}
	

	/**public boolean isDownloadingAllowed(String nickname)
	{
		return ( this.message.getMessageRecipients().contains(nickname) || this.message.getMessageSender().equals(nickname) );
	}
*/
	
	
}

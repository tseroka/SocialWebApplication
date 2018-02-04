package com.app.web.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

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
public class Attachment implements Serializable
{

	private static final long serialVersionUID = -9035598417433562863L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	@Column(name = "attachment_id", nullable = false, unique = true, length = 22)
	private Long attachmentId;

	@Column(name = "fileName", nullable = false, unique = false, length = 100)
	private String fileName;
	
	@Column(name = "fileType", nullable = false, unique = false, length = 100)
	private String fileType;
	
	@Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "fileContent", nullable = false)
    private byte[] fileContent;
	
	@Column(name = "fileSize", nullable = false, length=15)
	private long fileSize;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id", nullable = false)
	private PrivateMessage message;

	
	public Attachment() 
	{
		
	}
	
	public Attachment(Long attachmentId, String fileName, String fileType, byte[] fileContent, long fileSize, PrivateMessage message)
	{
		this.attachmentId = attachmentId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContent = fileContent;
		this.fileSize = fileSize;
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

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public PrivateMessage getMessage() {
		return message;
	}

	public void setMessage(PrivateMessage message) {
		this.message = message;
	}


	
	
}

package com.app.web.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.CascadeType;

import java.sql.Timestamp;

@Entity
@Table(name="securityIssues",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id", "username" } ) }     )
public class SecurityIssues implements Serializable
{

	private static final long serialVersionUID = -1647904435384587934L;

	@Column(name="username", nullable=false, unique=true, length=25)
	private String username;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;
	
	@Column(name="activationCode", nullable=true, unique=true, length=8 )
	private String activationCode;
	
	@Column(name="lastIPAddress", nullable=true, unique=true, length=16 )
	private String lastIPAddress;
	
	@Column(name="lastLoginDate", nullable=true, unique=false)
	private Timestamp lastLoginDate;
	
	@Column(name="lockReason", nullable=true, unique=false, length=300)
	private String lockReason;
	
	public SecurityIssues() 
	{
		
	}
	
	public SecurityIssues(String username, UserAccount userAccount, String activationCode, String lastIPAddress,
			Timestamp lastLoginDate, String lockReason) 
	{
		this.username = username;
		this.userAccount = userAccount;
		this.activationCode = activationCode;
		this.lastIPAddress = lastIPAddress;
		this.lastLoginDate = lastLoginDate;
		this.lockReason = lockReason;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserAccount getUserAccount(){
	     return userAccount;
	}
	 
    public void setUserAccount(UserAccount userAccount) {
	     this.userAccount = userAccount;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getLastIPAddress() {
		return lastIPAddress;
	}

	public void setLastIPAddress(String lastIPAddress) {
		this.lastIPAddress = lastIPAddress;
	}

	public Timestamp getTimestamp() {
		return lastLoginDate;
	}

	public void setTimestamp(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}
    
    
}

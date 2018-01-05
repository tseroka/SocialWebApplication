package com.app.web.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import com.app.web.social.model.converter.StringSetConverter;

import javax.persistence.CascadeType;

import java.sql.Timestamp;
import java.util.HashSet;

@Entity
@Table(name="securityIssues",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id", "username" } ) }     )
public class SecurityIssues implements Serializable
{

	private static final long serialVersionUID = -1647904435384587934L;

	@Column(name="username", nullable=false, unique=true, length=25)
	private String username;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;
	
	@Column(name="activationCode", nullable=false, unique=false, length=8 )
	private int activationCode;
	
	@Column(name="unlockCode", nullable=true, unique=false, length=8 )
	private int unlockCode;
	
	@Column(name="resetPasswordCode", nullable=true, unique=false, length=19 )
	private Long resetPasswordCode;
	
	@Column(name="lastIPAddress", nullable=false, unique=false, length=16 )
	private String lastIPAddress;
	
	@Column(name="allIPAddresses", nullable=false, unique=false)
	@Convert(converter = StringSetConverter.class)
	private HashSet<String> allIPAddresses;
	
	@Column(name="lastLoginDate", nullable=false, unique=false)
	private Timestamp lastLoginDate;
	
	@Column(name="numberOfLoginFails", nullable=false, unique=false, length=1)
	@Min(0)
	@Max(5)
	private byte numberOfLoginFails;
	
	@Column(name="lockReason", nullable=true, unique=false, length=300)
	private String lockReason;
	
	public SecurityIssues() 
	{
		
	}
	
	public SecurityIssues(String username, UserAccount userAccount, int activationCode, int unlockCode, Long resetPasswordCode,
			String lastIPAddress, HashSet<String> allIPAddresses, Timestamp lastLoginDate, 
			byte numberOfLoginFails, String lockReason) 
	{
		this.username = username;
		this.userAccount = userAccount;
		this.activationCode = activationCode;
		this.unlockCode = unlockCode;
		this.resetPasswordCode = resetPasswordCode;
		this.lastIPAddress = lastIPAddress;
		this.allIPAddresses = allIPAddresses;
		this.lastLoginDate = lastLoginDate;
		this.numberOfLoginFails = numberOfLoginFails;
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

	public int getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(int activationCode) {
		this.activationCode = activationCode;
	}

	public int getUnlockCode() {
		return unlockCode;
	}

	public void setUnlockCode(int unlockCode) {
		this.unlockCode = unlockCode;
	}

	
	public Long getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(Long resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}

	public String getLastIPAddress() {
		return lastIPAddress;
	}

	public void setLastIPAddress(String lastIPAddress) {
		this.lastIPAddress = lastIPAddress;
	}

	
	
	public HashSet<String> getAllIPAddresses() {
		return allIPAddresses;
	}

	public void setAllIPAddresses(HashSet<String> allIPAddresses) {
		this.allIPAddresses = allIPAddresses;
	}

	public void addIPAddress(String ip)
	{
	   this.allIPAddresses.add(ip);	
	}
	
	
	
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public byte getNumberOfLoginFails() {
		return numberOfLoginFails;
	}

	public void setNumberOfLoginFails(byte numberOfLoginFails) {
		this.numberOfLoginFails = numberOfLoginFails;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}
    
    
}

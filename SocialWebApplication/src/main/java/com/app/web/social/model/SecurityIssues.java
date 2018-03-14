package com.app.web.social.model;

import java.io.Serializable;

import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import javax.persistence.Basic;
import javax.persistence.CascadeType;

import java.sql.Timestamp;

@Entity
@Table(name="securityIssues",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id", "username","activationCode", "unlockCode", "resetPasswordCode"  } ) }     )
public class SecurityIssues implements Serializable
{

	private static final long serialVersionUID = -1647904435384587934L;

	@Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private long id; 
	
	@Column(name="username", nullable=false, unique=true, length=25)
	private String username;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserAccount userAccount;
	
	@Column(name="activationCode", nullable=true, unique=true, length=20 )
	@Basic(fetch = FetchType.LAZY)
	private String activationCode;
	
	@Column(name="unlockCode", nullable=true, unique=true, length=20 )
	@Basic(fetch = FetchType.LAZY)
	private String unlockCode;
	
	@Column(name="resetPasswordCode", nullable=true, unique=true, length=30 )
	@Basic(fetch = FetchType.LAZY)
	private String resetPasswordCode;
	
	@Column(name="codeExpirationDate", nullable=true, unique=false)
	@Basic(fetch = FetchType.LAZY)
	private Timestamp codeExpirationDate;
	
	@Transient
	private final long expirationTime = 300000L;
		
	@Column(name="numberOfLoginFails", nullable=false, unique=false, length=1)
	@Min(0)
	@Max(5)
	private byte numberOfLoginFails;
	
	@Column(name="lockReason", nullable=true, unique=false, length=300)
	@Basic(fetch = FetchType.LAZY)
	private String lockReason;
	
	@Column(name="unlockDate", nullable=true, unique=false)
	@Basic(fetch = FetchType.LAZY)
	private Timestamp unlockDate;
	
	public SecurityIssues() 
	{
		
	}
	
	public SecurityIssues(long id, String username, UserAccount userAccount,String activationCode, String unlockCode, String resetPasswordCode,
			Timestamp codeExpirationDate, byte numberOfLoginFails, String lockReason, Timestamp unlockDate) 
	{
		this.id = id;
		this.username = username;
		this.userAccount = userAccount;
		this.activationCode = activationCode;
		this.unlockCode = unlockCode;
		this.resetPasswordCode = resetPasswordCode;
		this.codeExpirationDate = codeExpirationDate;
		this.numberOfLoginFails = numberOfLoginFails;
		this.lockReason = lockReason;
		this.unlockDate = unlockDate;
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

	public String getUnlockCode() {
		return unlockCode;
	}

	public void setUnlockCode(String unlockCode) {
		this.unlockCode = unlockCode;
	}

	
	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}

	public Timestamp getCodeExpirationDate() {
		return codeExpirationDate;
	}

	public void setCodeExpirationDate(Timestamp codeExpirationDate) {
		this.codeExpirationDate = codeExpirationDate;
	}
	
	public long getExpirationTime(){
		return this.expirationTime;
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

	public Timestamp getUnlockDate() {
		return unlockDate;
	}

	public void setUnlockDate(Timestamp unlockDate) {
		this.unlockDate = unlockDate;
	}
    
	public boolean isLockTimeElapsed()
	{
		return ( this.unlockDate.getTime() - (System.currentTimeMillis()) ) < 0 ;
	}
	
	public boolean codeNotExpired()
	{
		return ( this.codeExpirationDate.getTime() - (System.currentTimeMillis()) ) > 0 ;
	}
	
    public void setTimeToExpire()
    {
    	this.setCodeExpirationDate(new Timestamp(expirationTime+System.currentTimeMillis()));
    }

    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activationCode == null) ? 0 : activationCode.hashCode());
		result = prime * result + ((codeExpirationDate == null) ? 0 : codeExpirationDate.hashCode());
		result = prime * result + (int) (expirationTime ^ (expirationTime >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lockReason == null) ? 0 : lockReason.hashCode());
		result = prime * result + numberOfLoginFails;
		result = prime * result + ((resetPasswordCode == null) ? 0 : resetPasswordCode.hashCode());
		result = prime * result + ((unlockCode == null) ? 0 : unlockCode.hashCode());
		result = prime * result + ((unlockDate == null) ? 0 : unlockDate.hashCode());
		result = prime * result + ((userAccount == null) ? 0 : userAccount.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		SecurityIssues other = (SecurityIssues) obj;
		if (activationCode == null) {
			if (other.activationCode != null)
				return false;
		} else if (!activationCode.equals(other.activationCode))
			return false;
		if (codeExpirationDate == null) {
			if (other.codeExpirationDate != null)
				return false;
		} else if (!codeExpirationDate.equals(other.codeExpirationDate))
			return false;
		if (expirationTime != other.expirationTime)
			return false;
		if (id != other.id)
			return false;
		if (lockReason == null) {
			if (other.lockReason != null)
				return false;
		} else if (!lockReason.equals(other.lockReason))
			return false;
		if (numberOfLoginFails != other.numberOfLoginFails)
			return false;
		if (resetPasswordCode == null) {
			if (other.resetPasswordCode != null)
				return false;
		} else if (!resetPasswordCode.equals(other.resetPasswordCode))
			return false;
		if (unlockCode == null) {
			if (other.unlockCode != null)
				return false;
		} else if (!unlockCode.equals(other.unlockCode))
			return false;
		if (unlockDate == null) {
			if (other.unlockDate != null)
				return false;
		} else if (!unlockDate.equals(other.unlockDate))
			return false;
		if (userAccount == null) {
			if (other.userAccount != null)
				return false;
		} else if (!userAccount.equals(other.userAccount))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
    
    
}

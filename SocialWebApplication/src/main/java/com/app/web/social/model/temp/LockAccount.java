package com.app.web.social.model.temp;

public class LockAccount 
{
	private long id; 
	
	private String lockReason;
	
	private long lockTime;

    public LockAccount()
    {
    	
    }

	public LockAccount(long id, String lockReason, long lockTime) 
	{
		this.id = id;
		this.lockReason = lockReason;
		this.lockTime = lockTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public long getLockTime() {
		return lockTime;
	}

	public void setLockTime(long lockTime) {
		this.lockTime = lockTime;
	}

	
	
	
	
	
	
	
	
	
	
}

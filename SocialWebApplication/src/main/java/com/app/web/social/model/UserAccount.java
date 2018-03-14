package com.app.web.social.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import com.app.web.social.service.InputCorrectness;

@Entity
@Table(name="users",  uniqueConstraints={ @UniqueConstraint( columnNames={"user_id", "username", "nickname", "email"} ) }     )
@Valid
public class UserAccount implements Serializable, InputCorrectness {
	
	private static final long serialVersionUID = 7264035467573861733L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
	@Column(name="user_id", nullable=false, unique=true, length=12)
	private long id;
   
	@Column(name="username", nullable=false, unique=true, length=25)
	@Pattern(regexp=USERNAME_VALIDATION_REGEX)
	private String username;
	 
	@Column(name="password", nullable=false, unique=false, length=100)
    private String password;
	 	
	@Transient
	private String repeatPassword;
	
	@Column(name="nickname", nullable=false, unique=true, length=25)
	@Pattern(regexp=NICKNAME_VALIDATION_REGEX)
    private String nickname;
	
	@Column(name="email", nullable=false, unique=true, length=40)
    @Pattern(regexp=EMAIL_VALIDATION_REGEX)
    private String email;
	 
	@Column(name="country", nullable=true, unique=false, length=3)
    private String country;
	
	@Column(name="creationDate", nullable=false, unique=false)
	@Basic(fetch = FetchType.LAZY)
	private Timestamp creationDate;
	
	@Column(name="role", nullable=false, unique=false, length=25)
	private String role="ROLE_USER";
	
	@Column(name="enabled", nullable=false, unique=false, length=1)
	private boolean enabled=false;
  
	@Column(name="notLocked", nullable=false, unique=false, length=1)
	private boolean notLocked=true;
	
	

	public UserAccount()
    {
    	
    }
    
    public UserAccount (long id, String username, String password, String email, String nickname, String country,
    		Timestamp creationDate, String role, boolean enabled, boolean notLocked)
    {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.nickname = nickname;
    	this.country = country;
    	this.creationDate = creationDate;
    	this.role = role;
    	this.enabled = enabled;
    }

    public UserAccount (String username, String password, String email, String nickname, String country)
    {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	this.nickname = nickname;
    	this.country = country;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNotLocked() {
		return notLocked;
	}

	public void setNotLocked(boolean notLocked) {
		this.notLocked = notLocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + (notLocked ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((repeatPassword == null) ? 0 : repeatPassword.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		UserAccount other = (UserAccount) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (id != other.id)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (notLocked != other.notLocked)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (repeatPassword == null) {
			if (other.repeatPassword != null)
				return false;
		} else if (!repeatPassword.equals(other.repeatPassword))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	

}


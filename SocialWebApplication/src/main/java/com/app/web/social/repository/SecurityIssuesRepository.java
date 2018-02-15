package com.app.web.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.UserAccount;
import com.app.web.social.model.SecurityIssues;

@Repository
public interface SecurityIssuesRepository extends JpaRepository<SecurityIssues, UserAccount> {

	SecurityIssues findByUsername(String username);
	
	
	SecurityIssues findByActivationCode(String code);
	
	boolean existsByActivationCode(String code);
	
	
	SecurityIssues findByUnlockCode(String code);
	
	boolean existsByUnlockCode(String code);
	
	
	SecurityIssues findByResetPasswordCode(String code);
	
	boolean existsByResetPasswordCode(String code);
}

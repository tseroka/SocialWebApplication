package com.app.web.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount,Long> {

	UserAccount findByUsername(String username);
	
	UserAccount findByNickname(String nickname);
	
	UserAccount findByEmail(String email);
	
	
	boolean existsByUsername(String username);
	
	boolean existsByNickname(String nickname);
	
	boolean existsByEmail(String email);
}

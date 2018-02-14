package com.app.web.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.Friends;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, String>{

}

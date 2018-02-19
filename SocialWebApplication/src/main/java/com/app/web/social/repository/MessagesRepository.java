package com.app.web.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.app.web.social.model.PrivateMessage;

@Repository
public interface MessagesRepository extends JpaRepository<PrivateMessage, Long>  {
	
	@Query("FROM PrivateMessage Pm where ( (Pm.messageSender=:nickname) OR (:nicknameAsList in Pm.messageRecipients) )")
	List<PrivateMessage> getAllMessages(@Param("nicknameAsList") List<String> nicknameAsList, @Param("nickname") String nickname);
	
	@Query("FROM PrivateMessage Pm where :nicknameAsList in Pm.messageRecipients ORDER BY Pm.sentDate desc ")
	Page<PrivateMessage> getInbox(@Param("nicknameAsList") List<String> nicknameAsList,@Param("page") Pageable pageable);
	
	@Query("SELECT count(Pm.messageSender) FROM PrivateMessage Pm WHERE :recipient in Pm.messageRecipients")
	Long countInboxMessages(@Param("recipient") List<String> recipient);
	
	@Query("FROM PrivateMessage Pm where Pm.messageSender=:nickname ORDER BY Pm.sentDate desc")
	Page<PrivateMessage> getOutbox(@Param("nickname") String nickname,@Param("page") Pageable pageable);
	
	@Query("SELECT count(Pm.messageSender) FROM PrivateMessage Pm WHERE Pm.messageSender =:msgSender")
	Long countOutboxMessages(@Param("msgSender") String sender);
	
}

package com.app.web.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.web.social.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}

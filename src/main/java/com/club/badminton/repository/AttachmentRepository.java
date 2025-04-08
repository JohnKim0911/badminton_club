package com.club.badminton.repository;

import com.club.badminton.entity.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}

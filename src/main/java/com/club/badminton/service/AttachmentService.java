package com.club.badminton.service;

import com.club.badminton.entity.attachment.Attachment;
import com.club.badminton.exception.attachment.FileTooBigException;
import com.club.badminton.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {

    public static final long MAX_SIZE_IN_MB = 10; // 10MB;

    @Value("${file.upload-dir}") //application.yml에서 값 가져옴.
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;

    @Transactional
    public Attachment save(MultipartFile file) throws IOException {
        validateFile(file);

        String originalName = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + originalName;
        String savePath = uploadDir + storedName;

        file.transferTo(new File(savePath));

        Attachment attachment = new Attachment(originalName, storedName);
        return attachmentRepository.save(attachment);
    }

    private void validateFile(MultipartFile file) {
        long maxSizeInBytes = MAX_SIZE_IN_MB * 1024 * 1024; // ex) 10 * 1024 * 1024 = 10MB;
        if (file.getSize() > maxSizeInBytes) {
            throw new FileTooBigException();
        }
    }

    public Attachment findById(Long id) {
        return attachmentRepository.findById(id).get();
    }

    @Transactional
    public void delete(Attachment attachment) throws IOException {
        deletePhysicalFile(attachment);
        attachmentRepository.delete(attachment);
    }

    private void deletePhysicalFile(Attachment attachment) throws IOException {
        Path file = Path.of(uploadDir + attachment.getStoredName());
        Files.delete(file);
        log.trace("삭제된 attachment_id: {}, 삭제된 파일: {},", attachment.getId(), file);
    }
}

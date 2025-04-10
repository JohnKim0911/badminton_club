package com.club.badminton.service;

import com.club.badminton.entity.attachment.Attachment;
import com.club.badminton.exception.NoFileException;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;

    @Transactional
    public Attachment save(Long memberId, MultipartFile file) throws IOException {
        validateFile(file);

        String originalName = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + originalName;
        String savePath = uploadDir + storedName;

        file.transferTo(new File(savePath));

        Attachment attachment = new Attachment(originalName, storedName);
        return attachmentRepository.save(attachment);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new NoFileException();
        }
    }

    public Attachment findById(Long id) {
        return attachmentRepository.findById(id).get();
    }


    public void delete(Long id) throws IOException {
        deletePhysicalFile(id);
        attachmentRepository.deleteById(id);
    }

    private void deletePhysicalFile(Long id) throws IOException {
        Attachment attachment = findById(id);
        Path file = Path.of(uploadDir + attachment.getStoredName());
        Files.delete(file);
        log.trace("삭제된 파일: {}, 삭제된 attachment_id: {}", file, id);
    }
}

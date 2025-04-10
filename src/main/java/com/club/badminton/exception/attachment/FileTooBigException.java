package com.club.badminton.exception.attachment;

import static com.club.badminton.service.AttachmentService.*;

public class FileTooBigException extends RuntimeException {
    public static final String ERROR_MESSAGE = "파일이 너무 큽니다. 최대 업로드 크기 " + MAX_SIZE_IN_MB + "MB를 초과했습니다.";

    public FileTooBigException() {
        super(ERROR_MESSAGE);
    }
}
